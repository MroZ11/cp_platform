package com.costar.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.costar.api.v1.dto.request.ChargeCreate;

import com.costar.api.v1.dto.response.ChargeResponse;
import com.costar.core.utils.cr.ChannelPayAdapterRegistry;
import com.costar.core.utils.cr.ChargeRequest;
import com.costar.dao.AppPayChannelDao;
import com.costar.dao.ChannelCertificateDao;
import com.costar.dao.UserAppDao;
import com.costar.model.AppPayChannel;
import com.costar.model.Charge;
import com.costar.model.UserApp;
import com.costar.service.ChargeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created by cloud on 2020/10/20.
 */
@Service
@Transactional
public class ChargeServiceImpl implements ChargeService {

    @Resource
    UserAppDao appDao;

    @Resource
    AppPayChannelDao appPayChannelDao;

    @Resource
    ChannelCertificateDao channelCertificateDao;

    ChannelPayAdapterRegistry channelPayAdapterRegistry = new ChannelPayAdapterRegistry();

    @Override
    public ChargeResponse createCharge(ChargeCreate chargeCreate) {
        String appId = chargeCreate.getAppId();
        String channel = chargeCreate.getChannel();
        UserApp userApp = appDao.findById(appId).orElse(null);
        AppPayChannel appPayChannel = appPayChannelDao.getByAppIdAndChannelCode(appId, channel).orElse(null);

        String platFormId = IdUtil.simpleUUID();

        Charge charge = new Charge();
        charge.setAppPayChannelId(appPayChannel.getChannelAppId());
        charge.setAppPayChannel(channel);
        charge.setAmount(chargeCreate.getAmount());
        charge.setBody(chargeCreate.getBody());
        charge.setChargeSourceBody(JSON.toJSONString(chargeCreate));
        charge.setClientIp(chargeCreate.getClient_ip());
        charge.setCreateTime(new Timestamp(System.currentTimeMillis()));
        charge.setId(platFormId);
        charge.setMetadata(JSON.toJSONString(chargeCreate.getMetadata()));
        charge.setOutTradeNo(chargeCreate.getOrder_no());
        charge.setSubject(chargeCreate.getSubject());
        charge.setUserAppId(appId);


        charge.setPaid(false);
        charge.setPaidTime(null);
        charge.setReversed(false);
        charge.setReversedTime(null);
        charge.setChannelNo(null);
        charge.setChannelStatus(null);
        charge.setChannelSyncReturn(null);
        charge.setFailureCode(null);
        charge.setFailureMsg(null);
        charge.setFailureType(null);

        try {
            channelPayAdapterRegistry.doChannelPay(new ChargeRequest() {
                @Override
                public String getChannel() {
                    return channel;
                }

                @Override
                public String getChannelAppId() {
                    return appPayChannel.getChannelAppId();
                }

                @Override
                public String getChannelPublicKey() {
                    return appPayChannel.getChannelPublicKey();
                }

                @Override
                public String getChannelPrivateKey() {
                    return appPayChannel.getChannelPrivateKey();
                }

                @Override
                public String getMerchantNumber() {
                    return appPayChannel.getMerchantNumber();
                }

                @Override
                public String getOutTradeNo() {
                    return chargeCreate.getOrder_no();
                }

                @Override
                public String getSubject() {
                    return chargeCreate.getSubject();
                }

                @Override
                public String getBody() {
                    return chargeCreate.getBody();
                }

                @Override
                public Double getAmount() {
                    return chargeCreate.getAmount();
                }

                @Override
                public String getBarCode() {
                    return null;
                }

                @Override
                public byte[] getWxPKCS12Data() {
                    return channelCertificateDao.findWxP12ByAppPayChannelId(appPayChannel.getId());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }







}
