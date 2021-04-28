package com.greenleaf.fruitshop.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitInfoMapper extends BaseMapper<FruitInfo> {
    void insertFruitInfo(FruitInfo fruitInfo);
}
