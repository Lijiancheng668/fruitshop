package com.greenleaf.fruitshop.model.mapper;

import com.greenleaf.fruitshop.model.entity.Role;
import com.greenleaf.fruitshop.model.entity.custom.OrderCustom;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomMapper {
    List<OrderCustom> findOrdersByUserId(int userId);

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
    List<OrderCustom> findOrdersByStoreId(int storeId);

}
