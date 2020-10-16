package com.costar.api.v1.dto.request;

import lombok.Data;

/**
 * Created by cloud on 2020/10/15.
 */
@Data
public class ChargeCreate {
    String appId;
    String channel;
    String order_no;
    Double amount;
    String client_ip;
    String subject;
    String String;
    Object metadata;
}
