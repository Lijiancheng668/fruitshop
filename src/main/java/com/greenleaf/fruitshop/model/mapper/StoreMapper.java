package com.greenleaf.fruitshop.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greenleaf.fruitshop.model.entity.MonthSale;
import com.greenleaf.fruitshop.model.entity.Store;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreMapper extends BaseMapper<Store> {
    @Select("select month(a.create_time) name, count(1) value\n" +
            "        FROM `orders` a\n" +
            "            left join\n" +
            "            order_detail b\n" +
            "        on a.order_id = b.order_id\n" +
            "        WHERE  a.create_time\n" +
            "            BETWEEN #{startTime}\n" +
            "          AND #{endTime}\n" +
            "          AND b.store_id = #{storeId}\n" +
            "        GROUP BY month(a.create_time )\n" +
            "        order by month(a.create_time) ASC")
    List<MonthSale> calStoreMonthNum(Integer storeId,String startTime,String endTime);
}
