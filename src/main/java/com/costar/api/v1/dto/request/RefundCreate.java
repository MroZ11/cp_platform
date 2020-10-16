package com.costar.api.v1.dto.request;

import lombok.Data;

/**
 * Created by cloud on 2020/10/15.
 */
@Data
public class RefundCreate {
    String appId;
    String chargeId;
    String outTradeNo;
}
