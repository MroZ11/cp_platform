package com.costar.service;

import com.costar.core.response.ReCode;

/**
 * Created by cloud on 2019/7/23.
 */
public interface UserInfoService {


    /**
     * 发送邮箱验证码
     *
     * @param username
     * @return
     */
    ReCode sendCheckEmail(String username);

    /**
     * 核验邮箱地址验证
     *
     * @param username
     * @param code
     * @return
     */
    ReCode checkEmailAddress(String username, String code);


}
