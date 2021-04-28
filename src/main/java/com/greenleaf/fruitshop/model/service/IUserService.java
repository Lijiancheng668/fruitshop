package com.greenleaf.fruitshop.model.service;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.model.entity.User;

import java.util.List;

public interface IUserService {
    BSResult compareAndChange(int userId, String oldPassword, String newPassword);
    BSResult updateUser(User user);

    List<User> findAllUsers();

    User findById(int userId);

    void delUser(int userId);

    BSResult saveUser(User user);

    BSResult checkUserExistByUsername(String username);
}
