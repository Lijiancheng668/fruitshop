package com.greenleaf.fruitshop.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FruitCategory {
    private Integer cateId;

    private String name;

    private Integer status;

    private Date created;

    private Date updated;
}
