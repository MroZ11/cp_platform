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
public class Charge {


    //在本平台的唯一id
    @Id
    @Column(name = "id")
    private String id;

    private Timestamp createTime;

    //外部订单号 由客服端传入
    private String outTradeNo;

    private String channelNo;

    private String channelStatus;

    private String clientIp;

    private String subject;

    private String body;

    //退款金额 单位元
    private Double amount;

    //元数据  由客服端传入 回推时原样返回
    private String metadata;

    private Boolean paid;

    private Boolean refunded;

    private Boolean reversed;

    private Timestamp paidTime;

    private Timestamp refundTime;

    private Timestamp reversedTime;

    //发送给渠道时,渠道同步返回结果
    private String channelSyncReturn;

    //记录用户请求实体
    private String chargeSourceBody;

    //错误类型
    private String failureType;

    //错误原因
    private String failureCode;

    //描述
    private String failureMsg;


    //关联对象
    private String userAppId;

    private String appPayChannelId;

    private String appPayChannel;


}
