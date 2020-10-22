package com.costar.service;

import com.costar.api.v1.dto.request.ChargeCreate;

import com.costar.api.v1.dto.response.ChargeResponse;


/**
 * Created by cloud on 2020/10/20.
 */
public interface ChargeService {

    ChargeResponse createCharge(ChargeCreate chargeCreate);

}
