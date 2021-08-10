package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.Member;
import top.course.server.domain.MemberExample;
import top.course.server.dto.LoginMemberDto;
import top.course.server.dto.MemberDto;
import top.course.server.dto.PageDto;
import top.course.server.exception.BusinessException;
import top.course.server.exception.BusinessExceptionCode;
import top.course.server.mapper.MemberMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Member持久层接口
 */

@Service
public class MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

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


    /**
     * 查询：根据手机号查找member表会员信息
     * @param mobile 手机号
     */
    public MemberDto findByMobile(String mobile) {
        Member member = this.selectByMobile(mobile);
        return CopyUtil.copy(member, MemberDto.class);
    }

    /**
     * 查询：根据手机号查找member表会员信息
     * @param mobile 手机号
     * @return 返回会员对象 Member
     */
    public Member selectByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        MemberExample example = new MemberExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<Member> memberList = memberMapper.selectByExample(example);
        /* 获取查询到指定手机号的会员对象 */
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }
    }

    /**
     * 查询：根据手机号查询该用户是否能登录（已注册且密码输入正确）
     * @param memberDto 会员登录信息传输对象
     */
    public LoginMemberDto login(MemberDto memberDto) {
        Member member = selectByMobile(memberDto.getMobile());
        if (member == null) {
            LOG.info("手机号不存在, {}", memberDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
        } else {
            if (member.getPassword().equals(memberDto.getPassword())) {
                /* 登录成功,将数据传输给前端 */
                LoginMemberDto loginMemberDto = CopyUtil.copy(member, LoginMemberDto.class);
                return loginMemberDto;
            } else {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", memberDto.getPassword(), member.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
            }
        }
    }


    /**
     * 查询会员是否存在，存在重置密码
     */
    public void resetPassword(MemberDto memberDto) throws BusinessException {
        Member memberDb = this.selectByMobile(memberDto.getMobile());
        if (memberDb == null) {
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        } else {
            Member member = new Member();
            member.setId(memberDb.getId());
            member.setPassword(memberDto.getPassword());
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }
}