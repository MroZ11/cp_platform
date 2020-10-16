package com.costar.core.security;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by cloud on 2019/6/12.
 */
@Component("userDetailsService")
public class MyJdbcUserDetailsService extends JdbcUserDetailsManager {

    @Resource
    private DataSource dataSource;

    public MyJdbcUserDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    /*@Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        //如果想加入锁定和过期和凭证过期等改写这个方法 获取为验证对象附加内容

        return super.loadUsersByUsername(username);


    }*/


}
