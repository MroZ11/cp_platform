package com.costar.service;

import com.costar.core.response.ReCode;
import com.costar.core.utils.EPager;
import com.costar.model.UserApp;
import com.costar.vo.AppPayChannelVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by cloud on 2019/6/28.
 */
public interface UserAppService {

    UserApp addUserDefaultApp(String userName);

    UserApp addUserApp(UserApp param);

    UserApp updateAppNotifyUrl(String appId,String notifyUrl);

    UserApp updateIpWhiteList(String appId,String ipWhiteList);

    ReCode deleteUserApp(String[] ids) throws Exception;


    Page selfAppsPaged(String userName, EPager ePager);

    List<AppPayChannelVo> getAppPayChannel(String appId);

}
