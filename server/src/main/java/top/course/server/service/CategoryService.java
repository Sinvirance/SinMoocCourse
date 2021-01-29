package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.course.server.domain.Category;
import top.course.server.domain.CategoryExample;
import top.course.server.dto.CategoryDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.CategoryMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/29 01:14
 * @Description: Category持久层接口
 */

@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询：category表信息
     * @return categoryDtoList 分类信息传输对象
     */
    public List<CategoryDto> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        return categoryDtoList;
    }

    /**
     * category表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryExample categoryExample = new CategoryExample();
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        categoryExample.setOrderByClause("sort asc");

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        pageDto.setList(categoryDtoList);
    }

    /**
     * 保存: CategoryDto对象有id属性值时更新，无值时新增
     * @param categoryDto 数据传输对象
     */
    public void save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto, Category.class);
        if (StringUtils.isEmpty(categoryDto.getId())) {
            this.insert(category);
        } else {
            this.update(category);
        }
    }

    /**
     * 新增:生成短id作为Category对象id插入category表
     * @param category (无ID)Category对象
     */
    private void insert(Category category) {
        category.setId(UUIDUtil.getShortUUID());
        categoryMapper.insert(category);
    }

    /**
     * 更新:根据Category对象id查询条件修改category表数据
     * @param category (有ID)Category对象
     */
    private void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 删除: 根据id删除category表数据
     * @param id id
     */
    @Transactional
    public void delete(String id) {
        deleteChildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除：根据父分类id删除子分类
     * @param id 父分类id
     */
    private void deleteChildren(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        /* 判断该分类是否为一级分类 */
        if ("00000000".equals(category.getParent())) {
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(categoryExample);
        }
    }
}