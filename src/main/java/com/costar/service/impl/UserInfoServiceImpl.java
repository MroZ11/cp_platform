package com.costar.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.costar.core.response.ReCode;
import com.costar.dao.UserInfoDao;
import com.costar.model.UserInfo;
import com.costar.service.UserInfoService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;

/**
 * Created by cloud on 2019/7/23.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    UserInfoDao userInfoDao;

    @Resource
    JavaMailSender javaMailSender;


    /**
     * 发送邮箱验证码
     *
     * @param username
     * @return
     */
    public ReCode sendCheckEmail(String username) {

        UserInfo userInfo = userInfoDao.findById(username).orElse(null);

        if (userInfo == null || StrUtil.isEmpty(userInfo.getEmail()) || !Validator.isEmail(userInfo.getEmail())) {
            return ReCode.SEND_SYS_MESSAGE_ERROR;
        }

        if (userInfo.getEmailChecked()!=null&&userInfo.getEmailChecked().equals(1)) {
            return ReCode.SEND_EMAIL_CHECK_ERROR_REPEATED_VERIFICATION;
        }

        //发送验证码
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String code = RandomUtil.randomString(4);
        try {
            helper.setFrom("zhoushu1118@qq.com");
            helper.setTo(userInfo.getEmail());
            helper.setSubject("这是您的验证码");
            helper.setText("您的邮箱验证码是：" + code + ",有效期5分钟");
            javaMailSender.send(message);
            userInfo.setEmailCheckSendTime(new Timestamp(System.currentTimeMillis()));
            userInfo.setEmailCheckCode(code);
            userInfoDao.saveAndFlush(userInfo);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ReCode.SEND_EMAIL_CHECK_SUCCESS;
    }

    /**
     * 核验邮箱地址验证
     *
     * @param username
     * @param code
     * @return
     */
    public ReCode checkEmailAddress(String username, String code) {
        UserInfo userInfo = userInfoDao.findById(username).orElse(null);
        if (userInfo == null) {
            return ReCode.CHECK_EMAIL_ADDRESS_ERROR;
        }

        if (userInfo.getEmailChecked()!=null&&userInfo.getEmailChecked().equals(1)) {
            return ReCode.CHECK_EMAIL_ADDRESS_ERROR_REPEATED_VERIFICATION;
        }
        //有效期5分钟
        boolean timeEffective = (System.currentTimeMillis() - userInfo.getEmailCheckSendTime().getTime()) < 1000 * 60 * 5;

        if (StrUtil.isNotEmpty(userInfo.getEmailCheckCode()) && StrUtil.equalsIgnoreCase(userInfo.getEmailCheckCode(), code) && timeEffective) {
            userInfo.setEmailChecked(1);
            userInfoDao.saveAndFlush(userInfo);
            return ReCode.CHECK_EMAIL_ADDRESS_SUCCESS;
        }
        return ReCode.CHECK_EMAIL_ADDRESS_ERROR_VERIFICATION_CODE_WRONG;
    }


}
