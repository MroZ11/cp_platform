package com.costar.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.costar.core.response.ReCode;
import com.costar.core.security.MyJdbcUserDetailsService;
import com.costar.dao.ModuleDao;
import com.costar.dao.RolesMoudlesDao;
import com.costar.dao.UserInfoDao;
import com.costar.model.UserInfo;
import com.costar.service.ModuleService;
import com.costar.service.UserAppService;
import com.costar.service.UserService;
import com.costar.vo.ModuleTree;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by cloud on 2019/6/20.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    final static String ROLE_SUPER_ID = "ROLE_SUPER";
    final static String ROLE_SUPER_NAME = "ROLE_SUPER";
    final static String ROLE_USER = "ROLE_USER";
    final static String ROLE_APP_OWNER = "ROLE_APP_OWNER";
    final static String[] defaultAuthorizedGrantTypes = {"authorization_code",
            "password", "client_credentials", "implicit", "refresh_token"};
    final static String[] defaultClientScopes = {"channel_pay_api"};


    @Resource
    MyJdbcUserDetailsService userDetailsService;
    @Resource
    UserAppService userAppService;

    @Resource
    ModuleDao moduleDao;

    @Resource
    ModuleService moduleService;

    @Resource
    RolesMoudlesDao rolesMoudlesDao;

    @Resource
    UserInfoDao userInfoDao;

    @Resource
    JdbcClientDetailsService jdbcClientDetailsService;


    /**
     * 根据用户名获取该用户所有授权的菜单
     *
     * @param userName
     * @return
     */
    public ModuleTree loadUserModuleTree(String userName) {

        //根据用户获取角色Id---->根据角色Id获取菜单权限--->包装成树

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        //当前授权的叶子结点
        Set<String> currentRoleMoudleIds = new TreeSet<>();

        userDetails.getAuthorities().forEach((simpleGrantedAuthority) -> {
            //拿到该用户角色ID
            String roleId = simpleGrantedAuthority.getAuthority();
            if (roleIsSuperManager(roleId)) {
                //超级管理员 获得全部菜单授权
                currentRoleMoudleIds.addAll(moduleDao.getAllMoudleIds());
                return;
            }
            currentRoleMoudleIds.addAll(rolesMoudlesDao.getCurrentRoleMoudleIds(roleId));
        });

        //包含叶子结点和目录结点的全部授权模块id
        Set<String> allAuthorizedModuleIds = getAuthorizedModulesIds(currentRoleMoudleIds);

        ModuleTree fullModules = moduleService.getRoot();
        fullModules.setChildren(getAuthorizedModuleChilds(fullModules.getId(), allAuthorizedModuleIds));
        return fullModules;
    }


    /**
     * 判断是不是超级管理员
     *
     * @param userName 当前登陆用户账号
     * @return
     */
    public boolean isSuperManager(String userName) {
        //根据用户获取角色Id---->根据角色Id获取菜单权限--->包装成树
        boolean isSuperManager = false;
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        for (GrantedAuthority roleId : userDetails.getAuthorities()) {
            if (roleIsSuperManager(roleId.getAuthority())) {
                //超级管理员 获得全部菜单授权
                isSuperManager = true;
                break;
            }
        }
        return isSuperManager;
    }


    public void register(String username, String password, String companyName) throws Exception {
        if (userDetailsService.userExists(username)) {
            throw new RuntimeException(ReCode.REGIST_ERROR_USER_EXIST.getDescribe());
        }
        UserDetails u = User.builder()
                .username(username).password(password)
                .authorities(ROLE_USER, ROLE_APP_OWNER)
                .build();
        userDetailsService.createUser(u);

        //增加用户的Oath2.0客服端
        BaseClientDetails baseClientDetails = createDefaultAppClient();
        jdbcClientDetailsService.addClientDetails(baseClientDetails);

        //增加用户的默认应用
        userAppService.addUserDefaultApp(username);

        //增加基础资料
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setUserName(username);
        userInfo.setCompanyName(companyName);
        userInfo.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        userInfo.setClientId(baseClientDetails.getClientId());
        userInfoDao.save(userInfo);
    }

    /**
     * 判断是不是超级管理员
     *
     * @param roleId 当前登陆角色
     * @return
     */
    public boolean roleIsSuperManager(String roleId) {
        //根据用户获取角色Id---->根据角色Id获取菜单权限--->包装成树
        return roleId.equalsIgnoreCase(ROLE_SUPER_ID);
    }


    /**
     * 计算所有授权目录id
     *
     * @param currentRoleMoudleIds 当前授权节点id
     * @return 所有授权目录id
     */
    private Set<String> getAuthorizedModulesIds(Set<String> currentRoleMoudleIds) {
        return moduleService.getAuthorizedModulesIds(currentRoleMoudleIds);
    }

    /**
     * 授权列表剔除后的子节点
     *
     * @param pid                    父节点
     * @param allAuthorizedModuleIds 授权列表
     * @return 授权列表剔除后的子节点
     */
    private List<ModuleTree> getAuthorizedModuleChilds(String pid, Set<String> allAuthorizedModuleIds) {
        return moduleService.getChilds(pid, allAuthorizedModuleIds);
    }

    /**
     * 增加Oauth2.0 client
     *
     * @return
     */
    private BaseClientDetails createDefaultAppClient() {
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setClientId(UUID.randomUUID().toString().replace("-", ""));
        baseClientDetails.setClientSecret(RandomUtil.randomString(10));
        baseClientDetails.setAuthorizedGrantTypes(Arrays.asList(defaultAuthorizedGrantTypes));
        baseClientDetails.setAuthorities(
                AuthorityUtils.commaSeparatedStringToAuthorityList(ROLE_USER + "," + ROLE_APP_OWNER));
        baseClientDetails.setScope(Arrays.asList(defaultClientScopes));
        baseClientDetails.setAccessTokenValiditySeconds(24 * 60 * 60);//默认有效时间24小时
        baseClientDetails.setRegisteredRedirectUri(Collections.<String>emptySet());
        return baseClientDetails;
    }


}
