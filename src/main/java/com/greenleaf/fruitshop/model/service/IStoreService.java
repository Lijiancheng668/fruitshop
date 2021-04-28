package com.greenleaf.fruitshop.model.service;

import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.model.entity.MonthSale;
import com.greenleaf.fruitshop.model.entity.Store;

import java.util.List;

public interface IStoreService {
    Store findStoreByUserId(Integer userId);
    Store findById(int storeId);
    List<Store> findStores();
    List<MonthSale> calStoreMonthNum(Integer storeId,String startTime,String endTime);
    BSResult updateStore(Store store);
}
