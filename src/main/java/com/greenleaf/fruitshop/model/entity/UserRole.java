package com.greenleaf.fruitshop.model.entity;

import lombok.Data;

@Data
public class UserRole {
    private Integer userId;
    private String RoleId;
    private String created;
    private String updated;
}
