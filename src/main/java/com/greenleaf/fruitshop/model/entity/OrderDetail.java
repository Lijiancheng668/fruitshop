package com.greenleaf.fruitshop.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderDetail {
    private Integer orderDetailId;

    /**
     * 订单号
     */
    private String orderNumber;

    private String orderId;

    private Integer fruitId;

    private Integer storeId;

    /**
     * 卖出数量
     */
    private Integer mount;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    /**
     * 支付、发货状态
     */
    private String postStatus;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 收货状态
     */
    private String receiveStatus;

    private String imageUrl;

    private String fruitName;
}