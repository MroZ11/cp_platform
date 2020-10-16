package com.costar.controller;

import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.model.Module;
import com.costar.service.ModuleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by cloud on 2019/6/14.
 */
@RestController
@RequestMapping("module")
public class ModuleController {

    @Resource
    ModuleService moduleService;


    @RequestMapping(value = "tree", method = RequestMethod.POST)
    public String getFullTree() {
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, moduleService.getFullModules()).toJsonStr();
    }

    @RequestMapping(value = "roleModuleTree", method = RequestMethod.POST)
    public String getFullTree(String roleId) {
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, moduleService.getRoleModuleTreeByRoleId(roleId)).toJsonStr();
    }

    /**
     * 新增或修改
     *
     * @param moduleParam
     * @return
     */
    @PostMapping("/save")
    public String save(@RequestBody Module moduleParam) {
        moduleService.saveOrUpdate(moduleParam);
        return SuccessRe.createRe(ReCode.SAVE_DATA_SUCCESS).toJsonStr();
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public String save(String id) {
        moduleService.deleteMoudleAndChild(id);
        return SuccessRe.createRe(ReCode.DELETE_DATA_SUCCESS).toJsonStr();
    }

}
