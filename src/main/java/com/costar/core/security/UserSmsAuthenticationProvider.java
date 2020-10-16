package com.costar.core.security;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 短信验证
 */
@Component
public class UserSmsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String smscode = (String) authentication.getCredentials();
        if(StrUtil.isBlank(username)){
            throw new UsernameNotFoundException("用户名不可以为空");
        }
        if(StrUtil.isBlank(smscode)){
            throw new BadCredentialsException("短信不可以为空");
        }
        //获取用户信息 实际username应该是手机号码 用手机号码查询用户
        UserDetails user = userDetailsService.loadUserByUsername(username);

        //TODO 这里改成实际的短信验证码（短信验证码，过期时间，保存到用户信息中）
        //TODO 短信验证逻辑可封装成一个组件，这样就到处使用,需要控制一个验证码只使用一次
        String smscodeCache = "12q34";

        //比较前端传入的密码明文和数据库中加密的密码是否相等
        if (!passwordEncoder.matches(smscode, smscodeCache)) {
            //发布密码不正确事件
            throw new BadCredentialsException("sms_code验证码不正确");
        }

        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, smscode, authorities);
    }

    /**
     * @Description 如果该AuthenticationProvider支持传入的Authentication对象，则返回true
     * @Date 2019/7/5 15:18
     * @Version  1.0
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
