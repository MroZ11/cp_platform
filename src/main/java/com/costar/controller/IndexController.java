package com.costar.controller;

import com.costar.dao.UserInfoDao;
import com.costar.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * Created by cloud on 2019/4/22.
 */
@Controller
public class IndexController {

    @Resource
    UserInfoDao infoDao;

    @GetMapping("/")
    public String welcome(Principal principal, ModelMap modelMap) {
        modelMap.addAttribute("nickName", infoDao.findById(principal.getName()).map(UserInfo::getNickName).orElse(principal.getName()));
        return "home";
    }

    @GetMapping("/board")
    public String board() {
        return "board";
    }

    @RequestMapping("/login/index")
    public String login(String loginerr, ModelMap map) {
        if (!StringUtils.isEmpty(loginerr)) {
            map.put("loginerr", "账号密码错误");
        }
        return "account/login";
    }


    /**
     * account 相关页面 放在account下
     *
     * @param pageName 页面名称 对应html名称
     * @param modelMap 有需要可根据pageName传递参数
     * @return
     */
    @GetMapping("/account/{pageName}")
    public String account(@PathVariable String pageName, ModelMap modelMap) {
        return "account/" + pageName;
    }


    /**
     * module 相关页面 放在module下
     *
     * @param pageName 页面名称 对应html名称
     * @param modelMap 有需要可根据pageName传递参数
     * @return
     */
    @GetMapping("/module/{pageName}")
    public String module(@PathVariable String pageName, ModelMap modelMap) {
        return "module/" + pageName;
    }


    /**
     * user 相关页面 放在user下
     *
     * @param pageName 页面名称 对应html名称
     * @param modelMap 有需要可根据pageName传递参数
     * @return
     */
    @GetMapping("/user/{pageName}")
    public String user(@PathVariable String pageName, ModelMap modelMap) {
        return "user/" + pageName;
    }


    /**
     * notice 相关页面 放在notice下
     *
     * @param pageName 页面名称 对应html名称
     * @param modelMap 有需要可根据pageName传递参数
     * @return
     */
    @GetMapping("/notice/{pageName}")
    public String notice(@PathVariable String pageName, ModelMap modelMap) {
        return "notice/" + pageName;
    }


    /**
     * user_app 相关页面 放在admin下
     *
     * @param pageName 页面名称 对应html名称
     * @param modelMap 有需要可根据pageName传递参数
     * @return
     */
    @GetMapping("/user_app/{pageName}")
    public String admin(@PathVariable String pageName, ModelMap modelMap) {
        return "user_app/" + pageName;
    }


    /**
     * invoice 相关页面 放在invoice下
     *
     * @param pageName 页面名称 对应html名称
     * @param modelMap 有需要可根据pageName传递参数
     * @return
     */
    @GetMapping("/invoice/{pageName}")
    public String invoice(@PathVariable String pageName, ModelMap modelMap) {
        return "invoice/" + pageName;
    }


}
