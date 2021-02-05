package com.nov.jhpoi.sql.mapper;

import com.nov.jhpoi.sql.model.ShopName;
import com.nov.jhpoi.sql.model.ShopNameExample;
import com.nov.jhpoi.sql.model.ShopNameKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopNameMapper {
    long countByExample(ShopNameExample example);

    int deleteByExample(ShopNameExample example);

    int deleteByPrimaryKey(ShopNameKey key);

    int insert(ShopName record);

    int insertSelective(ShopName record);

    List<ShopName> selectByExample(ShopNameExample example);

    ShopName selectByPrimaryKey(ShopNameKey key);

    int updateByExampleSelective(@Param("record") ShopName record, @Param("example") ShopNameExample example);

    int updateByExample(@Param("record") ShopName record, @Param("example") ShopNameExample example);

    int updateByPrimaryKeySelective(ShopName record);

    int updateByPrimaryKey(ShopName record);
}