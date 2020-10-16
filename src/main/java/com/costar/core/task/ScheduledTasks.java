package com.costar.core.task;

import com.costar.dao.UserAppDao;
import com.costar.service.SysMsgService;
import com.costar.service.UserAppService;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by cloud on 2019/7/1.
 */
@Component
@Log
public class ScheduledTasks {

    @Resource
    private UserAppService nnAppService;

    @Resource
    private UserAppDao userAppDao;


    @Resource
    private SysMsgService sysMsgService;


    /**
     * 间隔一分钟执行一次,自动取官网取开票结果
     */
    @Scheduled(fixedDelay = 60 * 1000)
    public void autoCheckEInvoice() {

    }


}
