package com.costar.api.v1.dto.request;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by cloud on 2020/10/15.
 */
@Data
public class ChargeList {
    String appId;
    Integer pageSize;
    Integer page;
    String channel;
    Boolean paid;
    Boolean refunded;
    Boolean reversed;
    Timestamp starTime;
    Timestamp endTime;
}
