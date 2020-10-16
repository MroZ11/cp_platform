package com.costar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

/**
 * Created by cloud on 2019/4/22.
 */
@Controller
public class LoginController {

    @Autowired
    private UserDetailsService userDetailsService;




}
