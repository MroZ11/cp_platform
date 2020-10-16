package com.costar.controller;

import cn.hutool.core.util.StrUtil;
import com.costar.core.response.FailRe;
import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.core.utils.EPager;
import com.costar.dao.SysMsgDao;
import com.costar.model.SysMsg;
import com.costar.service.SysMsgService;
import com.costar.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by cloud on 2019/6/24.
 */
@RestController
@RequestMapping("msg")
public class SysMsgController {

    @Resource
    SysMsgService sysMsgService;
    @Resource
    SysMsgDao sysMsgDao;
    @Resource
    UserService userService;

    @RequestMapping("/add")
    public Object add(Principal principal,@RequestBody SysMsg sysMsg) {
        sysMsg.setSendUsername(principal.getName());
        try {
            sysMsgService.createAndSend(sysMsg);
        } catch (Exception e) {
            e.printStackTrace();
            return FailRe.createRe(ReCode.ADD_DATA_ERROR);
        }
        return SuccessRe.createRe(ReCode.ADD_DATA_SUCCESS);
    }



    @RequestMapping("/delete")
    public Object delete(@RequestBody String[] ids, Principal principal) {
        for (String sysMsyId : ids) {
            Optional<SysMsg> sysMsgOptional = sysMsgDao.findById(sysMsyId);
            if(sysMsgOptional.isPresent()){
                SysMsg sysMsg = sysMsgOptional.get();
                if(principal.getName().equals(sysMsg.getSendUsername())){
                    sysMsg.setDeletemarkSend(1);
                }
                if(principal.getName().equals(sysMsg.getReceiveUsername())){
                    sysMsg.setDeletemarkReceive(1);
                }
                sysMsgDao.saveAndFlush(sysMsg);
            }
        }
        return SuccessRe.createRe(ReCode.DELETE_DATA_SUCCESS);
    }

    @RequestMapping("/edit")
    public Object edit(Principal principal) {

        return null;
    }

    @RequestMapping("/list")
    public Object list(Principal principal, EPager ePager
            , @RequestParam(defaultValue = "createTime") String orderProp
            , @RequestParam(required = false) String orderDirection
            , @RequestParam(defaultValue = "receive") String receiveOrSend) {


        //默认为按发送时间降序
        Sort sort = null;

        if (!orderProp.equals("createTime") && StrUtil.isNotEmpty(orderDirection)) {
            //自定义排序(默认升序)
            Sort.Direction direction = StrUtil.equalsAnyIgnoreCase(orderDirection, "DESCENDING") ? Sort.Direction.DESC : Sort.Direction.ASC;
            //如果不是按时间排序
            sort = Sort.by(direction,orderProp);
            sort = sort.and(Sort.by(Sort.Order.desc("createTime")));
        }else{
            sort = Sort.by(Sort.Order.desc("createTime"));
        }

        PageRequest pg = PageRequest.of(ePager.getPage(), ePager.getSize(), sort);

        Page<SysMsg> sysMsgs = sysMsgDao.findAll((root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction();

            if (!userService.isSuperManager(principal.getName())) {
                predicate.getExpressions().add(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("receiveUsername"), principal.getName()),
                                criteriaBuilder.equal(root.get("sendUsername"), principal.getName())
                        )
                );
            }

            if (receiveOrSend.equals("receive")) {
                predicate.getExpressions().add(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("receiveUsername"), principal.getName()),
                                criteriaBuilder.equal(root.get("deletemarkReceive"), 0)
                        )
                );
            } else {
                predicate.getExpressions().add(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("sendUsername"), principal.getName()),
                                criteriaBuilder.equal(root.get("deletemarkSend"), 0)
                        )
                );
            }
            return predicate;
        }, pg);

        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, EPager.pageToEPager(ePager, sysMsgs)).toJsonStr();
    }


    @RequestMapping("/send")
    public Object reSend(String id) {
        try {
            sysMsgService.send(id);
        } catch (Exception e) {
            e.printStackTrace();
            return FailRe.createRe(ReCode.SEND_SYS_MESSAGE_ERROR);
        }
        return SuccessRe.createRe(ReCode.SEND_SYS_MESSAGE_SUCCESS);
    }


    @RequestMapping("/setRead")
    public Object setRead(@RequestBody String[] ids, Principal principal) {
        for (String sysMsyId : ids) {
            Optional<SysMsg> sysMsgOptional = sysMsgDao.findById(sysMsyId);
            if(sysMsgOptional.isPresent()){
                SysMsg sysMsg = sysMsgOptional.get();
                if(principal.getName().equals(sysMsg.getReceiveUsername())){
                    sysMsg.setIsread(1);
                }
                sysMsgDao.saveAndFlush(sysMsg);
            }
        }
        return SuccessRe.createRe(ReCode.SET_SYS_MESSAGE_ISREAD_SUCCESS);
    }


}
