package com.greenleaf.fruitshop.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.common.utils.BSResultUtil;
import com.greenleaf.fruitshop.model.entity.Store;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.entity.UserRole;
import com.greenleaf.fruitshop.model.mapper.StoreMapper;
import com.greenleaf.fruitshop.model.mapper.UserMapper;
import com.greenleaf.fruitshop.model.mapper.UserRoleMapper;
import com.greenleaf.fruitshop.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private StoreMapper storeMapper;
    /**
     * 修改密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public BSResult compareAndChange(int userId, String oldPassword, String newPassword) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id",userId));
        String password = user.getPassword();
        if (password.equals(oldPassword)) {
            user.setPassword(newPassword);
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            userMapper.update(user, wrapper);
            return BSResultUtil.build(200, "修改密码成功");
        } else {
            return BSResultUtil.build(400, "旧密码不正确");
        }
    }

    @Override
    public BSResult updateUser(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getUserId());
        userMapper.update(user,queryWrapper);
        return BSResultUtil.build(200, "保存成功");
    }

    @Override
    public List<User> findAllUsers() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("active",1);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public User findById(int userId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public void delUser(int userId) {
        userRoleMapper.delUserRoleBYId(userId);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id",userId);
        User user = new User();
        user.setActive("0");
        userMapper.update(user,updateWrapper);
    }

    /**
     * 注册用户,向数据库插入一条记录
     *
     * @param user
     * @return
     */
    @Override
    public BSResult saveUser(User user) {
        user.setPassword(user.getPassword());
        user.setActive("1");
        user.setLocation("xxx");
        user.setDetailAddress("xxx");
        userMapper.insertUser(user);
        if (!StringUtils.isEmpty(user.getIdentity())){
            UserRole userRole = new UserRole();
            if (user.getIdentity().equals("ordinary")){
                userRole.setRoleId("2");
            }else if (user.getIdentity().equals("business")){
                userRole.setRoleId("3");
                Store store = new Store();
                store.setStoreManagerId(user.getUserId());
                store.setStoreManagerName(user.getUsername());
                store.setStoreName("新店铺");
                store.setStorePosition(user.getLocation());
                store.setCreated(new Date());
                store.setUpdated(new Date());
                storeMapper.insert(store);
            }
            userRole.setUserId(user.getUserId());
            userRoleMapper.insertUserRole(userRole);
        }
        return BSResultUtil.success(user);
    }

    /**
     * 检查用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public BSResult checkUserExistByUsername(String username) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(queryWrapper);

        if (users == null || users.size() == 0) {
            return BSResultUtil.build(200, "用户名可以使用", true);
        } else {
            return BSResultUtil.build(400, "用户名已被注册", false);
        }
    }
}
