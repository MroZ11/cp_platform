package com.costar.core.security;

import com.costar.core.response.FailRe;
import com.costar.core.response.ReCode;
import com.costar.core.response.SuccessRe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Security自定义配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)     //是注解权限控制生效
public class MySecurityConf extends WebSecurityConfigurerAdapter {

    /*@Resource
    private DataSource dataSource;*/

    @Resource
    private MyJdbcUserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserSmsAuthenticationProvider userSmsAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(new CorsConfigurationSource(){
                @Nullable
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
            })
        .and()
            .formLogin()
                .loginPage("/login/index")      //登陆页面(根据这个值会设置默认的ProcessingUrl failureUrl loginout成功地址)
                .loginProcessingUrl("/login")  // 自定义的登录接口
                .defaultSuccessUrl("/")     //成功地址 如果是ajax请求
                .successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                    //返回json格式 带跳转路径的
                    response.setStatus(200);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().append(SuccessRe.createRe(ReCode.LOGIN_SUCCESS).toJsonStr());

                })
                .failureHandler((HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) -> {
                    //返回json格式 带跳转路径的
                    response.setStatus(200);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().append(FailRe.createRe(ReCode.LOGIN_ERROR_USER_NOT_EXIST_OR_PASSWORD_WRONG).toJsonStr());
                })
            .and()
                .authorizeRequests()            //认证请求
                .antMatchers("/login/index", "/account/*", "error").permitAll()   //允许所有人
                .anyRequest().authenticated()   //其他请求所有都需要通过鉴权
            .and()
                .headers()
                .frameOptions()
                .disable()  //允许iframe呈现
            .and()
                .oauth2ResourceServer()
                .jwt().decoder(new NimbusJwtDecoderJwkSupport("http://1.c"))
        ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //TODO 修改为正确的加密方法
        return NoOpPasswordEncoder.getInstance();
    }




    /**
     * 将userDetailsService配置成自己的userDetailsService
     * <P> 新版本需要指定passwordEncoder加密方式<P/>
     * {@link org.springframework.security.crypto.password.PasswordEncoder}
     * 有官方的实现也可以自定义
     *
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //允许分组权限
        userDetailsService.setEnableGroups(true);
        //配置已个内部的验证器，该验证器会在修改密码时核对旧密码，这里改直接引用父类
        userDetailsService.setAuthenticationManager((usernamePasswordAuthenticationToken) -> {
            try {
                super.authenticationManager().authenticate(usernamePasswordAuthenticationToken);
            } catch (Exception e) {
                throw new BadCredentialsException("密码不匹配");
            }
            return usernamePasswordAuthenticationToken;
        });
        //TODO 密码加密
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        //增加短信验证 notice 实际是add操作,不会影响以后的验证提供者，链式验证，每一个验证Provider都会去验证一次，都不通过会返错误
        auth.authenticationProvider(userSmsAuthenticationProvider);

        //.withDefaultSchema() 不要用这个 这个会去建表 手动建表就行
        /*auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());*/
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/static/**");//忽略静态路径
    }
}
