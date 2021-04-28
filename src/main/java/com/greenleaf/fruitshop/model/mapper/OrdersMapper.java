package com.greenleaf.fruitshop.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greenleaf.fruitshop.model.entity.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersMapper extends BaseMapper<Orders> {
}
