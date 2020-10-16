package com.costar.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by cloud on 2019/6/25.
 */
@Entity
@Table(name = "user_info")
@Getter
@Setter
public class UserInfo {
    @Id
    @Column(name = "user_name")
    private String userName;
    @Basic
    @Column(name = "nick_name")
    private String nickName;
    @Basic
    @Column(name = "company_name")
    private String companyName;
    @Basic
    @Column(name = "sex")
    private Integer sex;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "id_card")
    private String idCard;
    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "birthday")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp birthday;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "industry")
    private String industry;
    @Basic
    @Column(name = "main_business")
    private String mainBusiness;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Basic
    @Column(name = "register_time")
    private Timestamp registerTime;

    @Basic
    @Column(name = "email_checked")
    private Integer emailChecked;


    @Basic
    @Column(name = "email_check_send_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp emailCheckSendTime;

    @Basic
    @Column(name = "email_check_code")
    private String emailCheckCode;

    @Basic
    @Column(name = "client_id")
    private String clientId;

}
