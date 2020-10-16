package com.costar.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by cloud on 2020/10/16.
 */
@Setter
@Getter
@Builder
public class AppPayChannelVo {

    String id;
    String appId;
    String channelCode;
    String channelName;
    String scene;
    Boolean opened;

}
