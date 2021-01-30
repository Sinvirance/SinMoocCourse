package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.Teacher;
import top.course.server.domain.TeacherExample;
import top.course.server.dto.TeacherDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.TeacherMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/30 21:05
 * @Description: Teacher持久层接口
 */

@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    /**
     * teacher表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);

        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
        pageDto.setList(teacherDtoList);
    }

    /**
     * 保存: TeacherDto对象有id属性值时更新，无值时新增
     * @param teacherDto 数据传输对象
     */
    public void save(TeacherDto teacherDto) {
        Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
        if (StringUtils.isEmpty(teacherDto.getId())) {
            this.insert(teacher);
        } else {
            this.update(teacher);
        }
    }

    /**
     * 新增:生成短id作为Teacher对象id插入teacher表
     * @param teacher (无ID)Teacher对象
     */
    private void insert(Teacher teacher) {
        teacher.setId(UUIDUtil.getShortUUID());
        teacherMapper.insert(teacher);
    }

    /**
     * 更新:根据Teacher对象id查询条件修改teacher表数据
     * @param teacher (有ID)Teacher对象
     */
    private void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }

    /**
     * 删除: 根据id删除teacher表数据
     * @param id id
     */
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }
}