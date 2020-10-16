package com.costar.controller;

import cn.hutool.core.util.StrUtil;
import com.costar.core.response.FailRe;
import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import com.costar.core.utils.EPager;
import com.costar.dao.RoleDao;
import com.costar.model.Role;
import com.costar.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

/**
 * Created by cloud on 2019/6/17.
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    RoleDao roleDao;

    @Resource
    RoleService roleService;

    @Resource
    private EntityManager entityManager;

    @Resource
    private ClientDetailsService clientDetailsService;



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add() {


        return null;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody String[] ids) {
        for (String roleId : ids) {
            roleDao.deleteById(roleId);
        }
        return SuccessRe.createRe(ReCode.DELETE_DATA_SUCCESS).toJsonStr();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Object edit() {
        return null;
    }


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(EPager ePager
            , @RequestParam(defaultValue = "id") String orderProp
            , @RequestParam(required = false) String orderDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (!orderProp.equals("id") && StrUtil.isNotEmpty(orderDirection)) {
            //自定义排序(默认升序)
            direction = StrUtil.equalsAnyIgnoreCase(orderDirection, "DESCENDING") ? Sort.Direction.DESC : Sort.Direction.ASC;
        } else {
            orderProp = "id";
        }
        PageRequest pg = PageRequest.of(ePager.getPage(), ePager.getSize(), direction, orderProp);
        Page<Role> roles = roleDao.findAll(pg);
        return SuccessRe.createRe(ReCode.SEARCH_DATA_SUCCESS, EPager.pageToEPager(ePager, roles)).toJsonStr();
    }


    @RequestMapping(value = "/allotMoudlesAuthority/{roleId}", method = RequestMethod.POST)
    public String allotMoudlesAuthority(@PathVariable String roleId, @RequestBody String[] authorizedMoudlesIds) {
        try {
            roleService.allotRolesMoudles(roleId, authorizedMoudlesIds);
            return SuccessRe.createRe(ReCode.ALLOT_ROLES_MOUDLES_SUCCESS).toJsonStr();
        } catch (Exception e) {
            e.printStackTrace();
            FailRe failRe = FailRe.createRe(ReCode.ALLOT_ROLES_MOUDLES_ERROR);
            failRe.setDescribe(e.getMessage());
            return failRe.toJsonStr();
        }

    }


   /* QRole qRole = QRole.role;
    BooleanExpression expression = qRole.id.eq("2");
        roleDao.findOne(expression);
        ExpressionUtils.and(expression,qRole.id.endsWith("s").or(qRole.enable.eq(1)));

        new JPAQueryFactory(entityManager).selectFrom(qRole).where(
            qRole.id.isNotNull().and(
            qRole.enable.eq(1).or(qRole.enable.eq(2))
            )
            );*/

}
