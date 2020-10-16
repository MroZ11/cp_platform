package com.costar.core.utils;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by cloud on 2019/6/14.
 */
public class SecurityUtils {

    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }




}
