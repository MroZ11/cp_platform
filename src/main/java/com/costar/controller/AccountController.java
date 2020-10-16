package com.costar.controller;

import com.costar.core.response.FailRe;
import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.core.security.MyJdbcUserDetailsService;
import com.costar.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by cloud on 2019/6/13.
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @Resource
    private MyJdbcUserDetailsService userDetailsService;

    @Resource
    UserService userService;

    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public String regist(String username, String password1,String companyName) {
        try {
            userService.register(username,password1,companyName);
        } catch (Exception e) {
            FailRe failRe = FailRe.createRe(ReCode.REGIST_ERROR);
            failRe.setDescribe(e.getMessage());
            return failRe.toJsonStr();
        }
        return SuccessRe.createRe(ReCode.REGIST_SUCCESS).toJsonStr();
    }
}
