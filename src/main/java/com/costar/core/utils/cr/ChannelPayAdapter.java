package com.costar.core.utils.cr;

/**
 * Created by cloud on 2020/10/16.
 */
public interface ChannelPayAdapter {

    boolean support(ChargeRequest charge);

    Object doChannelPay(ChargeRequest charge) throws Exception;

    Object queryChannelPay(ChargeRequest charge) throws Exception;

    Object closeChannelPay(ChargeRequest charge) throws Exception;

    Object refundChannelPay(ChargeRequest charge) throws Exception;

    Object queryRefund(ChargeRequest charge) throws Exception;
}
