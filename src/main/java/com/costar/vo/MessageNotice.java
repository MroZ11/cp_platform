package com.costar.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by cloud on 2019/6/25.
 */
@Getter
@Setter
@Builder
public class MessageNotice  {

    private String title;
    private String source;
    private String sendUserName;
    private String receiveUserName;
    private String readMoreUrl;


}
