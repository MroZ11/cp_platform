package com.costar.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by cloud on 2018/4/13.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {

    String fromUser;
    String content;
    String toUser;
    String url;//关联路径

}
