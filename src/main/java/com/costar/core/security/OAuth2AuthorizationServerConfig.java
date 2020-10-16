package com.costar.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * Created by cloud on 2019/6/27.
 *
 * 授权服务器配置
 *
 *
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 注入AuthenticationManager ，密码模式用到
     */
    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * 注入userDetailsService，开启refresh_token需要用到
     */
    @Resource
    private MyJdbcUserDetailsService userDetailsService;

    /**
     * 数据源
     */
    @Resource
    private DataSource dataSource;

    /**
     * 设置保存token的方式，一共有五种，这里采用数据库的方式
     */
    @Resource
    private TokenStore tokenStore;

    @Resource
    private MyJdbcClientDetailsService jdbcClientDetailsService;

    /**
     * 采用数据库方式保存token
     * @return
     */
    @Bean
    public TokenStore tokenStore() {

        return new JdbcTokenStore( dataSource );
    }




    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        /**
         * 配置oauth2服务跨域
         */
        CorsConfigurationSource source = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedHeader("*");
                corsConfiguration.addAllowedOrigin(request.getHeader( HttpHeaders.ORIGIN));
                corsConfiguration.addAllowedMethod("*");
                corsConfiguration.setAllowCredentials(true);
                //跨域请求时，如果超过maxAge,每次请求会发送OPTION预请求，这个请求时流浪器帮忙发起的
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }
        };

        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .addTokenEndpointAuthenticationFilter(new CorsFilter(source));
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //开启密码授权类型
        endpoints.authenticationManager(authenticationManager);
        //配置token存储方式
        endpoints.tokenStore(tokenStore);
        //自定义登录或者鉴权失败时的返回信息

        DefaultWebResponseExceptionTranslator  webResponseExceptionTranslator = new DefaultWebResponseExceptionTranslator();

        endpoints.exceptionTranslator(webResponseExceptionTranslator);
        //要使用refresh_token的话，需要额外配置userDetailsService
        endpoints.userDetailsService( userDetailsService );

        //四种授权模式 http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html


       /* 授权码模式（authorization code）
        简化模式（implicit）
        密码模式（resource owner password credentials）
        客户端模式（client credentials）*/

    }



}
