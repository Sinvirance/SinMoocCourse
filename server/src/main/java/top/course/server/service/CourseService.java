package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.course.server.domain.Course;
import top.course.server.domain.CourseContent;
import top.course.server.domain.CourseExample;
import top.course.server.dto.*;
import top.course.server.enums.CourseStatusEnum;
import top.course.server.mapper.CourseContentMapper;
import top.course.server.mapper.CourseMapper;
import top.course.server.mapper.my.MyCourseMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.DateUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/29 01:29
 * @Description: Course持久层接口
 */

@Service
public class CourseService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MyCourseMapper myCourseMapper;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;

    /**
     * course表列表分页查询：当有分类时，关联分类课程表
     * @param pageDto 分页组件传输对象
     */
    public void list(CoursePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CourseDto> courseDtoList = myCourseMapper.list(pageDto);
        PageInfo<CourseDto> pageInfo = new PageInfo<>(courseDtoList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(courseDtoList);
    }


    /**
     * 新课列表查询，只查询已发布的，按创建日期倒序
     * @param pageDto 分页组件传输对象
     * @return 新课列表
     */
    public List<CourseDto> listNew(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        /* 查询结果为已发布的：publish */
        courseExample.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        courseExample.setOrderByClause("created_at desc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        return CopyUtil.copyList(courseList, CourseDto.class);
    }


    /**
     * 热门课程列表查询，按创建日期倒序
     * @param pageDto 分页组件传输对象
     * @return 新课列表
     */
    public List<CourseDto> listHot(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        /* 查询结果为已发布的,距离现在3个月报名人数最多的 */
        courseExample.createCriteria().andEnrollGreaterThanOrEqualTo(50).
                andCreatedAtGreaterThanOrEqualTo(DateUtil.BeforeMonthDate(3))
                .andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        courseExample.setOrderByClause("enroll desc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        return CopyUtil.copyList(courseList, CourseDto.class);
    }


    /**
     * 保存: CourseDto对象有id属性值时更新，无值时新增
     * @param courseDto 数据传输对象
     */
    @Transactional
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);
        if (StringUtils.isEmpty(courseDto.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }
        // 批量保存课程分类
        courseCategoryService.saveBatch(course.getId(),courseDto.getCategorys());

    }

    /**
     * 新增:生成短id作为Course对象id插入course表
     * @param course (无ID)Course对象
     */
    private void insert(Course course) {
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(UUIDUtil.getShortUUID());
        courseMapper.insert(course);
    }

    /**
     * 更新:根据Course对象id查询条件修改course表数据
     * @param course (有ID)Course对象
     */
    private void update(Course course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除: 根据id删除course表数据
     * @param id id
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新: 根据更新id更新课程时长
     * @param courseId Course对象Id
     */
    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        myCourseMapper.updateTime(courseId);
    }

    /**
     * 查找: 根据传入的课程id获取course_content表信息
     * @param id 课程id
     * @return CourseContentDto 课程内容信息传输对象
     */
    public CourseContentDto findContent(String id) {
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content == null) {
            return null;
        }
        return CopyUtil.copy(content, CourseContentDto.class);
    }

    /**
     * 保存: 将课程内容新增或修改到课程内容表
     * @param courseContentDto 课程内容信息传输对象
     * @return 影响的行数
     */
    public int saveContent(CourseContentDto courseContentDto) {
        CourseContent content = CopyUtil.copy(courseContentDto, CourseContent.class);
        /*
         * 本来按照正常的思路，保存到底是新增还是修改需要先判断course_content是否有对应课程课程id
         * 但其实我们可以直接进行update，当没有更新到数据时，再去执行insert操作，这样减少了每次保存必须先进行查找的问题，减少了与数据表的交互
         */
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(content);
        if (i == 0) {
            i = courseContentMapper.insert(content);
        }
        return i;
    }

    /**
     * 更新: 一门课程顺序改变，其他顺序也要改变
     * @param sortDto 课程顺序数据传输对象
     */
    @Transactional
    public void sort(SortDto sortDto) {
        /* 修改当前记录的排序值 */
        myCourseMapper.updateSort(sortDto);
        /* 如果排序值变大 */
        if (sortDto.getNewSort() > sortDto.getOldSort()) {
            myCourseMapper.moveSortsForward(sortDto);
        }
        /* 如果排序值变小 */
        if (sortDto.getNewSort() < sortDto.getOldSort()) {
            myCourseMapper.moveSortsBackward(sortDto);
        }
    }
}