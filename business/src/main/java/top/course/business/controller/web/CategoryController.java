package top.course.business.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.course.server.dto.CategoryDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.CategoryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/5/7 7:50
 * @Description: 前台Category控制层
 */

@RestController("webCategoryController")
@RequestMapping("/web/category")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    public static final String BUSINESS_NAME = "分类";

    @Resource
    private CategoryService categoryService;

    /**
     * 分类列表查询
     */
    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<CategoryDto> categoryDtoList = categoryService.all();
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }
}

