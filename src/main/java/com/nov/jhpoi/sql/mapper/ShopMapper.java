package com.nov.jhpoi.sql.mapper;

import com.nov.jhpoi.sql.model.Shop;
import com.nov.jhpoi.sql.model.ShopExample;
import com.nov.jhpoi.sql.model.ShopKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopMapper {
    long countByExample(ShopExample example);

    int deleteByExample(ShopExample example);

    int deleteByPrimaryKey(ShopKey key);

    int insert(Shop record);

    int insertSelective(Shop record);

    List<Shop> selectByExample(ShopExample example);

    Shop selectByPrimaryKey(ShopKey key);

    int updateByExampleSelective(@Param("record") Shop record, @Param("example") ShopExample example);

    int updateByExample(@Param("record") Shop record, @Param("example") ShopExample example);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
}