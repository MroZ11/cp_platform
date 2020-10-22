package com.costar.core.utils.cr.wxpay;

import cn.hutool.core.util.ArrayUtil;
import com.costar.core.constant.NotifyUrl;
import com.costar.core.utils.cr.ChannelPayAdapter;
import com.costar.core.utils.cr.ChargeRequest;
import com.costar.core.utils.cr.wxpay.sdk.MyWxPayConfig;
import com.costar.core.utils.cr.wxpay.sdk.WXPay;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cloud on 2020/10/16.
 */
public class WxPay implements ChannelPayAdapter {

    private final String[] supports = {"wx", "wx_pub", "wx_pub_qr", "wx_pub_scan", "wx_wap", "wx_lite"};


    @Override
    public boolean support(ChargeRequest charge) {
        return ArrayUtil.containsIgnoreCase(supports, charge.getChannel());
    }

    @Override
    public Object doChannelPay(ChargeRequest charge) throws Exception {
        WXPay wxpay = null;

        try {
            wxpay = new WXPay(new MyWxPayConfig(charge.getChannelAppId(), charge.getMerchantNumber(), charge.getChannelPrivateKey(), null), true, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String notifyUrl = NotifyUrl.ALI_PAY_NOTIFY_URL;
        String total_fee = String.valueOf(Math.round(charge.getAmount() * 100));
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", charge.getSubject());
        data.put("out_trade_no", charge.getOutTradeNo());
        data.put("total_fee", total_fee);
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", notifyUrl);

        if (charge.getChannel().equals("wx")) {
            //APP 支付
            data.put("trade_type", "APP");  // 此处指定为扫码支付
            try {
                Map<String, String> resp = wxpay.unifiedOrder(data);
                System.out.println(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (charge.getChannel().equals("wx_pub")) {
            //微信 JSAPI 支付  在公众内调用
            data.put("trade_type", "JSAPI");
            try {
                Map<String, String> resp = wxpay.unifiedOrder(data);
                System.out.println(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (charge.getChannel().equals("wx_pub_qr")) {
            //微信 Native 支付,生成二维码给用户扫码
            data.put("trade_type", "NATIVE");
            try {
                Map<String, String> resp = wxpay.unifiedOrder(data);
                System.out.println(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (charge.getChannel().equals("wx_pub_scan")) {
            //微信付款码支付 扫码用户的付款码
            data.put("trade_type", "JSAPI");
            data.put("auth_code", charge.getBarCode());
            try {
                Map<String, String> resp = wxpay.microPay(data);
                System.out.println(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (charge.getChannel().equals("wx_wap")) {
            //微信 H5 支付 H5支付是指商户在微信客户端外的移动端网页展示商品或服务
            data.put("trade_type", "MWEB");

            //{"h5_info": //h5支付固定传"h5_info"
            //     {"type": "",  //场景类型
            //             "wap_url": "",//WAP网站URL地址
            //             "wap_name": ""  //WAP 网站名
            //     }
            //}
            data.put("scene_info", "scene_info");


        } else if (charge.getChannel().equals("wx_lite")) {
            //微信小程序支付
            data.put("trade_type", "JSAPI");
            try {
                Map<String, String> resp = wxpay.unifiedOrder(data);
                System.out.println(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    public Object queryChannelPay(ChargeRequest charge) throws Exception {
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(new MyWxPayConfig(charge.getChannelAppId(), charge.getMerchantNumber(), charge.getChannelPrivateKey(), null), true, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", charge.getOutTradeNo());
        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object closeChannelPay(ChargeRequest charge) throws Exception {
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(new MyWxPayConfig(charge.getChannelAppId(), charge.getMerchantNumber(), charge.getChannelPrivateKey(), null), true, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", charge.getOutTradeNo());
        try {
            Map<String, String> resp = wxpay.closeOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object refundChannelPay(ChargeRequest charge) throws Exception {
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(new MyWxPayConfig(charge.getChannelAppId(), charge.getMerchantNumber(), charge.getChannelPrivateKey(), charge.getWxPKCS12Data()), true, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String total_fee = String.valueOf(Math.round(charge.getAmount() * 100));
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", charge.getOutTradeNo());
        //商户退款单号
        data.put("out_refund_no", charge.getOutTradeNo());
        data.put("total_fee", total_fee);
        data.put("refund_fee", total_fee);
        try {
            Map<String, String> resp = wxpay.refund(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object queryRefund(ChargeRequest charge) throws Exception {
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(new MyWxPayConfig(charge.getChannelAppId(), charge.getMerchantNumber(), charge.getChannelPrivateKey(), null), true, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", charge.getOutTradeNo());
        try {
            Map<String, String> resp = wxpay.refundQuery(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
