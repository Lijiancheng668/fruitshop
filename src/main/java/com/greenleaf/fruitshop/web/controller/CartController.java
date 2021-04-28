package com.greenleaf.fruitshop.web.controller;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.entity.custom.Cart;
import com.greenleaf.fruitshop.model.service.IFruitInfoService;
import com.greenleaf.fruitshop.model.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private IFruitInfoService fruitInfoService;

    @Autowired
    private ICartService cartService;
    //返回购物差页面
    @GetMapping("/items")
    public String showCart() {
        return "cart";
    }
    /**
     * 加入购物车
     *
     * @param fruitId
     * @param request
     * @return
     */
    @RequestMapping("/addition")
    public String addToCart(@RequestParam(value = "fruitId",defaultValue = "0") int fruitId,
                            @RequestParam(value = "storeId",defaultValue = "0") int storeId,
                            @RequestParam(required = false,defaultValue = "0") int buyNum,
                            HttpServletRequest request) {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //根据要加入购物车的bookId查询bookInfo
        FruitInfo fruitInfo = fruitInfoService.queryBookAvailable(fruitId,storeId);

        if (fruitInfo != null) {
            //这本书在数据库里
            BSResult bsResult = cartService.addToCart(fruitInfo, cart, buyNum);
            request.getSession().setAttribute("cart", bsResult.getData());
            request.setAttribute("fruitInfo", fruitInfo);
        } else {
            //数据库里没有这本书,或库存不足
            request.setAttribute("bookInfo", null);
        }
        return "addcart";
    }
    @GetMapping("/deletion/{bookId}")
    public String deleteCartItem(@PathVariable("bookId") int bookId, HttpServletRequest request){
        cartService.deleteCartItem(bookId, request);
        return "redirect:/cart/items";
    }
    @GetMapping("/clear")
    public String clearCart(HttpServletRequest request) {
        cartService.clearCart(request,"cart");
        return "cart";
    }


}
