package com.costar.core.security;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by cloud on 2019/7/1.
 *
 * oauthClientDetails注入
 *
 *
 */
@Component("jdbcClientDetailsService")
public class MyJdbcClientDetailsService extends JdbcClientDetailsService{

    @Resource
    private DataSource dataSource;


    public MyJdbcClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }
}
