package com.greenleaf.fruitshop.web.controller.admin;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.model.entity.Role;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.mapper.CustomMapper;
import com.greenleaf.fruitshop.model.service.IRoleService;
import com.greenleaf.fruitshop.model.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private CustomMapper customMapper;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/list")
    @RequiresPermissions("user-list")
    public String userList(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/user/list";
    }
    @RequestMapping("/echo/{userId}")
    @RequiresPermissions("user-edit")
    public String echo(@PathVariable("userId") int userId, Model model){
        User user = userService.findById(userId);
        List<Role> userRoles = customMapper.findRolesByUserId(userId);
        List<Role> allRoles = roleService.findAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("allRoles", allRoles);
        return "admin/user/edit";
    }
    @RequestMapping("/deletion/{userId}")
    @RequiresPermissions("user-delete")
    public String delUser(@PathVariable("userId") int userId){
        userService.delUser(userId);
        return "forward:../list";
    }
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/admin/index";
    }
    @RequestMapping("/update")
    @RequiresPermissions("user-edit")
    public String updateUser(User user, int[] roleIds, Model model){
        BSResult bsResult = userService.updateUser(user);
        if(bsResult.getCode() == 200){
            model.addAttribute("saveMsg","保存成功");
        }
        return "forward:/admin/user/echo/"+user.getUserId();
    }
}
