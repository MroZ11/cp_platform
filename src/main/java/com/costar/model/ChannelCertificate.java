package com.costar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by cloud on 2020/10/15.
 */
@Entity
@Getter
@Setter
public class ChannelCertificate {
    @Id
    @Column(name = "id")
    private String id;

    private String appPayChannelId;

    //微信p12
    private byte[] wxP12;

    //支付宝公钥
    private byte[] aliPublicCrt;

    //支付宝根证书
    private byte[] aliRootCrt;

    //支付宝应用公钥证书
    private byte[] aliAppPublicCrt;

}
