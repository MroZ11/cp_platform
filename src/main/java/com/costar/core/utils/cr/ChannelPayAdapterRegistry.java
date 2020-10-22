package com.costar.core.utils.cr;

import com.costar.core.utils.cr.alipay.AliPay;
import com.costar.core.utils.cr.wxpay.WxPay;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cloud on 2019/12/19.
 * https://blog.csdn.net/wwwdc1012/article/details/82780560
 * <p>
 * 适配器模式 可以理解为对switch case的扩展 根据条件找到对应的适配器 对元数据或元对象进行处理
 */
public class ChannelPayAdapterRegistry {

    private List<ChannelPayAdapter> adapters = new LinkedList<ChannelPayAdapter>();

    public ChannelPayAdapterRegistry() {
        this.adapters.add(new AliPay());
        this.adapters.add(new WxPay());
    }

    public ChannelPayAdapterRegistry registryMoreChannelPayAdapter(ChannelPayAdapter other) {
        this.adapters.add(other);
        return this;
    }

    /**
     * 支付
     *
     * @param charge
     */
    public void doChannelPay(ChargeRequest charge) throws Exception {
        ChannelPayAdapter adapter = null;
        for (ChannelPayAdapter ad : this.adapters) {
            if (ad.support(charge)) {
                adapter = ad;
                adapter.doChannelPay(charge);
                break;
            }
        }
        if (adapter == null) {
            throw new IllegalArgumentException("没有支付适配器");
        }
    }

    /**
     * 查询
     *
     * @param charge
     */
    public void queryChannelPay(ChargeRequest charge) throws Exception {
        ChannelPayAdapter adapter = null;
        for (ChannelPayAdapter ad : this.adapters) {
            if (ad.support(charge)) {
                adapter = ad;
                adapter.queryChannelPay(charge);
                break;
            }
        }
        if (adapter == null) {
            throw new IllegalArgumentException("没有支付适配器");
        }
    }

    /**
     * 关闭订单
     *
     * @param charge
     */
    public void closeChannelPay(ChargeRequest charge) throws Exception {
        ChannelPayAdapter adapter = null;
        for (ChannelPayAdapter ad : this.adapters) {
            if (ad.support(charge)) {
                adapter = ad;
                adapter.closeChannelPay(charge);
                break;
            }
        }
        if (adapter == null) {
            throw new IllegalArgumentException("没有支付适配器");
        }
    }

    /**
     * 退款
     *
     * @param charge
     */
    public void refundChannelPay(ChargeRequest charge) throws Exception {
        ChannelPayAdapter adapter = null;
        for (ChannelPayAdapter ad : this.adapters) {
            if (ad.support(charge)) {
                adapter = ad;
                adapter.refundChannelPay(charge);
                break;
            }
        }
        if (adapter == null) {
            throw new IllegalArgumentException("没有支付适配器");
        }
    }

    /**
     * 查询退款
     *
     * @param charge
     */
    public void queryRefund(ChargeRequest charge) throws Exception {
        ChannelPayAdapter adapter = null;
        for (ChannelPayAdapter ad : this.adapters) {
            if (ad.support(charge)) {
                adapter = ad;
                adapter.queryRefund(charge);
                break;
            }
        }
        if (adapter == null) {
            throw new IllegalArgumentException("没有支付适配器");
        }
    }

}
