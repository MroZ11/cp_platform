package com.costar.core.utils.cr.wxpay.sdk;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by cloud on 2020/10/13.
 */
public class MyWxPayConfig extends WXPayConfig {

    private byte[] certData;

    private String appId;

    private String mchId;

    private String key;


    public MyWxPayConfig(String appId, String mchId, String key, byte[] certData) throws Exception {
        this.appId = appId;
        this.mchId = mchId;
        this.key = key;
        this.certData = certData;
    }

    public String getAppID() {
        return this.appId;
    }

    public String getMchID() {
        return this.mchId;
    }

    public String getKey() {
        return this.key;
    }


    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
    }
}
