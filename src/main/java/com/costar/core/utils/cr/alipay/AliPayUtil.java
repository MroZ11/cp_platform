package com.costar.core.utils.cr.alipay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Client;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.Context;

/**
 * Created by cloud on 2020/10/13.
 */
public class AliPayUtil {


    public static Config simpleConfig(String appId, String merchantPrivateKey, String alipayPublicKey, String notifyUrl) {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";
        config.appId = appId;
        config.merchantPrivateKey = merchantPrivateKey;
        config.alipayPublicKey = alipayPublicKey;
        config.notifyUrl = notifyUrl;
        return config;
    }

    public static  Client simpleClient(String appId, String merchantPrivateKey, String alipayPublicKey, String notifyUrl) throws Exception{
        return new Client(new Context(simpleConfig(appId,merchantPrivateKey,alipayPublicKey,notifyUrl), Factory.SDK_VERSION));

    }

}
