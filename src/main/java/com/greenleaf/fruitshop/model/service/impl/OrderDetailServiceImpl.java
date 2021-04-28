package com.greenleaf.fruitshop.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.entity.OrderDetail;
import com.greenleaf.fruitshop.model.mapper.FruitInfoMapper;
import com.greenleaf.fruitshop.model.mapper.OrderDetailMapper;
import com.greenleaf.fruitshop.model.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private FruitInfoMapper bookInfoMapper;

    @Override
    public List<FruitInfo> findBooksByOrderId(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(queryWrapper);

        List<FruitInfo> bookInfos = orderDetails.stream()
                .map(orderDetail -> bookInfoMapper.selectById(orderDetail.getFruitId()))
                .collect(Collectors.toList());

        return bookInfos;
    }

}
