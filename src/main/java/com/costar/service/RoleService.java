package com.costar.service;


/**
 * Created by cloud on 2019/6/20.
 */
public interface RoleService {

    /**
     * 分配角色菜单
     *
     * @param roleId               角色id
     * @param authorizedMoudlesIds 允许的菜单ids
     * @throws Exception
     */
    void allotRolesMoudles(String roleId, String[] authorizedMoudlesIds) throws Exception;


}
