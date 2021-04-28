package com.greenleaf.fruitshop.model.service.impl;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.common.utils.BSResultUtil;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.entity.custom.Cart;
import com.greenleaf.fruitshop.model.entity.custom.CartItem;
import com.greenleaf.fruitshop.model.service.ICartService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class CartServiceImpl implements ICartService {
    @Override
    public BSResult addToCart(FruitInfo fruitInfo, Cart cart, int buyNum) {

        //购物车为空，新建一个
        if (cart == null) {
            cart = new Cart();
        }
        Map<String, CartItem> cartItems = cart.getCartItems();
        double total = 0;
        if (cartItems.containsKey(fruitInfo.getFruitId()+"-"+fruitInfo.getStoreId())) {
            CartItem cartItem = cartItems.get(fruitInfo.getFruitId()+"-"+fruitInfo.getStoreId());
            cartItem.setBuyNum(cartItem.getBuyNum() + buyNum);
            cartItem.setSubTotal(cartItem.getBuyNum() * fruitInfo.getPrice().doubleValue());
            cartItem.setFruitInfo(fruitInfo);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setBuyNum(buyNum);
            cartItem.setFruitInfo(fruitInfo);
            cartItem.setSubTotal(fruitInfo.getPrice().doubleValue() * buyNum);
            cartItems.put(fruitInfo.getFruitId()+"-"+fruitInfo.getStoreId(), cartItem);
        }
        for (CartItem cartItem : cartItems.values()) {
            total += cartItem.getSubTotal();
        }
        cart.setTotal(total);

        return BSResultUtil.success(cart);

    }

    @Override
    public BSResult clearCart(HttpServletRequest request, String sessionName) {
        request.getSession().removeAttribute(sessionName);
        return BSResultUtil.success();
    }

    @Override
    public BSResult deleteCartItem(int bookId, HttpServletRequest request) {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Map<String, CartItem> cartItems = cart.getCartItems();
        if (cartItems.containsKey(bookId)) {
            CartItem cartItem = cartItems.get(bookId);
            cart.setTotal(cart.getTotal() - cartItem.getSubTotal());
            cartItems.remove(bookId);
        }
        request.getSession().setAttribute("cart", cart);
        return BSResultUtil.success();
    }
}
