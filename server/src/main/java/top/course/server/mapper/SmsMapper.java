package top.course.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.course.server.domain.Sms;
import top.course.server.domain.SmsExample;

public interface SmsMapper {
    long countByExample(SmsExample example);

    int deleteByExample(SmsExample example);

    int deleteByPrimaryKey(String id);

    int insert(Sms record);

    int insertSelective(Sms record);

    List<Sms> selectByExample(SmsExample example);

    Sms selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Sms record, @Param("example") SmsExample example);

    int updateByExample(@Param("record") Sms record, @Param("example") SmsExample example);

    int updateByPrimaryKeySelective(Sms record);

    int updateByPrimaryKey(Sms record);
}