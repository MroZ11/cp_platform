package com.costar.service;

import com.costar.model.SysMsg;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by cloud on 2019/6/24.
 */
public interface SysMsgService {

    void createAndSend(SysMsg sysMsg) throws Exception;


    @Async
    void send(String sysMsgId) throws Exception;


    @Async
    void send(SysMsg sysMsg) throws Exception;


}
