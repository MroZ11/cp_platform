package com.costar.core.utils.cr.alipay;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.kernel.Client;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.*;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.costar.core.constant.NotifyUrl;
import com.costar.core.utils.cr.ChannelPayAdapter;
import com.costar.core.utils.cr.ChargeRequest;
import com.costar.core.utils.cr.alipay.AliPayUtil;

/**
 * Created by cloud on 2020/10/16.
 */
public class AliPay implements ChannelPayAdapter {

    private final String[] supports = {"alipay", "alipay_wap", "alipay_qr", "alipay_scan", "alipay_lite", "alipay_pc_direct"};

    @Override
    public boolean support(ChargeRequest charge) {
        return ArrayUtil.containsIgnoreCase(supports, charge.getChannel());
    }

    @Override
    public Object doChannelPay(ChargeRequest charge) throws Exception{

        String appId = charge.getChannelAppId();
        String merchantPrivateKey = charge.getChannelPrivateKey();
        String alipayPublicKey = charge.getChannelPublicKey();
        String notifyUrl = NotifyUrl.ALI_PAY_NOTIFY_URL;
        try {
            //文档https://opendocs.alipay.com/open/00y8k9
            Client simpleClient = AliPayUtil.simpleClient(appId, merchantPrivateKey, alipayPublicKey, notifyUrl);
            if (charge.getChannel().equals("alipay")) {
                //Payment（支付能力）	App（手机APP）	pay（生成订单串，再使用客户端 SDK 凭此串唤起支付宝收银台）
                com.alipay.easysdk.payment.app.Client client = new com.alipay.easysdk.payment.app.Client(simpleClient);
                AlipayTradeAppPayResponse response = client.pay(charge.getSubject(), charge.getOutTradeNo(), charge.getAmount().toString());
                if (ResponseChecker.success(response)) {
                    System.out.println("调用成功");
                    System.out.println(JSON.toJSONString(response));
                } else {
                    System.err.println("调用失败");
                }
            } else if (charge.getChannel().equals("alipay_wap")) {
                //Payment（支付能力）	Wap（手机网站）	pay（生成交易表单，渲染后自动跳转支付宝网站引导用户完成支付）
                com.alipay.easysdk.payment.wap.Client client = new com.alipay.easysdk.payment.wap.Client(simpleClient);
                AlipayTradeWapPayResponse response = client.pay(charge.getSubject(), charge.getOutTradeNo(), charge.getAmount().toString(), "", "");
                if (ResponseChecker.success(response)) {
                    System.out.println("调用成功");
                    System.out.println(JSON.toJSONString(response));
                } else {
                    System.err.println("调用失败");
                }
            } else if (charge.getChannel().equals("alipay_qr")) {
                //Payment（支付能力）	FaceToFace（当面付）	precreate（生成交易付款码，待用户扫码付款）
                com.alipay.easysdk.payment.facetoface.Client client = new com.alipay.easysdk.payment.facetoface.Client(simpleClient);
                AlipayTradePrecreateResponse response = client.preCreate(charge.getSubject(), charge.getOutTradeNo(), charge.getAmount().toString());
                if (ResponseChecker.success(response)) {
                    System.out.println("调用成功");
                    System.out.println(JSON.toJSONString(response));
                } else {
                    System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
                }
            } else if (charge.getChannel().equals("alipay_scan")) {
                //Payment（支付能力）	FaceToFace（当面付）	pay（扫用户出示的付款码，完成付款）
                com.alipay.easysdk.payment.facetoface.Client client = new com.alipay.easysdk.payment.facetoface.Client(simpleClient);
                AlipayTradePayResponse response = client.pay(charge.getSubject(), charge.getOutTradeNo(), charge.getAmount().toString(), charge.getBarCode());
                if (ResponseChecker.success(response)) {
                    System.out.println("调用成功");
                    System.out.println(JSON.toJSONString(response));
                } else {
                    System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
                }
            } else if (charge.getChannel().equals("alipay_lite")) {
                //Payment（支付能力）	Page（电脑网站）	pay（生成交易表单，渲染后自动跳转支付宝网站引导用户完成支付）
                com.alipay.easysdk.payment.common.Client client = new com.alipay.easysdk.payment.common.Client(simpleClient);
                AlipayTradeCreateResponse response = client.create(charge.getSubject(), charge.getOutTradeNo(), charge.getAmount().toString(), "");
                if (ResponseChecker.success(response)) {
                    System.out.println("调用成功");
                    System.out.println(JSON.toJSONString(response));
                } else {
                    System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
                }
            } else if (charge.getChannel().equals("alipay_pc_direct")) {
                //Payment（支付能力）	Page（电脑网站）	pay（生成交易表单，渲染后自动跳转支付宝网站引导用户完成支付）
                com.alipay.easysdk.payment.page.Client client = new com.alipay.easysdk.payment.page.Client(simpleClient);
                AlipayTradePagePayResponse response = client.pay(charge.getSubject(), charge.getOutTradeNo(), charge.getAmount().toString(), "");
                if (ResponseChecker.success(response)) {
                    System.out.println("调用成功");
                    System.out.println(JSON.toJSONString(response));
                } else {
                    System.err.println("调用失败");
                }
            }

            // 3. 处理响应或异常
            /*if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                System.out.println(response);
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }*/
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Object queryChannelPay(ChargeRequest charge) throws Exception{
        String appId = charge.getChannelAppId();
        String merchantPrivateKey = charge.getChannelPrivateKey();
        String alipayPublicKey = charge.getChannelPublicKey();
        String notifyUrl = NotifyUrl.ALI_PAY_NOTIFY_URL;
        Client simpleClient = null;
        try {
            simpleClient = AliPayUtil.simpleClient(appId, merchantPrivateKey, alipayPublicKey, notifyUrl);
            com.alipay.easysdk.payment.common.Client client = new com.alipay.easysdk.payment.common.Client(simpleClient);
            AlipayTradeQueryResponse response = client.query(charge.getOutTradeNo());
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                System.out.println(JSON.toJSONString(response));
            } else {
                System.err.println("调用失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Object closeChannelPay(ChargeRequest charge) throws Exception{
        String appId = charge.getChannelAppId();
        String merchantPrivateKey = charge.getChannelPrivateKey();
        String alipayPublicKey = charge.getChannelPublicKey();
        String notifyUrl = NotifyUrl.ALI_PAY_NOTIFY_URL;
        Client simpleClient = null;
        try {
            simpleClient = AliPayUtil.simpleClient(appId, merchantPrivateKey, alipayPublicKey, notifyUrl);
            com.alipay.easysdk.payment.common.Client client = new com.alipay.easysdk.payment.common.Client(simpleClient);
            AlipayTradeCloseResponse response = client.close(charge.getOutTradeNo());
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                System.out.println(JSON.toJSONString(response));
            } else {
                System.err.println("调用失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Object refundChannelPay(ChargeRequest charge) throws Exception{
        String appId = charge.getChannelAppId();
        String merchantPrivateKey = charge.getChannelPrivateKey();
        String alipayPublicKey = charge.getChannelPublicKey();
        String notifyUrl = NotifyUrl.ALI_PAY_NOTIFY_URL;
        Client simpleClient = null;
        try {
            simpleClient = AliPayUtil.simpleClient(appId, merchantPrivateKey, alipayPublicKey, notifyUrl);
            com.alipay.easysdk.payment.common.Client client = new com.alipay.easysdk.payment.common.Client(simpleClient);
            AlipayTradeRefundResponse response = client.refund(charge.getOutTradeNo(), String.valueOf(charge.getAmount()));
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                System.out.println(JSON.toJSONString(response));
            } else {
                System.err.println("调用失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Object queryRefund(ChargeRequest charge) throws Exception{
        String appId = charge.getChannelAppId();
        String merchantPrivateKey = charge.getChannelPrivateKey();
        String alipayPublicKey = charge.getChannelPublicKey();
        String notifyUrl = NotifyUrl.ALI_PAY_NOTIFY_URL;
        Client simpleClient = null;
        try {
            simpleClient = AliPayUtil.simpleClient(appId, merchantPrivateKey, alipayPublicKey, notifyUrl);
            com.alipay.easysdk.payment.common.Client client = new com.alipay.easysdk.payment.common.Client(simpleClient);
            AlipayTradeFastpayRefundQueryResponse response = client.queryRefund(charge.getOutTradeNo(),charge.getOutTradeNo());
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                System.out.println(JSON.toJSONString(response));
            } else {
                System.err.println("调用失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return null;
    }
}
