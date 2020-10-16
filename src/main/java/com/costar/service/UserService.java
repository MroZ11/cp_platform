package com.costar.service;

import com.costar.vo.ModuleTree;

/**
 * Created by cloud on 2019/6/20.
 */
public interface UserService {


    /**
     * 根据用户名获取该用户所有授权的菜单
     *
     * @param userName
     * @return
     */
    ModuleTree loadUserModuleTree(String userName);


    /**
     * 判断是不是超级管理员
     *
     * @param userName 当前登陆用户账号
     * @return
     */
    boolean isSuperManager(String userName);


    /**
     * 自助注册
     * @param username 姓名
     * @param password 密码
     * @param companyName 公司名称
     * @throws Exception
     */
    void register(String username, String password, String companyName) throws Exception;

    /**
     * 判断是不是超级管理员
     *
     * @param roleId 当前登陆角色
     * @return
     */
    boolean roleIsSuperManager(String roleId);
}
