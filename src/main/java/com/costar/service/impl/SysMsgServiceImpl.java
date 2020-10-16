package com.costar.service.impl;


import com.costar.core.websocket.WsConfig;
import com.costar.dao.SysMsgDao;
import com.costar.model.SysMsg;
import com.costar.service.SysMsgService;
import com.costar.vo.MessageNotice;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created by cloud on 2019/6/24.
 */
@Service
@Transactional
public class SysMsgServiceImpl implements SysMsgService{

    @Resource
    SysMsgDao sysMsgDao;

    @Resource
    SimpMessagingTemplate simpMessagingTemplate;


    public void createAndSend(SysMsg sysMsg) throws Exception {
        //填写默认值
        sysMsg.setCreateTime(new Timestamp(System.currentTimeMillis()));
        sysMsg.setDeletemarkReceive(0);
        sysMsg.setDeletemarkSend(0);
        sysMsg.setIsread(0);
        sysMsg.setMsgtype(1);
        sysMsgDao.saveAndFlush(sysMsg);
        send(sysMsg);
    }


    @Async
    public void send(String sysMsgId) throws Exception {
        SysMsg sysMsg = sysMsgDao.findById(sysMsgId).orElse(null);
        send(sysMsg);
    }


    @Async
    public void send(SysMsg sysMsg) throws Exception {
        sysMsg.setSendTime(new Timestamp(System.currentTimeMillis()));
        sysMsgDao.saveAndFlush(sysMsg);
        MessageNotice messageNotice = MessageNotice.builder()
                .receiveUserName(sysMsg.getReceiveUsername())
                .sendUserName(sysMsg.getSendUsername())
                .title(sysMsg.getTitle())
                .readMoreUrl("")
                .build();
        simpMessagingTemplate.convertAndSendToUser(sysMsg.getReceiveUsername(), WsConfig.USER_DESTINATION, messageNotice);
    }


}
