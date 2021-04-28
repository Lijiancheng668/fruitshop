package com.greenleaf.fruitshop.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
@Data
public class Store {
    private Integer storeId;

    private Integer storeManagerId;


    private String storeTelephone;

    private String storeName;

    private String storePosition;

    private Date created;

    private Date updated;

    @TableField(exist = false)
    private String storeManagerName;

}