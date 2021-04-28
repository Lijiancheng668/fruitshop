package com.greenleaf.fruitshop.web.controller;

import com.greenleaf.fruitshop.model.entity.FruitCategory;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.entity.Notice;
import com.greenleaf.fruitshop.model.service.IFruitCateService;
import com.greenleaf.fruitshop.model.service.IFruitInfoService;
import com.greenleaf.fruitshop.model.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Random;

@Controller
public class IndexController {
    @Autowired
    private IFruitCateService cateService;
    @Autowired
    private IFruitInfoService fruitInfoService;
    @Autowired
    private INoticeService noticeService;
    private List<FruitCategory> categoryList;
    private List<Notice> noticeList;
    @Autowired
    private ServletContext servletContext;
    /**
     * 第一次访问首页首页
     *
     * @return
     */
    @RequestMapping({"", "/", "/index"})
    public String index(Model model) {
        if(categoryList == null){
            categoryList = cateService.getCategoryList();
        }
        if (CollectionUtils.isEmpty(noticeList)){
            noticeList = noticeService.findAllNotices();
        }
        //获得水果列表
        List<FruitInfo> fruitInfos = fruitInfoService.findFruitListByCateId(new Random().nextInt(5 - 1 + 1) + 1, 1, 8);
        model.addAttribute("fruitInfos", fruitInfos);
        model.addAttribute("bookCategories",categoryList);
        model.addAttribute("notices",noticeList);
        servletContext.setAttribute("fruitCategories", categoryList);
        return "index";
    }

}
