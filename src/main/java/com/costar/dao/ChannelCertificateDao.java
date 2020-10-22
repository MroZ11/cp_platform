package com.costar.dao;

import com.costar.model.ChannelCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by cloud on 2020/10/20.
 */
public interface ChannelCertificateDao extends JpaRepository<ChannelCertificate, String> {

    @Query(value = "SELECT t.wxp12 FROM channel_certificate t where t.app_pay_channel_id = ?1 ",nativeQuery = true)
    byte[] findWxP12ByAppPayChannelId(String appPayChannelId);

}
