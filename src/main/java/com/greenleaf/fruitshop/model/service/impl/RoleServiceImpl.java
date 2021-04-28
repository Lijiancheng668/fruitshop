package com.greenleaf.fruitshop.model.service.impl;

import com.greenleaf.fruitshop.model.entity.Role;
import com.greenleaf.fruitshop.model.mapper.RoleMapper;
import com.greenleaf.fruitshop.model.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> findAllRoles() {
        return roleMapper.selectList(null);
    }
}
