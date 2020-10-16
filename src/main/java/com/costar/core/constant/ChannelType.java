package com.costar.core.constant;

import lombok.Getter;

/**
 * Created by cloud on 2020/10/15.
 */
@Getter
public enum ChannelType {

    ALIPAY("alipay","支付宝 App 支付"),
    ALIPAY_WAP("alipay_wap","支付宝手机网站支付"),
    ALIPAY_QR("alipay_qr","支付宝扫码支付"),
    ALIPAY_SCAN("alipay_scan","支付宝条码支付"),
    ALIPAY_LITE("alipay_lite","支付宝小程序支付"),
    ALIPAY_PC_DIRECT("alipay_pc_direct","支付宝电脑网站支付"),
    WX("wx","微信 App 支付"),
    WX_PUB("wx_pub","微信 JSAPI 支付"),
    WX_PUB_QR("wx_pub_qr","微信 Native 支付"),
    WX_PUB_SCAN("wx_pub_scan","微信付款码支付"),
    WX_WAP("wx_wap","微信 H5 支付"),
    WX_LITE("wx_lite","微信小程序支付");

    private String value;
    private String desc;

    ChannelType(String value,String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }
}
