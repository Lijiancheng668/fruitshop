package com.greenleaf.fruitshop.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.greenleaf.fruitshop.exception.BSException;
import com.greenleaf.fruitshop.model.entity.FruitDesc;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.mapper.FruitDescMapper;
import com.greenleaf.fruitshop.model.service.IFruitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/book")
public class FruitInfoController {
    @Autowired
    private IFruitInfoService fruitInfoService;
    @Autowired
    private FruitDescMapper fruitDescMapper;

    @RequestMapping("/list")
    public String fruitSearchList(@RequestParam(defaultValue = "1", required = false) int cateId,//分类Id，默认为0，即不按照分类Id查
                                 @RequestParam(defaultValue = "1", required = false) int page,
                                 @RequestParam(defaultValue = "6", required = false) int pageSize,
                                 Model model) {
        PageInfo<FruitInfo> fruitPageInfo = fruitInfoService.findBookListByCondition(cateId, page, pageSize);

        model.addAttribute("fruitPageInfo", fruitPageInfo);

        model.addAttribute("cateId", cateId);

        return "book_list";
    }
    /**
     * 查询某一本水果详情
     *
     * @param fruitId
     * @param model
     * @return
     */
    @RequestMapping("/info/{fruitId}")
    public String fruitInfo(@PathVariable("fruitId") Integer fruitId, Model model) throws BSException {
        //查询书籍
        FruitInfo fruitInfo = fruitInfoService.findById(fruitId);
        //查询书籍推荐列表
        List<FruitInfo> recommendBookList = fruitInfoService.findFruitListByCateId(fruitInfo.getFruitCategoryId(), 1, 5);
        //查询书籍详情
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fruit_id",fruitId);
        FruitDesc fruitDesc = fruitDescMapper.selectOne(queryWrapper);
        Collections.shuffle(recommendBookList);
        model.addAttribute("fruitInfo", fruitInfo);
        model.addAttribute("FruitDesc", fruitDesc);
        return "book_info";
    }

}
