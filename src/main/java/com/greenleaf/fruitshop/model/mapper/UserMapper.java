package com.greenleaf.fruitshop.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greenleaf.fruitshop.model.entity.Privilege;
import com.greenleaf.fruitshop.model.entity.Role;
import com.greenleaf.fruitshop.model.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    //角色拥有的权限列表
    @Select("SELECT\n" +
            "      p.priv_id AS privId,\n" +
            "      p.name,\n" +
            "      p.code,\n" +
            "      p.url,\n" +
            "      p.parent_id AS parentId,\n" +
            "      p.is_parent AS isParent,\n" +
            "      p.created,\n" +
            "      p.updated\n" +
            "    FROM\n" +
            "      `role_privilege` rp\n" +
            "    INNER JOIN `privilege` p ON rp.privilege_id = p.priv_id AND rp.role_id = #{roleId}")
    List<Privilege> findPrivilegesByRoleId(int roleId);
    //用户所属角色查询
    @Select("SELECT\n" +
            "      r.role_id AS roleId,\n" +
            "      r.name,\n" +
            "      r.code,\n" +
            "      r.description,\n" +
            "      r.created,\n" +
            "      r.updated\n" +
            "    FROM\n" +
            "      `user_role` ur\n" +
            "    INNER JOIN `role` r ON ur.role_id = r.role_id AND ur.user_id = #{userId}")
    List<Role> findRolesByUserId(int userId);
    void insertUser(User user);
}
