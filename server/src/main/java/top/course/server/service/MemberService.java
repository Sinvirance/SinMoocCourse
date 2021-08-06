package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.Member;
import top.course.server.domain.MemberExample;
import top.course.server.dto.MemberDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.MemberMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Member持久层接口
 */

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;

    /**
     * member表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        MemberExample memberExample = new MemberExample();
        List<Member> memberList = memberMapper.selectByExample(memberExample);

        PageInfo<Member> pageInfo = new PageInfo<>(memberList);
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberDto> memberDtoList = CopyUtil.copyList(memberList, MemberDto.class);
        pageDto.setList(memberDtoList);
    }

    /**
     * 保存: MemberDto对象有id属性值时更新，无值时新增
     * @param memberDto 数据传输对象
     */
    public void save(MemberDto memberDto) {
        Member member = CopyUtil.copy(memberDto, Member.class);
        if (StringUtils.isEmpty(memberDto.getId())) {
            this.insert(member);
        } else {
            this.update(member);
        }
    }

    /**
     * 新增:生成短id作为Member对象id插入member表
     * @param member (无ID)Member对象
     */
    private void insert(Member member) {
        Date now = new Date();
        member.setId(UUIDUtil.getShortUUID());
        member.setRegisterTime(now);
        memberMapper.insert(member);
    }

    /**
     * 更新:根据Member对象id查询条件修改member表数据
     * @param member (有ID)Member对象
     */
    private void update(Member member) {
        memberMapper.updateByPrimaryKey(member);
    }

    /**
     * 删除: 根据id删除member表数据
     * @param id id
     */
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey(id);
    }
}