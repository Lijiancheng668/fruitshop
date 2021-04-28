package com.greenleaf.fruitshop.model.mapper.custom;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenleaf.fruitshop.model.entity.Privilege;
import com.greenleaf.fruitshop.model.entity.Role;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

// 自定义UserRealm
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("========执行授权========");
        //查询用户拥有的角色
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Role> roles =  userMapper.findRolesByUserId(user.getUserId());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (!CollectionUtils.isEmpty(roles)){
            for (Role role : roles) {
                authorizationInfo.addRole(role.getCode());
                //查询角色拥有的权限
                List<Privilege> privileges = userMapper.findPrivilegesByRoleId(role.getRoleId());
                System.out.println(privileges);
                if (!CollectionUtils.isEmpty(privileges)){
                    for (Privilege privilege : privileges) {
                        authorizationInfo.addStringPermission(privilege.getCode());
                    }
                }
            }

        }
        return authorizationInfo;
    }
    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("========执行认证========");
        //Principal 身份信息 用户名 标识具有唯一性
        String principal = (String) token.getPrincipal();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",principal);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null)  return null;//抛出UnknownAccountException 用户不存在
        //credential凭证信息 密码
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
    }
}
