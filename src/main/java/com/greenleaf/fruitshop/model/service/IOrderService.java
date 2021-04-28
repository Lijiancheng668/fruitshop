package com.greenleaf.fruitshop.model.service;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.entity.custom.Cart;
import com.greenleaf.fruitshop.model.entity.custom.OrderCustom;

import java.util.List;

public interface IOrderService
{
    BSResult createOrder(Cart cart, User user, String express, int payMethod);
    List<OrderCustom> findOrdersByUserId(int userId);
    BSResult deleteOrder(String orderId);
    BSResult findOrderById(String orderId);
    BSResult payOrderById(String orderId);
    BSResult confirmReceiving(String orderId);

    List<OrderCustom> findOrdersByStoreId(int storeId);
    OrderCustom findOrderCustomById(String orderId);
    BSResult postOrder(String orderId);
}
