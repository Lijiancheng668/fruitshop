package com.greenleaf.fruitshop.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Privilege {
    private Integer privId;
    private String name;
    private String code;
    private String url;
    private Integer parentId;
    private Date created;
    private Date updated;
    private Integer isParent;
}
