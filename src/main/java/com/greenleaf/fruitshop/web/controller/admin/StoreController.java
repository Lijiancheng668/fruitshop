package com.greenleaf.fruitshop.web.controller.admin;

import com.greenleaf.fruitshop.model.entity.MonthSale;
import com.greenleaf.fruitshop.model.entity.Store;
import com.greenleaf.fruitshop.model.service.IStoreService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/admin/store")
@RequiresPermissions("store-manage")
public class StoreController {
    @Autowired
    private IStoreService storeService;
    @RequestMapping("/{storeId}")
    @RequiresPermissions("store-edit")
    public String toEdit(@PathVariable("storeId") int storeId, Model model){

        Store store = storeService.findById(storeId);

        model.addAttribute("store", store);

        return "admin/store/edit";
    }
    @RequestMapping("/list")
    @RequiresPermissions("store-list")
    public String storeList(Model model){

        List<Store> stores = storeService.findStores();

        model.addAttribute("stores", stores);

        return "admin/store/list";
    }
    @RequestMapping("/storeMonthNum/{storeId}")
    @ResponseBody
    public List<MonthSale> storeMonthNum(Model model, @PathVariable("storeId") int storeId){
        Calendar cal = Calendar.getInstance();
        int nowYear = cal.get(Calendar.YEAR);
        String startTime = nowYear + "-01-01 00:00:00";
        String endTime = nowYear + "-12-31 00:00:00";
        List<MonthSale> monthSales = storeService.calStoreMonthNum(storeId,startTime,endTime);
        Map map = new HashMap();
        for (MonthSale i: monthSales) {
            map.put(i.getName(),0);
        }
        for (int i = 1; i <= 12; i++) {
            if (!map.containsKey(i)){
                monthSales.add(new MonthSale(i,0));
            }
        }
        Collections.sort(monthSales);
        return monthSales;
    }
    @RequestMapping("/storeMonth")
    public String toEcharts(){
        return "admin/store/echarts";
    }
    @RequestMapping("/storeHello")
    public String toHello(){
        return "admin/store/hello";
    }
    @RequestMapping("/selectStoreId")
    @ResponseBody
    public List<Integer> selectStoreId(){
        List<Store> stores = storeService.findStores();
        List<Integer> StoreIds = new ArrayList<>();
        for (Store s: stores) {
            StoreIds.add(s.getStoreId());
        }
        return StoreIds;
    }

    @PostMapping("/edit")
    @RequiresPermissions("store-edit")
    public String editStore(Store store,Model model){

        storeService.updateStore(store);

        model.addAttribute("saveMsg", "保存成功");

        return "forward:"+store.getStoreId();
    }

}
