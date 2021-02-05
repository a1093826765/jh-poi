package com.nov.jhpoi.sql.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.sql.mapper.ShopNameMapper;
import com.nov.jhpoi.sql.model.ShopName;
import com.nov.jhpoi.sql.model.ShopNameExample;
import com.nov.jhpoi.sql.model.ShopNameKey;
import com.nov.jhpoi.sql.service.ShopNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:02 下午
 */
@Transactional
@Service(value = "ShopNameService")
public class ShopNameServiceImpl implements ShopNameService {
    @Autowired(required = false)
    private ShopNameMapper ShopNameMapper;

    @Override
    public List<ShopName> getShopNameByExample(ShopNameExample ShopNameExample) {
        return ShopNameMapper.selectByExample(ShopNameExample);
    }

    @Override
    public long getCountByExample(ShopNameExample ShopNameExample) {
        return ShopNameMapper.countByExample(ShopNameExample);
    }

    @Override
    public ShopName getShopNameByKey(ShopNameKey ShopNameKey) {
        return ShopNameMapper.selectByPrimaryKey(ShopNameKey);
    }

    @Override
    public int save(ShopName ShopName) {
        return ShopNameMapper.insert(ShopName);
    }

    @Override
    public int deleteByKey(ShopNameKey ShopNameKey) {
        return ShopNameMapper.deleteByPrimaryKey(ShopNameKey);
    }

    @Override
    public int deleteByExampleAll(ShopNameExample ShopNameExample) {
        return ShopNameMapper.deleteByExample(ShopNameExample);
    }

    @Override
    public int updateByKey(ShopName ShopName) {
        return ShopNameMapper.updateByPrimaryKey(ShopName);
    }

    @Override
    public int updateByExampleAll(ShopName ShopName, ShopNameExample ShopNameyExample) {
        return ShopNameMapper.updateByExample(ShopName, ShopNameyExample);
    }

    @Override
    public PageInfo getMenus(Integer page, Integer limit, ShopNameExample ShopNameExample) {
        PageHelper.startPage(page, limit);
        List<ShopName> ShopNameList = ShopNameMapper.selectByExample(ShopNameExample);
        PageInfo<ShopName> pageInfo = new PageInfo<ShopName>(ShopNameList);
        return pageInfo;
    }
}

