package com.costar.service.impl;

import cn.hutool.core.util.IdUtil;
import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.dao.AppPayChannelDao;
import com.costar.model.AppPayChannel;
import com.costar.service.AppPayChannelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * Created by cloud on 2020/10/16.
 */
@Service
@Transactional
public class AppPayChannelServiceImpl implements AppPayChannelService {

    @Resource
    AppPayChannelDao appPayChannelDao;


    @Override
    public AppPayChannel save(AppPayChannel resource) {
        AppPayChannel appPayChannel = appPayChannelDao.getByAppIdAndChannelCode(resource.getAppId(), resource.getChannelCode()).orElseGet(() -> {
            AppPayChannel create = new AppPayChannel();
            create.setId(IdUtil.simpleUUID());
            create.setCreateTime(new Timestamp(System.currentTimeMillis()));
            return create;
        });
        appPayChannel.setAppId(resource.getAppId());
        appPayChannel.setChannelAppId(resource.getChannelAppId());
        appPayChannel.setChannelCode(resource.getChannelCode());
        appPayChannel.setChannelPrivateKey(resource.getChannelPrivateKey());
        appPayChannel.setChannelPublicKey(resource.getChannelPublicKey());
        appPayChannel.setMerchantNumber(resource.getMerchantNumber());
        appPayChannel.setEnable(true);
        return appPayChannelDao.saveAndFlush(appPayChannel);
    }
}
