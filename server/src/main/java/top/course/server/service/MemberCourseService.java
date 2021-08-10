package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.MemberCourse;
import top.course.server.domain.MemberCourseExample;
import top.course.server.dto.MemberCourseDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.MemberCourseMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: MemberCourse持久层接口
 */

@Service
public class MemberCourseService {
    @Resource
    private MemberCourseMapper memberCourseMapper;

    /**
     * memberCourse表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        MemberCourseExample memberCourseExample = new MemberCourseExample();
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(memberCourseExample);

        PageInfo<MemberCourse> pageInfo = new PageInfo<>(memberCourseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberCourseDto> memberCourseDtoList = CopyUtil.copyList(memberCourseList, MemberCourseDto.class);
        pageDto.setList(memberCourseDtoList);
    }

    /**
     * 保存: MemberCourseDto对象有id属性值时更新，无值时新增
     * @param memberCourseDto 数据传输对象
     */
    public void save(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
        if (StringUtils.isEmpty(memberCourseDto.getId())) {
            this.insert(memberCourse);
        } else {
            this.update(memberCourse);
        }
    }

    /**
     * 新增:生成短id作为MemberCourse对象id插入memberCourse表
     * @param memberCourse (无ID)MemberCourse对象
     */
    private void insert(MemberCourse memberCourse) {
        Date now = new Date();
        memberCourse.setId(UUIDUtil.getShortUUID());
        memberCourseMapper.insert(memberCourse);
    }

    /**
     * 更新:根据MemberCourse对象id查询条件修改memberCourse表数据
     * @param memberCourse (有ID)MemberCourse对象
     */
    private void update(MemberCourse memberCourse) {
        memberCourseMapper.updateByPrimaryKey(memberCourse);
    }

    /**
     * 删除: 根据id删除memberCourse表数据
     * @param id id
     */
    public void delete(String id) {
        memberCourseMapper.deleteByPrimaryKey(id);
    }
}