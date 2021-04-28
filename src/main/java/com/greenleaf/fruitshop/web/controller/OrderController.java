package com.greenleaf.fruitshop.web.controller;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.exception.BSException;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.entity.custom.Cart;
import com.greenleaf.fruitshop.model.entity.custom.OrderCustom;
import com.greenleaf.fruitshop.model.service.IFruitInfoService;
import com.greenleaf.fruitshop.model.service.ICartService;
import com.greenleaf.fruitshop.model.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private IFruitInfoService bookInfoService;
    @Autowired
    private IOrderService orderService;
    @RequestMapping("/deletion/{orderId}")
    public String deletion(@PathVariable("orderId") String orderId) {

        BSResult bsResult = orderService.deleteOrder(orderId);

        if (bsResult.getCode() == 200) {
            return "redirect:/order/list";
        }
        return "exception";
    }

    /**
     * 填写订单信息页面
     *
     * @param fruitId
     * @param buyNum
     * @param request
     * @return
     */
    @GetMapping("/info")
    public String orderInfo(@RequestParam(required = false, defaultValue = "0") int fruitId,
                            @RequestParam(required = false, defaultValue = "0") int buyNum,
                            HttpServletRequest request) throws BSException {

        if (fruitId != 0) {
            //点了立即购买，放到request域中，也session的立即购买域中以区分购物车中的书籍
            FruitInfo bookInfo = bookInfoService.findById(fruitId);
            if (bookInfo != null) {
                BSResult bsResult = cartService.addToCart(bookInfo, null, buyNum);
                request.getSession().setAttribute("buyNowCart", bsResult.getData());
                request.setAttribute("cart", bsResult.getData());
                return "order_info";
            } else {
                request.setAttribute("exception", "不好意思，书籍库存不足或不存在了！");
                return "exception";
            }
        }
        //没有点立即购买，购物车中的总金额大于0才让填写订单信息
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null && cart.getTotal() > 0) {
            return "order_info";
        } else {
            return "cart";
        }

    }
    @GetMapping("/payPage/{orderId}")
    public String toPay(@PathVariable("orderId") String orderId, Model model) {

        BSResult bsResult = orderService.findOrderById(orderId);

        if (bsResult.getCode() == 200) {
            model.addAttribute("order", bsResult.getData());
            return "payment";
        }
        return "exception";
    }
    /**
     * 创建订单
     *
     * @return
     */
    @PostMapping("/creation")
    public String createOrder(User userDTO, String express, int payMethod, HttpServletRequest request) {

        //立即购买,优先创建订单
        Cart buyNowCart = (Cart) request.getSession().getAttribute("buyNowCart");

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        userDTO.setUserId(loginUser.getUserId());

        if (buyNowCart != null) {
            BSResult bsResult = orderService.createOrder(buyNowCart, userDTO, express, payMethod);

            if (bsResult.getCode() == 200) {
                request.setAttribute("order", bsResult.getData());
                request.setAttribute("payMethod",payMethod);
                cartService.clearCart(request, "buyNowCart");
                return "payment";
            } else {
                request.setAttribute("exception", bsResult.getMessage());
                return "exception";
            }
        }

        //普通购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            BSResult bsResult = orderService.createOrder(cart, userDTO, express, payMethod);

            if (bsResult.getCode() == 200) {
                request.setAttribute("order", bsResult.getData());
                cartService.clearCart(request, "cart");
                return "payment";
            } else {
                request.setAttribute("exception", bsResult.getMessage());
                return "exception";
            }

        } else {
            request.setAttribute("exception", "购物车为空！");
            return "exception";
        }

    }
    /**
     * 订单列表
     *
     * @return
     */
    @GetMapping("/list")
    public String orderList(HttpServletRequest request) {

        User loginUser = (User) request.getSession().getAttribute("loginUser");

        List<OrderCustom> orderCustoms = orderService.findOrdersByUserId(loginUser.getUserId());

        request.setAttribute("orderCustoms", orderCustoms);

        return "order_list";
    }
    /**
     * 确认收货
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/confirm/{orderId}")
    public String confirmReceiving(@PathVariable("orderId") String orderId, Model model) {
        BSResult bsResult = orderService.confirmReceiving(orderId);

        if (bsResult.getCode() == 200) {
            return "redirect:/order/list";
        } else {
            model.addAttribute("exception", bsResult.getMessage());
            return "exception";
        }

    }
}
