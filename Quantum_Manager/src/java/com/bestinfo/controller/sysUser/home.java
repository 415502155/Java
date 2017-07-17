package com.bestinfo.controller.sysUser;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/home")
public class home {

    private final Logger logger = Logger.getLogger(home.class);

    @RequestMapping(value = "/index")
    public String toLogin(HttpServletRequest request, Model resModel) {
        return "/home/home";
    }

}
