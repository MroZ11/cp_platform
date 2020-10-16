package com.costar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by cloud on 2020/10/15.
 */
@Entity
@Getter
@Setter
public class AppPayChannel {
    @Id
    @Column(name = "id")
    private String id;
    private String appId;
    private String channelCode;
    private Timestamp createTime;

    //是否可用
    private boolean enable;

    //渠道方的APPID
    private String channelAppId;
    //渠道方的公钥
    private String channelPublicKey;
    //渠道方的私钥
    private String channelPrivateKey;
    //商户号，微信支付需要将商户号绑定到channelAppId下
    private String merchantNumber;

}
