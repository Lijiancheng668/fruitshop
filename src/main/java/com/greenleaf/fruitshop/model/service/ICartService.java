package com.greenleaf.fruitshop.model.service;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.entity.custom.Cart;

import javax.servlet.http.HttpServletRequest;

public interface ICartService {
    BSResult addToCart(FruitInfo fruitInfo, Cart cart, int buyNum);
    BSResult clearCart(HttpServletRequest request, String sessionName);
    BSResult deleteCartItem(int bookId, HttpServletRequest request);
}
