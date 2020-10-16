package com.costar.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by cloud on 2018/4/3.
 */
@Controller
public class WebSocketController {

    @Resource
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notice")
    @SendTo("/websocket/notice")
    public String notice(String content) {
        System.out.println("send:"+content);
        return new Date().toLocaleString() + "</br>" + content;
    }


    @MessageMapping("/message")
    @SendToUser("/message")
    public String message(String content) {
        //simpMessagingTemplate.convertAndSendToUser("s","message","我是你爸爸");
        System.out.println("send:"+content);
        return new Date().toLocaleString() + "</br>" + content;
    }



}
