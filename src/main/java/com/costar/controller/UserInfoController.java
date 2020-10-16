package com.costar.controller;

import cn.hutool.core.util.ReflectUtil;
import com.costar.core.response.FailRe;
import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.core.security.MyJdbcUserDetailsService;
import com.costar.dao.UserInfoDao;
import com.costar.model.UserInfo;
import com.costar.service.UserInfoService;
import com.costar.vo.projection.UserInfoPro;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * Created by cloud on 2019/6/26.
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController {

    @Resource
    UserInfoDao userInfoDao;
    @Resource
    MyJdbcUserDetailsService jdbcUserDetailsService;
    @Resource
    UserInfoService userInfoService;
    @Resource
    JdbcClientDetailsService jdbcClientDetailsService;


    @RequestMapping(value = "/self", method = RequestMethod.POST)
    public String self(Principal principal) {
        UserInfoPro userInfo = userInfoDao.getByUserName(principal.getName(), UserInfoPro.class).get();
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, userInfo).toJsonStr();
    }


    @RequestMapping(value = "/editSelf", method = RequestMethod.POST)
    public String edit(Principal principal, String key, String value) {
        UserInfo userInfo = userInfoDao.findById(principal.getName()).orElse(null);
        if (userInfo == null) {
            return FailRe.createRe(ReCode.UPDATE_DATA_ERROR).toJsonStr();
        }

        ReflectUtil.setFieldValue(userInfo, key, value);

        userInfoDao.save(userInfo);
        return SuccessRe.createRe(ReCode.UPDATE_DATA_SUCCESS).toJsonStr();
    }


    @RequestMapping(value = "/editSelfPassword", method = RequestMethod.POST)
    public String editSelfPassword(String old, String new1, String new2) {

        try {
            jdbcUserDetailsService.changePassword(old, new1);
        } catch (AuthenticationException e) {
            return FailRe.createRe(ReCode.EDIT_PASSWORD_ERROR_OLD_PASSWORD_WRONG).toJsonStr();
        }
        return SuccessRe.createRe(ReCode.UPDATE_DATA_SUCCESS).toJsonStr();
    }


    @RequestMapping(value = "/sendCheckEmail", method = RequestMethod.POST)
    public String sendCheckEmail(Principal principal) {
        ReCode reCode = userInfoService.sendCheckEmail(principal.getName());
        if (reCode.equals(ReCode.SEND_EMAIL_CHECK_SUCCESS)) {
            return SuccessRe.createRe(reCode).toJsonStr();
        } else {
            return FailRe.createRe(reCode).toJsonStr();
        }
    }

    @RequestMapping(value = "/checkEmailCode", method = RequestMethod.POST)
    public String checkEmailCode(Principal principal, String code) {
        ReCode reCode = userInfoService.checkEmailAddress(principal.getName(), code);
        if (reCode.equals(ReCode.CHECK_EMAIL_ADDRESS_SUCCESS)) {
            return SuccessRe.createRe(reCode).toJsonStr();
        } else {
            return FailRe.createRe(reCode).toJsonStr();
        }
    }

    @RequestMapping(value = "/selfClient", method = RequestMethod.POST)
    public String selfClient(Principal principal) {
        UserInfoPro userInfo = userInfoDao.getByUserName(principal.getName(), UserInfoPro.class).get();
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(userInfo.getClientId());
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, clientDetails).toJsonStr();
    }

}
