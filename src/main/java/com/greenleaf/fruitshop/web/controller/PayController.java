package com.greenleaf.fruitshop.web.controller;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.model.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/payment")
public class PayController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/{orderId}/{payMethod}")
    public String paymentPage(@PathVariable("orderId") String orderId, @PathVariable("payMethod") int payMethod, HttpServletResponse response, Model model){
        System.out.println(payMethod);
        BSResult bsResult = orderService.findOrderById(orderId);
        try {
            if (!StringUtils.isEmpty(bsResult)){
                orderService.payOrderById(orderId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("exception", "支付出错了!");
            return "exception";
        }
        return "pay_success";
    }
}
