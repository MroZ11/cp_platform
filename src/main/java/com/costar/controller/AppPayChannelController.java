package com.costar.controller;

import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.dao.AppPayChannelDao;
import com.costar.model.AppPayChannel;
import com.costar.service.AppPayChannelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created by cloud on 2020/10/16.
 */
@RestController
@RequestMapping("appPayChannel")
public class AppPayChannelController {

    @Resource
    AppPayChannelService appPayChannelService;

    @Resource
    AppPayChannelDao appPayChannelDao;


    /**
     * 新增或修改
     *
     * @return
     */
    @PostMapping("/save")
    public String save(@RequestBody AppPayChannel resource) {
        return SuccessRe.createRe(ReCode.SAVE_DATA_SUCCESS, appPayChannelService.save(resource)).toJsonStr();
    }

}
