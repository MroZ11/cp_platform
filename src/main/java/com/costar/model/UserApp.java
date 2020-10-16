package com.costar.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by cloud on 2019/7/1.
 */
@Entity
@Table(name = "user_app")
@Getter
@Setter
public class UserApp {
    @Id
    private String appId;
    private String ownerUserName;
    private String appName;
    private String notes;
    private String notifyUrl;
    private String ipWhiteList;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
}
