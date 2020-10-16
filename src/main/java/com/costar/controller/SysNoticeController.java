package com.costar.controller;

import cn.hutool.core.util.StrUtil;
import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.core.utils.EPager;
import com.costar.dao.SysNoticeDao;
import com.costar.model.QSysNotice;
import com.costar.model.SysNotice;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

/**
 * Created by cloud on 2019/7/23.
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeController {

    @Resource
    private SysNoticeDao sysNoticeDao;

    @RequestMapping("/add")
    public Object add(Principal principal, @RequestBody SysNotice sysNotice) {
        sysNotice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        sysNotice.setDeletemark(0);
        sysNotice.setCreateUsername(principal.getName());
        sysNoticeDao.saveAndFlush(sysNotice);
        return SuccessRe.createRe(ReCode.ADD_DATA_SUCCESS);
    }


    @RequestMapping("/delete")
    public Object delete(@RequestBody String[] ids) {
        for (String sysMsyId : ids) {
            Optional<SysNotice> sysNoticeOptional = sysNoticeDao.findById(sysMsyId);
            if (sysNoticeOptional.isPresent()) {
                SysNotice sysMsg = sysNoticeOptional.get();
                sysMsg.setDeletemark(1);
                sysNoticeDao.saveAndFlush(sysMsg);
            }
        }
        return SuccessRe.createRe(ReCode.DELETE_DATA_SUCCESS);
    }

    @RequestMapping("/edit")
    public Object edit(Principal principal) {

        return null;
    }


    @RequestMapping("/list")
    public Object list(EPager ePager
            , @RequestParam(defaultValue = "createTime") String orderProp
            , @RequestParam(required = false) String orderDirection) {

        PageRequest pg = ePager.buildDefaultPageRequestWithSort(orderProp, orderDirection);

        QSysNotice sysNotice = QSysNotice.sysNotice;
        BooleanExpression expression = sysNotice.id.isNotNull().and(sysNotice.deletemark.ne(1));
        Map<String, Object> search = ePager.getSearch();

        if (StrUtil.isNotEmpty(search.get("noticeType").toString())) {
            expression = expression.and(sysNotice.noticeType.eq(Integer.parseInt(search.get("noticeType").toString())));
        }

        Page<SysNotice> sysNotices = sysNoticeDao.findAll(expression, pg);
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, EPager.pageToEPager(ePager, sysNotices)).toJsonStr();
    }


}
