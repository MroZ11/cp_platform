package com.costar.service;

import com.costar.model.Module;
import com.costar.vo.ModuleTree;
import com.costar.vo.RoleModuleTree;

import java.util.List;
import java.util.Set;

/**
 * Created by cloud on 2019/6/14.
 */
public interface ModuleService {


    /**
     * 新增或修改菜单
     *
     * @param moduleParam
     */
    void saveOrUpdate(Module moduleParam);

    /**
     * 删除菜单及其子菜单
     *
     * @param moduleId
     */
    void deleteMoudleAndChild(String moduleId);


    /**
     * 获取全部菜单树
     *
     * @return
     */
    ModuleTree getFullModules();


    /**
     * 查找当前角色菜单树
     *
     * @param roleId
     * @return
     */
    RoleModuleTree getRoleModuleTreeByRoleId(String roleId);


    /**
     * 获取根目录(0下的直接目录，不包括0)
     *
     * @return
     */
    ModuleTree getRoot();

    /**
     * 获取直接子目录(只查询一级)
     *
     * @param parent 父目录id
     * @return
     */
    List<Module> getChildrenModules(String parent);


    /**
     * 根据授权的菜单列表获取全部子菜单
     *
     * @param pid               父级菜单
     * @param authorizedModules 所有授权的菜单id
     * @return
     */
    List<ModuleTree> getChilds(String pid, Set<String> authorizedModules);

    /**
     * 根据授权的菜单列表获取直接子目录(只查询一级)
     *
     * @param parent            父级菜单id
     * @param authorizedModules 所有授权的菜单id
     * @return
     */
    List<Module> getChildrenModules(String parent, Set<String> authorizedModules);

    /**
     * 获取直接父目录
     *
     * @param chidrenId 当前子节点id
     * @return
     */
    Module getParent(String chidrenId);

    /**
     * 获取全部父节点 包括祖辈
     *
     * @param chidrenId 当前子节点id
     * @return
     */
    List<Module> getParents(String chidrenId);

    /**
     * 根据当授权节点 计算算所有授权节点（包括所有父节点和祖辈节点）
     *
     * @param authorizedModules 所有授权的节点（可以不包括父节点，拥有子节点自动获得父节点权限）
     * @return 所有授权节点（包括当前节点和所有其父节点及其祖辈节点）
     */
    Set<String> getAuthorizedModulesIds(Set<String> authorizedModules);


}
