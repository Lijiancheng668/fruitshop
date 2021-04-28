package com.greenleaf.fruitshop.web.controller;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.common.utils.BSResultUtil;
import com.greenleaf.fruitshop.model.entity.Store;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.service.IMailService;
import com.greenleaf.fruitshop.model.service.IStoreService;
import com.greenleaf.fruitshop.model.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IStoreService storeService;
    @Autowired
    private IMailService mailService;

    private final String USERNAME_PASSWORD_NOT_MATCH = "密码错误";

    private final String USERNAME_NOT_EXIT = "用户名不存在";
    private final String USERNAME_CANNOT_NULL = "用户名不能为空";

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletRequest request, Model model) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return "login";
        }
        //获取主体对象  自动给web安全工具注入安全管理器
        Subject userSubject = SecurityUtils.getSubject();
        if (!userSubject.isAuthenticated()) {
            //封装用户登录的数据
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                userSubject.login(token);
                User loginUser = (User) userSubject.getPrincipal();
                request.getSession().setAttribute("loginUser", loginUser);
                Store store = storeService.findStoreByUserId(loginUser.getUserId());
                request.getSession().setAttribute("loginStore", store);
                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                String url = "/";
                if (savedRequest != null) {
                    url = savedRequest.getRequestUrl();
                    if(url.contains(request.getContextPath())){
                        url = url.replace(request.getContextPath(),"");
                    }
                }
                if(StringUtils.isEmpty(url) || url.equals("/favicon.ico")){
                    url = "/";
                }

                return "redirect:" + url;

            } catch (UnknownAccountException  e) {
                model.addAttribute("loginMsg", USERNAME_NOT_EXIT);
                return "login";
            } catch (IncorrectCredentialsException e) {
                model.addAttribute("loginMsg", USERNAME_PASSWORD_NOT_MATCH);
                return "login";
            } catch (AuthenticationException ae) {
                model.addAttribute("loginMsg", "登录失败");
                return "login";
            }

        } else {
            //用户已经登录
            return "redirect:/index";
        }
    }
    @RequestMapping("/info")
    public String personInfo(){
        return "user_info";
    }
    //shiro框架帮我们注销
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/page/login";
    }

    @RequestMapping("/password/{userId}")
    @ResponseBody
    public BSResult changePassword(@PathVariable("userId") int userId, String oldPassword, String newPassword){
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)){
            return BSResultUtil.build(400, "密码不能为空");
        }
        BSResult bsResult = userService.compareAndChange(userId, oldPassword, newPassword);
        System.out.println(bsResult);
        return bsResult;
    }
    @RequestMapping("/update")
    @ResponseBody
    public BSResult updateUser(User user, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        loginUser.setNickname(user.getNickname());
        loginUser.setLocation(user.getLocation());
        loginUser.setDetailAddress(user.getDetailAddress());
        loginUser.setGender(user.getGender());
        loginUser.setUpdated(new Date());
        loginUser.setPhone(user.getPhone());
        loginUser.setIdentity(user.getIdentity());
        loginUser.setPhone(user.getPhone());
        BSResult bsResult = userService.updateUser(loginUser);
        session.setAttribute("loginUser", loginUser);
        return bsResult;
    }
    /**
     * 注册，发提示邮箱
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public String register(User user,Model model) {
        BSResult isExist = checkUserExist(user.getUsername());
        if ((Boolean) isExist.getData()) {
            userService.saveUser(user);
            try {
                mailService.sendSimpleMail(user.getEmail(), "<绿叶水果商城>---用户注册---",
                        "<html><body><a href=''>店主：闫志平，欢迎您前来注册！</a></body></html>");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("registerError", "发送邮件异常!");
                return "fail";
            }
        }
        return "register_success";
    }
    /**
     * 注册 检验用户名是否存在
     *
     * @param username
     * @return
     */
    @RequestMapping("/checkUserExist")
    @ResponseBody
    public BSResult checkUserExist(String username) {
        if (StringUtils.isEmpty(username)) {
            return BSResultUtil.build(200, USERNAME_CANNOT_NULL, false);
        }

        return userService.checkUserExistByUsername(username);
    }

}
