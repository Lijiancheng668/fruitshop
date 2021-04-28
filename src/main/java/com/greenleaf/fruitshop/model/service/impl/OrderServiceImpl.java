package com.greenleaf.fruitshop.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.common.utils.BSResultUtil;
import com.greenleaf.fruitshop.common.utils.IDUtils;
import com.greenleaf.fruitshop.model.entity.OrderDetail;
import com.greenleaf.fruitshop.model.entity.OrderShipping;
import com.greenleaf.fruitshop.model.entity.Orders;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.entity.custom.Cart;
import com.greenleaf.fruitshop.model.entity.custom.CartItem;
import com.greenleaf.fruitshop.model.entity.custom.OrderCustom;
import com.greenleaf.fruitshop.model.mapper.CustomMapper;
import com.greenleaf.fruitshop.model.mapper.OrderDetailMapper;
import com.greenleaf.fruitshop.model.mapper.OrderShippingMapper;
import com.greenleaf.fruitshop.model.mapper.OrdersMapper;
import com.greenleaf.fruitshop.model.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrdersMapper orderMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private CustomMapper customMapper;

    //未完成状态，比如未付款等
    private final int NOT_COMPLETED = 0;

    private final int COMPLETED = 1;

    //未发货
    private final int NOT_POST = 2;
    //已发货
    private final int POSTED = 3;

    //确认收货
    private final int CONFIRM_REVEIVE = 4;
    /**
     * 创建订单
     *
     * @param cart
     * @param user
     * @return
     */
    @Override
    @Transactional
    public BSResult createOrder(Cart cart, User user, String express, int payMethod) {
        Map<String, CartItem> cartItems = cart.getCartItems();

        if(cartItems.size() > 0){
            Orders order = new Orders();
            String orderId = IDUtils.genOrderId();
            order.setOrderId(orderId);
            order.setUserId(user.getUserId());
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            order.setBuyerRate(NOT_COMPLETED);
            order.setShippingName(express);
            order.setOrderMount(cartItems.size());
            order.setPayment(String.format("%.2f", cart.getTotal()));
            order.setPaymentType(payMethod);
            order.setStatus(NOT_COMPLETED);
            order.setBuyerRate(NOT_COMPLETED);
            order.setPostFee("0.00");//邮费
            // 填写订单
            orderMapper.insert(order);

            OrderShipping orderShipping = new OrderShipping();
            orderShipping.setOrderId(orderId);
            orderShipping.setCreated(new Date());
            orderShipping.setUpdated(new Date());
            orderShipping.setReceiverAddress(user.getDetailAddress());
            orderShipping.setReceiverMobile(user.getPhone());
            orderShipping.setReceiverName(user.getUsername());
            orderShipping.setReceiverState("广东");
            orderShipping.setReceiverCity("广州");
            orderShipping.setReceiverDistrict("海珠区");
            // 填写收货单
            orderShippingMapper.insert(orderShipping);

            List<OrderDetail> orderDetails = new ArrayList<>();
            for (Map.Entry<String, CartItem> cartItemEntry : cartItems.entrySet()) {

                CartItem cartItem = cartItemEntry.getValue();
                if (cartItem.getBuyNum() > 0 && cartItem.isChecked()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(orderId);
                    String[] split = cartItemEntry.getKey().split("-");
                    orderDetail.setFruitId(Integer.valueOf(split[0]));
                    orderDetail.setMount(cartItem.getBuyNum());
                    orderDetail.setStoreId(Integer.valueOf(split[1]));
                    orderDetail.setOrderNumber(orderId);
                    orderDetail.setPostStatus(NOT_COMPLETED + "");
                    orderDetail.setReceiveStatus(NOT_COMPLETED + "");
                    //orderDetail.setStoreId(cartItem.getBookInfo().getStoreId());
                    orderDetail.setTotalPrice(BigDecimal.valueOf(cartItem.getSubTotal()));
                    orderDetail.setUnitPrice(cartItem.getFruitInfo().getPrice());
                    orderDetail.setImageUrl(cartItem.getFruitInfo().getImageUrl());
                    orderDetail.setFruitName(cartItem.getFruitInfo().getName());
                    orderDetails.add(orderDetail);
                    // 填写订单详情
                    orderDetailMapper.insert(orderDetail);
                }
            }
            return BSResultUtil.success(order);
        }else
            return BSResultUtil.build(400, "没有选中的购物项，创建订单失败!");
    }


    @Override
    public List<OrderCustom> findOrdersByUserId(int userId) {
        return customMapper.findOrdersByUserId(userId);
    }

    @Override
    @Transactional
    public BSResult deleteOrder(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        orderDetailMapper.delete(queryWrapper);

        orderMapper.deleteById(orderId);
        orderShippingMapper.deleteById(orderId);

        return BSResultUtil.success();
    }

    /**
     * 通过订单号查询订单
     * @param orderId
     * @return
     */
    @Override
    public BSResult findOrderById(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        Orders orders = orderMapper.selectOne(queryWrapper);
        return BSResultUtil.success(orders);
    }

    @Override
    public BSResult payOrderById(String orderId) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("order_id",orderId);
        Orders orders = new Orders();
        orders.setStatus(1);
        orderMapper.update(orders,updateWrapper);
        return BSResultUtil.success();
    }

    @Override
    public BSResult confirmReceiving(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setStatus(CONFIRM_REVEIVE);
        int i = orderMapper.update(order,queryWrapper);
        if(i > 0){
            return BSResultUtil.success();
        }
        return BSResultUtil.build(400, "确认收货失败!");
    }

    @Override
    public List<OrderCustom> findOrdersByStoreId(int storeId) {
        return customMapper.findOrdersByStoreId(storeId);
    }
    @Override
    public OrderCustom findOrderCustomById(String orderId) {
        OrderCustom orderCustom = new OrderCustom();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        Orders orders = orderMapper.selectOne(queryWrapper);
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(queryWrapper);
        OrderShipping orderShipping = orderShippingMapper.selectOne(queryWrapper);
        orderCustom.setOrder(orders);
        orderCustom.setOrderDetails(orderDetails);
        orderCustom.setOrderShipping(orderShipping);
        return orderCustom;
    }
    @Override
    public BSResult postOrder(String orderId) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        QueryWrapper queryWrapper = new QueryWrapper();
        try {
            orders.setStatus(POSTED);
            queryWrapper.eq("order_id",orderId);
            orderMapper.update(orders,queryWrapper);
        } catch (Exception e) {
            orders.setStatus(NOT_POST);
            orderMapper.update(orders,queryWrapper);
            e.printStackTrace();
            return BSResultUtil.build(500, "发货失败");
        }
        return BSResultUtil.success();
    }
}
