package com.costar.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by cloud on 2019/6/24.
 */
@Entity
@Table(name = "sys_msg")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
public class SysMsg {
    private String id;
    private String receiveUsername;
    private String title;
    private String content;
    private String url;
    private String sub;
    private String sendUsername;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp sendTime;
    private Integer isread;
    private Integer deletemarkReceive;
    private Integer deletemarkSend;
    private Integer msgtype;
    private String sendGroup;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "RECEIVE_USERNAME")
    public String getReceiveUsername() {
        return receiveUsername;
    }

    public void setReceiveUsername(String receiveUsername) {
        this.receiveUsername = receiveUsername;
    }


    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "SUB")
    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    @Basic
    @Column(name = "SEND_USERNAME")
    public String getSendUsername() {
        return sendUsername;
    }

    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername;
    }

    @Basic
    @Column(name = "CREATE_TIME")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "SEND_TIME")
    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "ISREAD")
    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    @Basic
    @Column(name = "DELETEMARK_RECEIVE")
    public Integer getDeletemarkReceive() {
        return deletemarkReceive;
    }

    public void setDeletemarkReceive(Integer deletemarkReceive) {
        this.deletemarkReceive = deletemarkReceive;
    }

    @Basic
    @Column(name = "DELETEMARK_SEND")
    public Integer getDeletemarkSend() {
        return deletemarkSend;
    }

    public void setDeletemarkSend(Integer deletemarkSend) {
        this.deletemarkSend = deletemarkSend;
    }

    @Basic
    @Column(name = "MSGTYPE")
    public Integer getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(Integer msgtype) {
        this.msgtype = msgtype;
    }

    @Basic
    @Column(name = "SEND_GROUP")
    public String getSendGroup() {
        return sendGroup;
    }

    public void setSendGroup(String sendGroup) {
        this.sendGroup = sendGroup;
    }


}
