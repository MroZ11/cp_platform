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
public class Refund {

    //在本平台的唯一id
    @Id
    @Column(name = "id")
    private String id;

    //退款的订单号 有本平台生成
    private String orderNo;

    private Timestamp createTime;

    //退款金额 单位元
    private Double amount;

    private Boolean succeed;

    //（目前支持三种状态: pending: 处理中; succeeded: 成功; failed: 失败）。
    private String status;

    private Timestamp  succeedTime;


    //错误类型
    private String failureType;

    //错误原因
    private String failureCode;

    //描述
    private String failureMsg;


    //发送给渠道时,渠道同步返回结果
    private String channelSyncReturn;

    //记录用户请求实体
    private String chargeSourceBody;

    //元数据  由客服端传入 回推时原样返回
    private String metadata;


    //关联对象
    private String chargeId;

}
