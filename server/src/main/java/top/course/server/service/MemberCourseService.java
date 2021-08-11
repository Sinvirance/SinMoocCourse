package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.course.server.domain.MemberCourse;
import top.course.server.domain.MemberCourseExample;
import top.course.server.dto.MemberCourseDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.MemberCourseMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        memberCourse.setAt(now);
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


    /**
     * 查询：根据课程是否报名返回课程对象或者报名信息
     * @param memberCourseDto 会员报名课程表传输对象
     */
    public MemberCourseDto enroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourseDb = this.select(memberCourseDto.getMemberId(), memberCourseDto.getCourseId());
        if (memberCourseDb == null) {
            MemberCourse memberCourse = CopyUtil.copy(memberCourseDto, MemberCourse.class);
            this.insert(memberCourse);
            /* 将数据库信息全部返回，包括id, at */
            return CopyUtil.copy(memberCourse, MemberCourseDto.class);
        } else {
            // 如果已经报名，则直接返回已报名的信息
            return CopyUtil.copy(memberCourseDb, MemberCourseDto.class);
        }
    }


    /**
     * 查询: 根据会员id和课程id判断会员是否已报名
     * @param memberId 登录会员id
     * @param courseId 报名课程id
     */
    public MemberCourse select(String memberId, String courseId) {
        MemberCourseExample example = new MemberCourseExample();
        example.createCriteria()
                .andCourseIdEqualTo(courseId)
                .andMemberIdEqualTo(memberId);
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(memberCourseList)) {
            return null;
        } else {
            return memberCourseList.get(0);
        }
    }

    /**
     * 查询：获取报名信息
     */
    public MemberCourseDto getEnroll(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = this.select(memberCourseDto.getMemberId(), memberCourseDto.getCourseId());
        return CopyUtil.copy(memberCourse, MemberCourseDto.class);
    }
}