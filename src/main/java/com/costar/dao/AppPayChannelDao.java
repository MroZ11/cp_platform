package com.costar.dao;

import com.costar.model.AppPayChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by cloud on 2020/10/16.
 */
public interface AppPayChannelDao extends JpaRepository<AppPayChannel, String> {

    int countByAppIdAndChannelCodeAndEnable(String appId,String channelType,boolean enable);

    Optional<AppPayChannel> getByAppIdAndChannelCode(String appId, String channelCode);
}