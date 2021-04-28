package com.greenleaf.fruitshop.model.service;

import com.github.pagehelper.PageInfo;
import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.exception.BSException;
import com.greenleaf.fruitshop.model.entity.FruitInfo;

import java.util.List;

public interface IFruitInfoService {
    PageInfo<FruitInfo> findFruitListByStoreId(int storeId, int page, int pageSize);

    FruitInfo adminFindById(Integer storeId,Integer fruitId) throws BSException;

    List<FruitInfo> findFruitListByCateId(int cateId, int page, int pageSize);

    PageInfo<FruitInfo> findBookListByCondition( int cateId, int page, int pageSize);

    FruitInfo queryBookAvailable(int bookId,int storeId);

    FruitInfo findById(Integer fruitId) throws BSException;

    BSResult saveBook(FruitInfo fruitInfo, String bookDescStr);

    BSResult deleteFruit(Integer storeId,Integer fruitId);

    BSResult updateFruit(FruitInfo fruitInfo, String bookDesc);

    BSResult changeShelfStatus(int bookId,int shelf,int storeId);
}
