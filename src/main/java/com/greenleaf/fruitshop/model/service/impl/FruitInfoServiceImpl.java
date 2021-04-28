package com.greenleaf.fruitshop.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenleaf.fruitshop.common.pojo.BSResult;
import com.greenleaf.fruitshop.common.utils.BSResultUtil;
import com.greenleaf.fruitshop.exception.BSException;
import com.greenleaf.fruitshop.model.entity.FruitDesc;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.mapper.FruitCateMapper;
import com.greenleaf.fruitshop.model.mapper.FruitDescMapper;
import com.greenleaf.fruitshop.model.mapper.FruitInfoMapper;
import com.greenleaf.fruitshop.model.service.IFruitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class FruitInfoServiceImpl implements IFruitInfoService {
    @Autowired
    private FruitInfoMapper fruitInfoMapper;
    @Autowired
    private FruitCateMapper categoryMapper;
    @Autowired
    private FruitDescMapper fruitDescMapper;
    @Override
    public PageInfo<FruitInfo> findFruitListByStoreId(int storeId, int page, int pageSize) {
        //设置分页信息，当前页，每页显示多少
        PageHelper.startPage(page, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("store_id",storeId);
        //queryWrapper.eq("is_shelf",1);
        List<FruitInfo> fruitInfos = fruitInfoMapper.selectList(queryWrapper);
        PageInfo<FruitInfo> pageInfo = new PageInfo<>(fruitInfos);
        return pageInfo;
    }

    @Override
    public List<FruitInfo> findFruitListByCateId(int cateId, int page, int pageSize) {
        //设置分页信息，当前页，每页显示多少
        PageHelper.startPage(page, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fruit_category_id",cateId);
        queryWrapper.eq("is_shelf",1);
        List<FruitInfo> fruitInfos = fruitInfoMapper.selectList(queryWrapper);
        PageInfo<FruitInfo> pageInfo = new PageInfo<>(fruitInfos);
        return pageInfo.getList();
    }

    @Override
    public PageInfo<FruitInfo> findBookListByCondition(int cateId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<FruitInfo> fruitInfos = null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fruit_category_id",cateId);
        queryWrapper.eq("is_shelf",1);
        fruitInfos = fruitInfoMapper.selectList(queryWrapper);
        PageInfo<FruitInfo> pageInfo = new PageInfo<>(fruitInfos);

        return pageInfo;
    }

    @Override
    public FruitInfo queryBookAvailable(int fruitId,int storeId) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fruit_id", fruitId);
        queryWrapper.eq("is_shelf", 1);
        queryWrapper.eq("store_id", storeId);
        queryWrapper.gt("store_mount", 0);
        List<FruitInfo> fruitInfos = fruitInfoMapper.selectList(queryWrapper);
        if (fruitInfos != null && !fruitInfos.isEmpty()) {
            return fruitInfos.get(0);
        }
        return null;
    }

    @Override
    public FruitInfo findById(Integer fruitId) throws BSException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_shelf", 1);
        queryWrapper.eq("fruit_id", fruitId);
        List<FruitInfo> fruitInfos = fruitInfoMapper.selectList(queryWrapper);
        if (fruitInfos == null || fruitInfos.size() == 0) {
            throw new BSException("你搜索的书籍不存在或未上架！");
        }
        FruitInfo fruitInfo = fruitInfos.get(0);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("cate_id",fruitInfo.getFruitCategoryId());
        fruitInfo.setCategoryName(categoryMapper.selectOne(qw).getName());
        return fruitInfo;
    }

    @Override
    public FruitInfo adminFindById(Integer storeId,Integer fruitId) throws BSException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_shelf", 1);
        queryWrapper.eq("store_id",storeId);
        queryWrapper.eq("fruit_id", fruitId);
        List<FruitInfo> fruitInfos = fruitInfoMapper.selectList(queryWrapper);
        if (fruitInfos == null || fruitInfos.size() == 0) {
            throw new BSException("你搜索的书籍不存在或未上架！");
        }
        FruitInfo fruitInfo = fruitInfos.get(0);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("cate_id",fruitInfo.getFruitCategoryId());
        fruitInfo.setCategoryName(categoryMapper.selectOne(qw).getName());
        return fruitInfo;
    }
    @Override
    @Transactional
    public BSResult saveBook(FruitInfo fruitInfo, String fruitDescStr) {

        fruitInfo.setStoreTime(new Date());
        fruitInfo.setDealMount(100);
        fruitInfo.setPackStyle("");
        fruitInfo.setIsShelf(1);
        fruitInfoMapper.insertFruitInfo(fruitInfo);
        FruitDesc fruitDesc = new FruitDesc();
        fruitDesc.setFruitDesc(fruitDescStr);
        fruitDesc.setFruitId(fruitInfo.getFruitId());
        fruitDesc.setCreated(new Date());
        fruitDesc.setUpdated(new Date());
        fruitDescMapper.insert(fruitDesc);

        return BSResultUtil.success();
    }

    @Override
    public BSResult deleteFruit(Integer storeId,Integer fruitId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fruit_id",fruitId);
        queryWrapper.eq("store_id",storeId);
        fruitInfoMapper.delete(queryWrapper);
        QueryWrapper qw = new QueryWrapper();
        qw.eq("fruit_id",fruitId);
        fruitDescMapper.delete(qw);
        return null;
    }

    @Override
    @Transactional
    public BSResult updateFruit(FruitInfo fruitInfo, String fruitDescStr) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("store_id",fruitInfo.getStoreId());
        queryWrapper.eq("fruit_id",fruitInfo.getFruitId());
        fruitInfoMapper.update(fruitInfo,queryWrapper);

        FruitDesc fruitDesc = new FruitDesc();
        fruitDesc.setFruitDesc(fruitDescStr);
        fruitDesc.setFruitId(fruitInfo.getFruitId());
        fruitDesc.setUpdated(new Date());

        QueryWrapper qw = new QueryWrapper();
        qw.eq("fruit_id",fruitInfo.getFruitId());
        fruitDescMapper.update(fruitDesc,qw);
        return BSResultUtil.success();
    }

    /**
     * 商品下架
     *
     * @param fruitId
     * @return
     */
    @Override
    @Transactional
    public BSResult changeShelfStatus(int fruitId,int shelf,int storeId) {

        FruitInfo fruitInfo = new FruitInfo();
        fruitInfo.setFruitId(fruitId);
        fruitInfo.setIsShelf(shelf);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fruit_id",fruitId);
        queryWrapper.eq("store_id",storeId);
        fruitInfoMapper.update(fruitInfo,queryWrapper);
        return BSResultUtil.success();
    }


}
