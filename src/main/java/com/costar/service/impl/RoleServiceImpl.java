package com.costar.service.impl;


import com.costar.dao.RoleDao;
import com.costar.dao.RolesMoudlesDao;
import com.costar.model.RolesMoudles;
import com.costar.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by cloud on 2019/6/20.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Resource
    RoleDao roleDao;

    @Resource
    RolesMoudlesDao rolesMoudlesDao;

    /**
     * 分配角色菜单
     *
     * @param roleId               角色id
     * @param authorizedMoudlesIds 允许的菜单ids
     * @throws Exception
     */
    public void allotRolesMoudles(String roleId, String[] authorizedMoudlesIds) throws Exception {
        //Assert.notNull("","");
        //删除现有菜单
        rolesMoudlesDao.deleteInBulkByRoleId(roleId);

        //重新添加新菜单
        for (String moudlesId : authorizedMoudlesIds) {
            RolesMoudles newRolesMoudles = new RolesMoudles();
            newRolesMoudles.setRoleId(roleId);
            newRolesMoudles.setModuleId(moudlesId);
            rolesMoudlesDao.save(newRolesMoudles);
        }

    }


}
