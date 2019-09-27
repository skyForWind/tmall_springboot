package com.how2java.tmall.web;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 专门用来提供 RESTFUL 服务其控制器
 * 表示这是一个控制器，并且对每个方法的返回值都会直接转换为 json 数据格式。
 */
@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        Page4Navigator<Category> page = categoryService.list(start, size, 5);
        return page;
    }

    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image,
                      HttpServletRequest request) throws IOException {
        categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {
        File imageFloder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFloder, bean.getId() + ".jpg");

        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        image.transferTo(file);

        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        categoryService.delete(id);

        File imageFloder = new File(request.getServletContext().getRealPath("/img/category"));
        File file = new File(imageFloder, id + ".jpg");
        file.delete();

        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) {
        Category bean = categoryService.get(id);
        return bean;
    }

    @PutMapping("/categories/{id}")
    public Object update(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {
        //因为PUT 方式注入不了。。。 只能用这种方式取参数了
        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);

        if (image != null)
            saveOrUpdateImageFile(bean, image, request);

        return bean;
    }
}
