package com.greenleaf.fruitshop.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private Integer weight;
    private Date created;
    private Date updated;
    private String state;
}
