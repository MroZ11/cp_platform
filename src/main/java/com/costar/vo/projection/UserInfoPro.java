package com.costar.vo.projection;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Value;

import java.util.Date;

/**
 * Created by cloud on 2019/6/26.
 */
@Value
public class  UserInfoPro {

    String  userName;

    String  companyName;

    String  address;

    String  email;

    String  city;

    String  industry;
    
    String  mainBusiness;

    String nickName;

    String clientId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    Date registerTime;

    Integer emailChecked;

}
