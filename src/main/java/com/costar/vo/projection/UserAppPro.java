package com.costar.vo.projection;

import com.costar.model.UserApp;
import lombok.Value;

/**
 * Created by cloud on 2019/6/28.
 */
@Value
public class UserAppPro extends UserApp {

    String name;

    String userName;


}
