package com.costar.api.v1.dto.request;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by cloud on 2020/10/15.
 */
@Data
public class RefundList {
    String appId;
    String chargeId;
    String outTradeNo;
    Integer pageSize;
    Integer page;
    String channel;
    Boolean reversed;
    Timestamp starTime;
    Timestamp endTime;
}
