package com.greenleaf.fruitshop.model.entity;

import lombok.Data;

import java.util.Date;
@Data
public class FruitDesc {
    private Integer fruitId;
    private String fruitDesc;
    private Date created;
    private Date updated;
}
