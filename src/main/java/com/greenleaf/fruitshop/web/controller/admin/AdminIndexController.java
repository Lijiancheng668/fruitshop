package com.greenleaf.fruitshop.web.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @RequestMapping({"", "/", "/index"})
    @RequiresPermissions("system")
    public String adminIndex() {
        return "admin/index";
    }
}
