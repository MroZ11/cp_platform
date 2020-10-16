package com.costar.controller;

import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.core.security.MyJdbcUserDetailsService;
import com.costar.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * Created by cloud on 2019/6/12.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    MyJdbcUserDetailsService userdetailsService;

    @Resource
    UserService userService;


    @RequestMapping("/add")
    public Object add() {
        userdetailsService.createUser(null);

        return null;
    }

    @RequestMapping("/delete")
    public Object delete() {
        userdetailsService.deleteUser(null);
        return null;
    }

    @RequestMapping("/edit")
    public Object edit() {
        userdetailsService.updateUser(null);
        return null;
    }

    @RequestMapping("/list")
    public Object list() {
        return null;
    }


    @RequestMapping(value = "moduleTree", method = RequestMethod.POST)
    public String getFullTree(Principal principal) {
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, userService.loadUserModuleTree(principal.getName())).toJsonStr();
    }


}
