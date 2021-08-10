package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.Sms;
import top.course.server.domain.SmsExample;
import top.course.server.dto.PageDto;
import top.course.server.dto.SmsDto;
import top.course.server.enums.SmsStatusEnum;
import top.course.server.exception.BusinessException;
import top.course.server.exception.BusinessExceptionCode;
import top.course.server.mapper.SmsMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/08/09 17:31
 * @Description: Sms持久层接口
 */

@Service
public class SmsService {

    @Resource
    private SmsMapper smsMapper;

    private static final Logger LOG = LoggerFactory.getLogger(SmsService.class);

    /**
     * sms表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SmsExample smsExample = new SmsExample();
        List<Sms> smsList = smsMapper.selectByExample(smsExample);

        PageInfo<Sms> pageInfo = new PageInfo<>(smsList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SmsDto> smsDtoList = CopyUtil.copyList(smsList, SmsDto.class);
        pageDto.setList(smsDtoList);
    }

    /**
     * 保存: SmsDto对象有id属性值时更新，无值时新增
     * @param smsDto 数据传输对象
     */
    public void save(SmsDto smsDto) {
        Sms sms = CopyUtil.copy(smsDto, Sms.class);
        if (StringUtils.isEmpty(smsDto.getId())) {
            this.insert(sms);
        } else {
            this.update(sms);
        }
    }

    /**
     * 新增:生成短id作为Sms对象id插入sms表
     * @param sms (无ID)Sms对象
     */
    private void insert(Sms sms) {
        Date now = new Date();
        sms.setId(UUIDUtil.getShortUUID());
        smsMapper.insert(sms);
    }

    /**
     * 更新:根据Sms对象id查询条件修改sms表数据
     * @param sms (有ID)Sms对象
     */
    private void update(Sms sms) {
        smsMapper.updateByPrimaryKey(sms);
    }

    /**
     * 删除: 根据id删除sms表数据
     * @param id id
     */
    public void delete(String id) {
        smsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 发送短信验证码
     * 同手机号同操作1分钟内不能重复发送短信
     * @param smsDto 短信传输对象
     */
    public void sendCode(SmsDto smsDto) {
        SmsExample example = new SmsExample();
        SmsExample.Criteria criteria = example.createCriteria();
        // 查找1分钟内有没有同手机号同操作发送记录且没被用过
        criteria.andMobileEqualTo(smsDto.getMobile())
                .andUseEqualTo(smsDto.getUse())
                .andStatusEqualTo(SmsStatusEnum.NOT_USED.getCode())
                .andAtGreaterThan(new Date(new Date().getTime() - 1 * 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList == null || smsList.size() == 0) {
            saveAndSend(smsDto);
        } else {
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_TOO_FREQUENT);
        }
    }

    /**
     * 保存并发送短信验证码
     * @param smsDto 短信传输对象
     */
    private void saveAndSend(SmsDto smsDto) {
        // 生成6位数字
        String code = String.valueOf((int) (((Math.random() * 9) + 1) * 100000));
        smsDto.setAt(new Date());
        smsDto.setStatus(SmsStatusEnum.NOT_USED.getCode());
        smsDto.setCode(code);
        this.save(smsDto);

        // TODO 调第三方短信接口发送短信

    }

    /**
     * 验证码5分钟内有效，且操作类型要一致
     * @param smsDto 短信传输对象
     */
    public void validCode(SmsDto smsDto) {
        SmsExample example = new SmsExample();
        SmsExample.Criteria criteria = example.createCriteria();
        // 查找5分钟内同手机号同操作发送记录
        criteria.andMobileEqualTo(smsDto.getMobile()).andUseEqualTo(smsDto.getUse()).andAtGreaterThan(new Date(new Date().getTime() - 1 * 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList != null && smsList.size() > 0) {
            Sms smsDb = smsList.get(0);
            if (!smsDb.getCode().equals(smsDto.getCode())) {
                LOG.warn("短信验证码不正确");
                throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_ERROR);
            } else {
                smsDto.setStatus(SmsStatusEnum.USED.getCode());
                smsMapper.updateByPrimaryKey(smsDb);
            }
        } else {
            LOG.warn("短信验证码不存在或已过期，请重新发送短信");
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_EXPIRED);
        }
    }
}