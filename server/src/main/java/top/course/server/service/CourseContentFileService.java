package top.course.server.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.CourseContentFile;
import top.course.server.domain.CourseContentFileExample;
import top.course.server.dto.CourseContentFileDto;
import top.course.server.mapper.CourseContentFileMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: CourseContentFile持久层接口
 */

@Service
public class CourseContentFileService {
    @Resource
    private CourseContentFileMapper courseContentFileMapper;

    /**
     * courseContentFile表列表查询
     * @param courseId 对应课程id
     */
    public List<CourseContentFileDto> list(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> fileList = courseContentFileMapper.selectByExample(example);
        return CopyUtil.copyList(fileList, CourseContentFileDto.class);
    }

    /**
     * 保存: CourseContentFileDto对象有id属性值时更新，无值时新增
     * @param courseContentFileDto 数据传输对象
     */
    public void save(CourseContentFileDto courseContentFileDto) {
        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto, CourseContentFile.class);
        if (StringUtils.isEmpty(courseContentFileDto.getId())) {
            this.insert(courseContentFile);
        } else {
            this.update(courseContentFile);
        }
    }

    /**
     * 新增:生成短id作为CourseContentFile对象id插入courseContentFile表
     * @param courseContentFile (无ID)CourseContentFile对象
     */
    private void insert(CourseContentFile courseContentFile) {
        courseContentFile.setId(UUIDUtil.getShortUUID());
        courseContentFileMapper.insert(courseContentFile);
    }

    /**
     * 更新:根据CourseContentFile对象id查询条件修改courseContentFile表数据
     * @param courseContentFile (有ID)CourseContentFile对象
     */
    private void update(CourseContentFile courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    /**
     * 删除: 根据id删除courseContentFile表数据
     * @param id id
     */
    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey(id);
    }
}