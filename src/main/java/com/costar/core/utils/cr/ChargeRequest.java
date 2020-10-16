package com.costar.core.utils.cr;

/**
 * Created by cloud on 2020/10/16.
 */
public interface ChargeRequest {

    String getChannel();
    //渠道方的APPid
    String getChannelAppId();
    //渠道方的公钥
    String getChannelPublicKey();
    //渠道方的私钥
    String getChannelPrivateKey();
    //商户号，微信支付需要将商户号绑定到channelAppId下
    String getMerchantNumber();
    //外部订单号
    String getOutTradeNo();
    //标题
    String getSubject();
    //
    String getBody();
    //金额元
    Double getAmount();
    //条码支付时的授权码
    String getBarCode();

}
