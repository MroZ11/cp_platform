package com.costar.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by cloud on 2019/6/13
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ReCode {

    ADD_DATA_SUCCESS(1000,"增加数据成功")
    ,ADD_DATA_ERROR(1000,"增加数据失败")
    ,DELETE_DATA_SUCCESS(2000,"删除数据成功")
    ,DELETE_DATA_ERROR(2001,"删除数据失败")
    ,UPDATE_DATA_SUCCESS(3000,"修改数据成功")
    ,UPDATE_DATA_ERROR(3001,"修改数据失败")
    ,SEARCH_DATA_SUCCESS(4000,"查询数据成功")
    ,SEARCH_DATA_ERROR(4001,"查询数据失败")
    ,SAVE_DATA_SUCCESS(4100,"保存数据成功")
    ,SAVE_DATA_ERROR(4101,"保存数据失败")

    ,PARAM_ERROR(9001,"参数错误")
    ,AUTHORITY_ERROR(8001,"权限错误")

    ,LOGIN_SUCCESS(10000,"登陆成功")
    ,LOGIN_ERROR(10001,"登陆失败")
    ,LOGIN_ERROR_USER_NOT_EXIST_OR_PASSWORD_WRONG(10002,"登陆失败,账号或密码错误")
    ,LOGIN_ERROR_USER_NOT_EXIST(10003,"登陆失败,账号不存在")
    ,LOGIN_ERROR_PASSWORD_WRONG(10004,"登陆失败,密码错误")
    ,LOGIN_ERROR_VERIFY_CODE_WRONG(10005,"登陆失败,验证码错误")

    ,REGIST_SUCCESS(20000,"注册成功")
    ,REGIST_ERROR(20001,"注册失败")
    ,REGIST_ERROR_USER_EXIST(20002,"注册失败,用户已存在")
    ,REGIST_ERROR_COMPANY_EXIST(20003,"注册失败,公司已存在")


    ,ALLOT_ROLES_MOUDLES_SUCCESS(30000,"分配角色菜单成功")
    ,ALLOT_ROLES_MOUDLES_ERROR(30001,"分配角色菜单失败")

    ,SEND_SYS_MESSAGE_SUCCESS(40000,"发送消息成功")
    ,SEND_SYS_MESSAGE_ERROR(40001,"发送消息失败")

    ,SET_SYS_MESSAGE_ISREAD_SUCCESS(50000,"设置已读成功")
    ,SET_SYS_MESSAGE_ISREAD_ERROR(50001,"设置已读失败")

    ,EDIT_PASSWORD_SUCCESS(60000,"修改密码成功")
    ,EDIT_PASSWORD_ERROR(60001,"修改密码失败")
    ,EDIT_PASSWORD_ERROR_OLD_PASSWORD_WRONG(60002,"修改密码失败,原密码错误")


    ,REQUEST_BILLING_EINVOICE_SUCCESS(70000,"请求开票成功")
    ,REQUEST_BILLING_EINVOICE_ERROR(70001,"请求开票失败")

    ,SUBMIT_EINVOICE_SUCCESS(70000,"提交开票请求成功")
    ,SUBMIT_EINVOICE_ERROR(70001,"提交开票请求失败")
    ,SUBMIT_EINVOICE_ERROR_REPEAT_SUBMIT(70002,"提交开票请求失败,重复提交")


    ,OBTAINING_THIRD_PARTY_AUTHORIZATION_SUCCESS(80000,"获取第三方授权成功")

    ,SEND_EMAIL_CHECK_SUCCESS(90000,"发送验证邮件成功")
    ,SEND_EMAIL_CHECK_ERROR(90001,"发送验证邮件失败")
    ,SEND_EMAIL_CHECK_ERROR_REPEATED_VERIFICATION(90002,"发送验证邮件失败,已通过验证")

    ,CHECK_EMAIL_ADDRESS_SUCCESS(100000,"验证邮件成功")
    ,CHECK_EMAIL_ADDRESS_ERROR(1000001,"验证邮件成功失败")
    ,CHECK_EMAIL_ADDRESS_ERROR_VERIFICATION_CODE_WRONG(1000002,"验证邮件成功失败,验证码错误")
    ,CHECK_EMAIL_ADDRESS_ERROR_REPEATED_VERIFICATION(1000003,"验证邮件成功失败,已通过验证")

    ;


    private int code;
    private String describe;
}
