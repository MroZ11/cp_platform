package com.costar.core.utils.cr;

/**
 * Created by cloud on 2020/10/16.
 */
public interface ChannelPayAccessAdapter {

    boolean support(ChargeRequest charge);

    Object doChannelPay(ChargeRequest charge);
}
