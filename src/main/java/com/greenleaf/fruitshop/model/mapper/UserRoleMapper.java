package com.greenleaf.fruitshop.model.mapper;

import com.greenleaf.fruitshop.model.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper {
    @Delete("delete from user_role where user_id = #{userId}")
    void delUserRoleBYId(int userId);
    @Insert("INSERT INTO user_role (user_id,role_id, created,updated) VALUES (#{userId},#{roleId}, now(), now())")
    void insertUserRole(UserRole userRole);
}
