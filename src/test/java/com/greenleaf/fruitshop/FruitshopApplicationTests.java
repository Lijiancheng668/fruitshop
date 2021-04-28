package com.greenleaf.fruitshop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenleaf.fruitshop.model.mapper.UserMapper;
import com.greenleaf.fruitshop.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FruitshopApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void contextLoads() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",1);
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user: users) {
            System.out.println(user);
        }

    }

}
