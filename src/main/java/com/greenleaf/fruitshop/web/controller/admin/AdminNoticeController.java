package com.greenleaf.fruitshop.web.controller.admin;

import com.greenleaf.fruitshop.model.entity.Notice;
import com.greenleaf.fruitshop.model.service.INoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/notice")
@RequiresPermissions("book-manage")
public class AdminNoticeController {
    @Autowired
    private INoticeService noticeService;
    @RequestMapping("list")
    @RequiresPermissions("notice-list")
    public String tolist(HttpServletRequest request) {
        List<Notice> allNotices = noticeService.findAllNotices();
        request.setAttribute("allNotices", allNotices);
        return "admin/notice/list";
    }
}
