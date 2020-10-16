package com.costar.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.costar.dao.ModuleDao;
import com.costar.dao.RolesMoudlesDao;
import com.costar.model.Module;
import com.costar.service.ModuleService;
import com.costar.vo.ModuleTree;
import com.costar.vo.RoleModuleTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by cloud on 2019/6/14.
 */
@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    @Resource
    ModuleDao moduleDao;

    @Resource
    RolesMoudlesDao rolesMoudlesDao;


    /**
     * 新增或修改菜单
     *
     * @param moduleParam
     */
    public void saveOrUpdate(Module moduleParam) {
        Assert.notEmpty(moduleParam.getParent());
        Assert.notEmpty(moduleParam.getModuleName());
        boolean isAdd = StrUtil.isEmpty(moduleParam.getId());
        if (isAdd) {
            //新增
            moduleParam.setEnable(1);
            Integer priority = moduleParam.getPriority();
            if (priority == null) {
                Integer currentMaxPriority = moduleDao.getChildMaxPriority(moduleParam.getParent());
                priority = currentMaxPriority == null ? 1 : currentMaxPriority + 1;
                moduleParam.setPriority(priority);
            }
            moduleDao.saveAndFlush(moduleParam);
        } else {
            //允许修改名称 权重 路径
            Module module = moduleDao.findById(moduleParam.getId()).get();
            module.setModuleUrl(moduleParam.getModuleUrl());
            module.setModuleName(moduleParam.getModuleName());
            module.setPriority(moduleParam.getPriority());
            moduleDao.saveAndFlush(module);
        }
    }

    /**
     * 删除菜单及其子菜单
     * @param moduleId
     */
    public void deleteMoudleAndChild(String moduleId) {
        Assert.notEmpty(moduleId);
        moduleDao.deleteMoudleAndChild(moduleId);
    }




    /**
     * 获取全部菜单树
     *
     * @return
     */
    public ModuleTree getFullModules() {
        ModuleTree fullModules = getRoot();
        fullModules.setChildren(getChilds(fullModules.getId()));
        return fullModules;
    }


    /**
     * 查找当前角色菜单树
     *
     * @param roleId
     * @return
     */
    public RoleModuleTree getRoleModuleTreeByRoleId(String roleId) {
        RoleModuleTree roleModuleTree = RoleModuleTree.builder().build();
        ModuleTree moduleTree = getFullModules();
        roleModuleTree.setBaseTree(moduleTree);
        roleModuleTree.setRoleId(roleId);
        Set<String> authorizedModuleIds = rolesMoudlesDao.getCurrentRoleMoudleIds(roleId);
        roleModuleTree.setAuthorizedIds(authorizedModuleIds);
        return roleModuleTree;
    }


    /**
     * 获取根目录(0下的直接目录，不包括0)
     *
     * @return
     */
    public ModuleTree getRoot() {
        return ModuleTree.builder().id("0").isParent(true).isUsable(true).name("全部菜单").open(true).build();
    }

    /**
     * 获取直接子目录(只查询一级)
     *
     * @param parent 父目录id
     * @return
     */
    public List<Module> getChildrenModules(String parent) {
        return moduleDao.findModulesByParentOrderByPriorityAsc(parent);
    }


    /**
     * 根据授权的菜单列表获取全部子菜单
     *
     * @param pid               父级菜单
     * @param authorizedModules 所有授权的菜单id
     * @return
     */
    public List<ModuleTree> getChilds(String pid, Set<String> authorizedModules) {
        List<ModuleTree> moduleTreeList = new ArrayList<ModuleTree>();
        List<Module> modules = getChildrenModules(pid, authorizedModules);
        if (modules.size() == 0) {
            return null;
        }

        modules.forEach((module) -> {
            ModuleTree moduleTree = ModuleTree.builder().build();
            moduleTree.setId(module.getId());
            moduleTree.setUsable(module.getEnable() != null && module.getEnable().equals(1));
            moduleTree.setOpen(false);
            moduleTree.setName(module.getModuleName());
            moduleTree.setModuleInfo(moduleDao.findById(module.getId()).get());

            List<ModuleTree> nextTreeList = getChilds(module.getId(), authorizedModules);
            moduleTree.setChildren(nextTreeList);
            moduleTree.setParent(nextTreeList != null && nextTreeList.size() > 0);
            moduleTreeList.add(moduleTree);
        });

        return moduleTreeList;
    }

    /**
     * 根据授权的菜单列表获取直接子目录(只查询一级)
     *
     * @param parent            父级菜单id
     * @param authorizedModules 所有授权的菜单id
     * @return
     */
    public List<Module> getChildrenModules(String parent, Set<String> authorizedModules) {
        List<Module> childrenModules = moduleDao.findModulesByParentOrderByPriorityAsc(parent).stream().filter((temp) -> {
            return authorizedModules.contains(temp.getId());
        }).collect(Collectors.toList());
        return childrenModules;
    }

    /**
     * 获取直接父目录
     *
     * @param chidrenId 当前子节点id
     * @return
     */
    public Module getParent(String chidrenId) {
        Module chidrenModule = moduleDao.findById(chidrenId).orElse(null);
        if (chidrenModule == null) {
            return null;
        }
        Module parentModule = moduleDao.findById(chidrenModule.getParent()).orElse(null);
        return parentModule;
    }

    /**
     * 获取全部父节点 包括祖辈
     *
     * @param chidrenId 当前子节点id
     * @return
     */
    public List<Module> getParents(String chidrenId) {
        List<Module> moduleList = new ArrayList<Module>();
        Module pmodule = getParent(chidrenId);
        if (pmodule != null && pmodule.getParent().equals(0)) {
            return null;
        }
        moduleList.add(pmodule);
        if (pmodule != null) {
            List<Module> nextParent = getParents(pmodule.getId());
            moduleList.addAll(nextParent);
        }
        return moduleList;
    }

    /**
     * 根据当授权节点 计算算所有授权节点（包括所有父节点和祖辈节点）
     *
     * @param authorizedModules 所有授权的节点（可以不包括父节点，拥有子节点自动获得父节点权限）
     * @return 所有授权节点（包括当前节点和所有其父节点及其祖辈节点）
     */
    public Set<String> getAuthorizedModulesIds(Set<String> authorizedModules) {
        Set<String> all = new TreeSet<>();
        all.add("0");//所有菜单目录作为根
        all.addAll(authorizedModules);
        authorizedModules.forEach(s -> {
            List<Module> parent = getParents(s);
            parent.forEach(module -> {
                if (module != null) {
                    all.add(module.getId());
                }
            });
        });
        return all;
    }

    /**
     * 获取子完整节点树
     *
     * @param pid 父节点
     * @return
     */
    private List<ModuleTree> getChilds(String pid) {
        List<ModuleTree> moduleTreeList = new ArrayList<ModuleTree>();
        List<Module> modules = getChildrenModules(pid);
        if (modules.size() == 0) {
            return null;
        }
        modules.forEach((module) -> {
            ModuleTree moduleTree = ModuleTree.builder().build();
            moduleTree.setId(module.getId());
            moduleTree.setUsable(module.getEnable() != null && module.getEnable().equals(1));
            moduleTree.setOpen(false);
            moduleTree.setName(module.getModuleName());
            moduleTree.setModuleInfo(moduleDao.findById(module.getId()).get());

            List<ModuleTree> nextTreeList = getChilds(moduleTree.getId());
            moduleTree.setChildren(nextTreeList);
            moduleTree.setParent(nextTreeList != null && nextTreeList.size() > 0);
            moduleTreeList.add(moduleTree);
        });

        return moduleTreeList;
    }


}
