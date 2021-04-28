package com.greenleaf.fruitshop.model.entity;

import lombok.Data;

@Data
public class Role {
    private Integer roleId;
    private String name;
    private String code;
    private String description;
    private String created;
    private String updated;
}
