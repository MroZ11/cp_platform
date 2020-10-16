package com.costar.service.impl;

import cn.hutool.core.util.IdUtil;
import com.costar.core.constant.SupportChannels;
import com.costar.core.response.ReCode;
import com.costar.core.utils.EPager;
import com.costar.dao.AppPayChannelDao;
import com.costar.dao.UserAppDao;
import com.costar.model.QUserApp;
import com.costar.model.UserApp;
import com.costar.service.UserAppService;
import com.costar.vo.AppPayChannelVo;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloud on 2019/6/28.
 */
@Service
@Transactional
public class UserAppServiceImpl implements UserAppService {

    public static String APP_ID_PREFIX = "app_";
    public static String DEFAULT_APP_NAME = "默认应用";

    @Resource
    private UserAppDao userAppDao;

    @Resource
    private AppPayChannelDao appPayChannelDao;


    @Override
    public UserApp addUserDefaultApp(String userName) {
        UserApp userApp = new UserApp();
        userApp.setOwnerUserName(userName);
        userApp.setAppId(APP_ID_PREFIX + IdUtil.objectId());
        userApp.setAppName(DEFAULT_APP_NAME);
        userApp.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return userAppDao.saveAndFlush(userApp);
    }

    @Override
    public UserApp addUserApp(UserApp param) {
        param.setAppId(APP_ID_PREFIX + IdUtil.objectId());
        param.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return userAppDao.saveAndFlush(param);
    }

    @Override
    public UserApp updateAppNotifyUrl(String appId, String notifyUrl) {
        UserApp userApp = userAppDao.findById(appId).orElse(null);
        if (userApp != null) {
            userApp.setNotifyUrl(notifyUrl);
            //删除userApp
            userAppDao.saveAndFlush(userApp);
        }
        return userApp;
    }

    @Override
    public UserApp updateIpWhiteList(String appId, String ipWhiteList) {
        UserApp userApp = userAppDao.findById(appId).orElse(null);
        if (userApp != null) {
            userApp.setIpWhiteList(ipWhiteList);
            //删除userApp
            userAppDao.saveAndFlush(userApp);
        }
        return userApp;
    }

    public ReCode deleteUserApp(String[] ids) throws Exception {
        for (String id : ids) {
            UserApp userApp = userAppDao.findById(id).orElse(null);
            if (userApp != null) {
                //删除userApp
                userAppDao.delete(userApp);
            }
        }
        return ReCode.DELETE_DATA_SUCCESS;
    }


    public Page selfAppsPaged(String userName, EPager ePager) {

        QUserApp userApp = QUserApp.userApp;
        PageRequest pg = PageRequest.of(ePager.getPage(), ePager.getSize(), Sort.by(Sort.Order.desc("createTime")));

        Predicate predicate = userApp.ownerUserName.eq(userName);
        //动态条件这样拼接 ExpressionUtils.and();

        /*

        结果可用 .map(trans)转换

        Function<UserApp,UserAppPro> trans = (userApp1)->{
            return new UserAppPro(userApp1.getName(),userApp1.getUserName());
        };*/

        Page userAppPros = userAppDao.findAll(predicate, pg);
        return userAppPros;
    }

    @Override
    public List<AppPayChannelVo> getAppPayChannel(String appId) {
        List<AppPayChannelVo> list = new ArrayList<>();
        SupportChannels.all().stream().forEach(payChannel -> {
            boolean opened = appPayChannelDao.countByAppIdAndChannelCodeAndEnable(appId,payChannel.getChannelCode(),true)>0;
            AppPayChannelVo appPayChannelVo = AppPayChannelVo.builder()
                    .appId(appId)
                    .channelCode(payChannel.getChannelCode())
                    .channelName(payChannel.getChannelName())
                    .scene(payChannel.getScene())
                    .opened(opened)
                    .build();
            list.add(appPayChannelVo);
        });
        return list;
    }


}
