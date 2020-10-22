package com.costar.core.constant;

import lombok.Getter;

/**
 * Created by cloud on 2020/10/15.
 */
@Getter
public enum ChannelType {

    ALIPAY("alipay","alipay","支付宝App支付"),
    ALIPAY_WAP("alipay_wap","alipay_wap","支付宝手机网站支付"),
    ALIPAY_QR("alipay_qr","alipay_facetoface","支付宝扫码支付"),
    ALIPAY_SCAN("alipay_scan","alipay_facetoface","支付宝条码支付"),
    ALIPAY_LITE("alipay_lite","alipay_lite","支付宝小程序支付"),
    ALIPAY_PC_DIRECT("alipay_pc_direct","alipay_pc_direct","支付宝电脑网站支付"),
    WX("wx","wx","微信App支付"),
    WX_PUB("wx_pub","wx_pub","微信JSAPI支付"),
    WX_PUB_QR("wx_pub_qr","wx_pub","微信Native支付"),
    WX_PUB_SCAN("wx_pub_scan","wx_pub","微信付款码支付"),
    WX_WAP("wx_wap","wx_wap","微信H5支付"),
    WX_LITE("wx_lite","wx_lite","微信小程序支付");

    private String value;
    private String desc;
    private String channelCode;

    ChannelType(String value,String channelCode,String desc) {
        this.value = value;
        this.desc = desc;
        this.channelCode = channelCode;
    }

    public String getValue() {
        return value;
    }
}
