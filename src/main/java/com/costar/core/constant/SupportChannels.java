package com.costar.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cloud on 2020/10/16.
 */
public class SupportChannels {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PayChannel{
        String channelCode;
        String channelName;
        String scene;
    }

    public static PayChannel  alipay = new PayChannel("alipay","支付宝App支付","APP/分期");
    public static PayChannel  alipay_wap = new PayChannel("alipay_wap","支付宝手机网站支付","WAP/分期");
    public static PayChannel  alipay_facetoface = new PayChannel("alipay_facetoface","支付宝当面付","用户主扫/用户被扫/分期");
    public static PayChannel  alipay_lite = new PayChannel("alipay_lite","支付宝小程序支付","支付宝小程序");
    public static PayChannel  alipay_pc_direct = new PayChannel("alipay_pc_direct","支付宝电脑网站支付","PC/分期");
    //public static PayChannel  wx = new PayChannel("ali","转账到支付宝账户","企业付款");
    public static PayChannel  wx = new PayChannel("wx","微信App支付","APP/企业付款");
    public static PayChannel  wx_pub = new PayChannel("wx_pub","微信JSAPI支付","微信公众号/用户主扫/用户被扫/企业付款/红包");
    public static PayChannel  wx_wap = new PayChannel("wx_wap","微信H5支付","WAP");
    public static PayChannel  wx_lite = new PayChannel("wx_lite","微信小程序支付","微信小程序/企业付款");

    public static List<PayChannel>  all(){
       return Arrays.asList(alipay,alipay_wap,alipay_facetoface,alipay_lite,alipay_pc_direct,wx,wx_pub,wx_wap,wx_lite);
    }



}
