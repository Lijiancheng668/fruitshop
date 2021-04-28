package com.greenleaf.fruitshop.model.service;

import com.greenleaf.fruitshop.model.entity.FruitInfo;

import java.util.List;

public interface IOrderDetailService {
    List<FruitInfo> findBooksByOrderId(String orderId);
}
