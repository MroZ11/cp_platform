package com.costar.core.websocket;

/**
 * Created by cloud on 2019/6/24.
 */
public class WsConfig {

    /**
     * END_POINT
     */
    public static String END_POINT = "websocket";

    /**
     * 单用户前缀
     */
    public static String USER_DESTINATION_PREFIX = "/user";

    /**
     * 单用户一对一
     */
    public static String USER_DESTINATION = "/message";

    /**
     * 公告消息 用于群发所有用户都能收到
     */
    public static String NOTICE_DESTINATION = "/notice";

    public static String[] DESTINATION_PREFIXES = {NOTICE_DESTINATION, USER_DESTINATION_PREFIX, USER_DESTINATION};


}
