package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.course.server.domain.CourseCategory;
import top.course.server.domain.CourseCategoryExample;
import top.course.server.dto.CategoryDto;
import top.course.server.dto.CourseCategoryDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.CourseCategoryMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/29 21:04
 * @Description: CourseCategory持久层接口
 */

@Service
public class CourseCategoryService {
    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * courseCategory表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);

        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
        pageDto.setList(courseCategoryDtoList);
    }

    /**
     * 保存: CourseCategoryDto对象有id属性值时更新，无值时新增
     * @param courseCategoryDto 数据传输对象
     */
    public void save(CourseCategoryDto courseCategoryDto) {
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto, CourseCategory.class);
        if (StringUtils.isEmpty(courseCategoryDto.getId())) {
            this.insert(courseCategory);
        } else {
            this.update(courseCategory);
        }
    }

    /**
     * 新增:生成短id作为CourseCategory对象id插入courseCategory表
     * @param courseCategory (无ID)CourseCategory对象
     */
    private void insert(CourseCategory courseCategory) {
        courseCategory.setId(UUIDUtil.getShortUUID());
        courseCategoryMapper.insert(courseCategory);
    }

    /**
     * 更新:根据CourseCategory对象id查询条件修改courseCategory表数据
     * @param courseCategory (有ID)CourseCategory对象
     */
    private void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    /**
     * 删除: 根据id删除courseCategory表数据
     * @param id id
     */
    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 保存: 批量保存课程分类表数据
     * @param courseId 对应的课程id
     * @param categorys CategoryDto对象列表
     */
    @Transactional
    public void saveBatch(String courseId, List<CategoryDto> categorys) {
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCategoryIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(courseCategoryExample);
        for (int i = 0; i < categorys.size(); i++) {
            CategoryDto categoryDto = categorys.get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UUIDUtil.getShortUUID());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDto.getId());
            insert(courseCategory);
        }
    }

    /**
     * 查找：课程分类表下的某个课程的所有分类
     * @param courseId 对应课程id
     * @return 以课程id为筛选id返回的分类列表
     */
    public List<CourseCategoryDto> listByCourse(String courseId) {
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        return CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
    }
}