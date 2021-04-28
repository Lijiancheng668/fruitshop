package com.greenleaf.fruitshop.model.service.impl;

import com.greenleaf.fruitshop.model.entity.FruitCategory;
import com.greenleaf.fruitshop.model.mapper.FruitCateMapper;
import com.greenleaf.fruitshop.model.service.IFruitCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitCateServiceImpl implements IFruitCateService {
    @Autowired
    private FruitCateMapper fruitCateMapper;
    @Override
    public List<FruitCategory> getCategoryList() {
        return fruitCateMapper.selectList(null);
    }
}
