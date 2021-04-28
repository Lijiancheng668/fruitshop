package com.greenleaf.fruitshop.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.greenleaf.fruitshop.common.utils.IDUtils;
import com.greenleaf.fruitshop.exception.BSException;
import com.greenleaf.fruitshop.model.entity.FruitDesc;
import com.greenleaf.fruitshop.model.entity.FruitInfo;
import com.greenleaf.fruitshop.model.entity.Store;
import com.greenleaf.fruitshop.model.mapper.FruitDescMapper;
import com.greenleaf.fruitshop.model.service.IFruitInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/book")
@RequiresPermissions("book-manage")
public class AdminBookController {
    @Autowired
    private IFruitInfoService bookInfoService;
    @Autowired
    private FruitDescMapper fruitDescMapper;
    @Value("${image.url.prefix}")
    private String urlPrefix;
    @RequestMapping(value = "/list")
    @RequiresPermissions("book-query")
    public String bookList(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                           HttpSession session,
                           Model model) {
        Store store = (Store) session.getAttribute("loginStore");

        if (store != null) {
            PageInfo<FruitInfo> books = bookInfoService.findFruitListByStoreId(store.getStoreId(), page, 6);
            model.addAttribute("bookPageInfo", books);
        } else {
            model.addAttribute("exception", "您请求的资源不存在");
            return "exception";
        }

        return "admin/book/list";
   }
    @RequestMapping("toAddition")
    @RequiresPermissions("book-add")
    public String toAddition() {
        return "admin/book/add";
    }
    @RequestMapping("/addition")
    @RequiresPermissions("book-add")
    public String addBook(FruitInfo fruitInfo, String detail, MultipartFile pictureFile, HttpServletRequest request) throws Exception {
        System.out.println(detail);
        uploadPicture(fruitInfo, pictureFile, request);
        bookInfoService.saveBook(fruitInfo, detail);

        return "redirect:/admin/book/list";
    }
    private void uploadPicture(FruitInfo fruitInfo, MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        if (pictureFile != null) {
            if (!StringUtils.isEmpty(pictureFile.getOriginalFilename())) {
                String realPath = request.getServletContext().getRealPath("/" + urlPrefix);
                //原始文件名称
                String pictureFileName = pictureFile.getOriginalFilename();
                //新文件名称
                String newFileName = IDUtils.genShortUUID() + pictureFileName.substring(pictureFileName.lastIndexOf("."));

                //上传图片
                File uploadPic = new File(realPath + File.separator + newFileName);

                //向磁盘写文件
                pictureFile.transferTo(uploadPic);
                fruitInfo.setImageUrl(urlPrefix + File.separator + newFileName);
            }
        }
    }
    /**
     * 更新页面回显
     *
     * @param fruitId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/echo")
    @RequiresPermissions("book-edit")
    public String echo(int fruitId, Model model,HttpSession session) throws BSException {
        Store store = (Store) session.getAttribute("loginStore");
        FruitInfo fruitInfo = bookInfoService.adminFindById(store.getStoreId(),fruitId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fruit_id",fruitInfo.getFruitId());
        FruitDesc fruitDesc = fruitDescMapper.selectOne(queryWrapper);

        model.addAttribute("bookInfo", fruitInfo);

        model.addAttribute("bookDesc", fruitDesc);

        return "admin/book/edit";
    }
    @RequestMapping("/deletion/{fruitId}")
    @RequiresPermissions("book-delete")
    public String deletion(@PathVariable("fruitId") int fruitId,HttpSession session, HttpServletRequest request) throws BSException {
        Store store = (Store) session.getAttribute("loginStore");
        FruitInfo fruitInfo = bookInfoService.adminFindById(store.getStoreId(),fruitId);
        String realPath = request.getServletContext().getRealPath("/");
        File uploadPic = new File(realPath + fruitInfo.getImageUrl());
        uploadPic.delete();
        bookInfoService.deleteFruit(store.getStoreId(),fruitId);
        return "redirect:/admin/book/list";
    }
    @RequestMapping("/update")
    @RequiresPermissions("book-edit")
    public String updateFruit(FruitInfo fruitInfo, String fruitDesc,HttpSession session, MultipartFile pictureFile, HttpServletRequest request, RedirectAttributes ra) throws Exception {
        uploadPicture(fruitInfo, pictureFile, request);
        Store store = (Store) session.getAttribute("loginStore");
        FruitInfo originBook = bookInfoService.adminFindById(store.getStoreId(),fruitInfo.getFruitId());
        bookInfoService.updateFruit(fruitInfo, fruitDesc);

        //更新图片后，删除原来的图片
        String realPath = request.getServletContext().getRealPath("/");
        File uploadPic = new File(realPath + originBook.getImageUrl());
        uploadPic.delete();

        return "redirect:/admin/book/list";
    }
    @RequestMapping("/shelf")
    @RequiresPermissions("book-shelf")
    public String fruitOffShelf(int fruitId,int isShelf,HttpSession session) {
        Store store = (Store) session.getAttribute("loginStore");
        bookInfoService.changeShelfStatus(fruitId, isShelf,store.getStoreId());
        return "redirect:/admin/book/list";
    }

}
