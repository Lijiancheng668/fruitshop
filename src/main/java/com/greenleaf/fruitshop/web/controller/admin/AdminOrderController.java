package com.greenleaf.fruitshop.web.controller.admin;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.model.entity.Store;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.entity.custom.OrderCustom;
import com.greenleaf.fruitshop.model.service.IOrderService;
import com.greenleaf.fruitshop.model.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("admin/order")
@RequiresPermissions("order-manage")
public class AdminOrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @RequestMapping("/list")
    @RequiresPermissions("order-list")
    public String orderList(HttpServletRequest request){

        Store loginStore = (Store) request.getSession().getAttribute("loginStore");

        List<OrderCustom> orderCustoms = orderService.findOrdersByStoreId(loginStore.getStoreId());

        request.setAttribute("orderCustoms", orderCustoms);

        return "admin/order/list";
    }
    /**
     * 更新订单
     * @param orderId
     * @return
     */
    @RequestMapping("/toUpdate/{orderId}")
    @RequiresPermissions("order-edit")
    public String updateOrder(@PathVariable("orderId") String orderId, Model model) {

        OrderCustom orderCustom = orderService.findOrderCustomById(orderId);

        User buyer = userService.findById(orderCustom.getOrder().getUserId());
        model.addAttribute("orderCustom", orderCustom);
        model.addAttribute("buyer", buyer);
        return "admin/order/edit";
    }
    /**
     * 发货
     * @param orderId
     * @return
     */
    @RequiresPermissions("order-edit")
    @RequestMapping("/post/{orderId}")
    public String postOrder(@PathVariable("orderId") String orderId){
        BSResult bsResult =  orderService.postOrder(orderId);
        if (bsResult.getCode() == 200) {
            return "redirect:/admin/order/list";
        }
        return "exception";
    }
    @RequestMapping("/deletion/{orderId}")
    @RequiresPermissions("order-delete")
    public String deletion(@PathVariable("orderId") String orderId) {

        BSResult bsResult = orderService.deleteOrder(orderId);

        if (bsResult.getCode() == 200) {
            return "redirect:/admin/order/list";
        }
        return "exception";
    }

}
