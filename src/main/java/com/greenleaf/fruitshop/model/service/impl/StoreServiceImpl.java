package com.greenleaf.fruitshop.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.common.utils.BSResultUtil;
import com.greenleaf.fruitshop.model.entity.MonthSale;
import com.greenleaf.fruitshop.model.entity.Store;
import com.greenleaf.fruitshop.model.entity.User;
import com.greenleaf.fruitshop.model.mapper.StoreMapper;
import com.greenleaf.fruitshop.model.mapper.UserMapper;
import com.greenleaf.fruitshop.model.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class StoreServiceImpl implements IStoreService {
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Store findStoreByUserId(Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("store_manager_id",userId);
        List<Store> stores = storeMapper.selectList(queryWrapper);
        if(stores !=null && stores.size() > 0){
            return stores.get(0);
        }
        return null;
    }

    @Override
    public Store findById(int storeId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("store_id",storeId);
        Store store = storeMapper.selectOne(queryWrapper);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("user_id",store.getStoreManagerId());
        User user = userMapper.selectOne(qw);
        store.setStoreManagerName(user.getUsername());
        return store;
    }
    @Override
    public List<Store> findStores() {
        List<Store> stores = storeMapper.selectList(null);
        stores.forEach(store -> {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id",store.getStoreManagerId());
            User user = userMapper.selectOne(queryWrapper);
            if(user != null){
                store.setStoreManagerName(user.getUsername());
            }
        });
        return stores;
    }

    @Override
    public List<MonthSale> calStoreMonthNum(Integer storeId,String startTime,String endTime) {
        return storeMapper.calStoreMonthNum(storeId,startTime,endTime);
    }

    @Override
    @Transactional
    public BSResult updateStore(Store store) {
        store.setUpdated(new Date());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("store_id",store.getStoreId());
        storeMapper.update(store,queryWrapper);
        return BSResultUtil.success();
    }
}
