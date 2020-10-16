package com.costar.api.v1.controller;

import com.costar.dao.UserInfoDao;
import com.costar.model.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by cloud on 2020/10/15.
 */
@RestController
@RequestMapping("api/v1/charge")
public class ChargeController {

    @Resource
    UserInfoDao userInfoDao;

    @ModelAttribute("user")
    public UserInfo addAccount(Authentication authentication) {
        String clientId = authentication.getName();
        return userInfoDao.getByClientId(clientId);
    }


    @RequestMapping(value = "create")
    public Object create(@ModelAttribute("user") UserInfo userInfo) {

        return null;
    }

    @RequestMapping(value = "reverse")
    public Object reverse(@ModelAttribute("user") UserInfo userInfo) {
        return null;
    }

    @RequestMapping(value = "query")
    public Object query(@ModelAttribute("user") UserInfo userInfo) {
        return null;
    }

    @RequestMapping(value = "list")
    public Object list(@ModelAttribute("user") UserInfo userInfo) {
        return null;
    }


}
