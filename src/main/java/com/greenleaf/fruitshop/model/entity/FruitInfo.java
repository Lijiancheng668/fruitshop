package com.greenleaf.fruitshop.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FruitInfo {
    private Integer fruitId;

    private Integer fruitCategoryId;

    private Integer storeId;

    private String name;

    /**
     * 简介
     */
    private String outline;

    /**
     * 商品详情
     */
    private String detail;

    @TableField(exist = false)
    private String categoryName;

    private String place;

    private BigDecimal price;

    /**
     * 市场价\定价
     */
    private BigDecimal marketPrice;

    /**
     * 会员价格
     */
    private BigDecimal memberPrice;

    /**
     * 成交量
     */
    private Integer dealMount;


    /**
     * 版面图片
     */
    private String imageUrl;

    /**
     * 库存数量
     */
    private Integer storeMount;

    /**
     * 入库时间
     */
    private Date storeTime;

    /**
     * 封装方式
     */
    private String packStyle;

    /**
     * 是否上架
     */
    private Integer isShelf;
}
