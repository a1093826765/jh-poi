package com.nov.jhpoi.sql.mapper;

import com.nov.jhpoi.sql.model.WeChat;
import com.nov.jhpoi.sql.model.WeChatExample;
import com.nov.jhpoi.sql.model.WeChatKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WeChatMapper {
    long countByExample(WeChatExample example);

    int deleteByExample(WeChatExample example);

    int deleteByPrimaryKey(WeChatKey key);

    int insert(WeChat record);

    int insertSelective(WeChat record);

    List<WeChat> selectByExample(WeChatExample example);

    WeChat selectByPrimaryKey(WeChatKey key);

    int updateByExampleSelective(@Param("record") WeChat record, @Param("example") WeChatExample example);

    int updateByExample(@Param("record") WeChat record, @Param("example") WeChatExample example);

    int updateByPrimaryKeySelective(WeChat record);

    int updateByPrimaryKey(WeChat record);
}