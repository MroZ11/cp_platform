package com.costar.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.costar.core.response.FailRe;
import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.core.utils.EPager;
import com.costar.dao.AppPayChannelDao;
import com.costar.dao.UserAppDao;
import com.costar.model.UserApp;
import com.costar.service.UserAppService;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cloud on 2019/6/28.
 */
@RestController
@RequestMapping("/user_app")
public class UserAppController {

    @Resource
    private UserAppService uerAppService;

    @Resource
    private UserAppDao userAppDao;

    @Resource
    private AppPayChannelDao appPayChannelDao;


    @PostMapping(value = "/add")
    public String add(@RequestBody UserApp userApp, Principal principal) {
        try {
            userApp.setOwnerUserName(principal.getName());
            uerAppService.addUserApp(userApp);
        } catch (Exception e) {
            return FailRe.createRe(ReCode.ADD_DATA_ERROR).toJsonStr();
        }
        return SuccessRe.createRe(ReCode.ADD_DATA_SUCCESS).toJsonStr();
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestBody String[] ids) {
        try {
            uerAppService.deleteUserApp(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return FailRe.createRe(ReCode.DELETE_DATA_ERROR).toJsonStr();
        }
        return SuccessRe.createRe(ReCode.DELETE_DATA_SUCCESS).toJsonStr();
    }

    @PostMapping("/edit")
    public Object edit(@RequestBody UserApp resources) {
        UserApp userApp = userAppDao.findById(resources.getAppId()).orElse(null);
        if(userApp==null){
            return FailRe.createRe(ReCode.PARAM_ERROR).toJsonStr();
        }
        userApp.setAppName(resources.getAppName());
        userApp.setIpWhiteList(resources.getIpWhiteList());
        userApp.setNotifyUrl(resources.getNotifyUrl());
        userApp.setNotes(resources.getNotes());
        return SuccessRe.createRe(ReCode.UPDATE_DATA_SUCCESS);
    }

    @PostMapping("self")
    public String list(Principal principal, EPager ePager) {
        Page userApps = uerAppService.selfAppsPaged(principal.getName(), ePager);
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, EPager.pageToEPager(ePager, userApps)).toJsonStr();
    }

    @RequestMapping(value = "{appId}/payChannel", method = RequestMethod.POST)
    public String getAppPayChannel(@PathVariable String appId,String channelCode) {
        if(StrUtil.isNotEmpty(channelCode)){
            return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS,appPayChannelDao.getByAppIdAndChannelCode(appId,channelCode)).toJsonStr();
        }
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS,uerAppService.getAppPayChannel(appId)).toJsonStr();
    }

    @RequestMapping(value = "{appId}/payChannel/{channelCode}", method = RequestMethod.POST)
    public String getAppPayChannelByChannelCode(@PathVariable String appId,@PathVariable String channelCode) {
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS,appPayChannelDao.getByAppIdAndChannelCode(appId,channelCode)).toJsonStr();
    }


}
