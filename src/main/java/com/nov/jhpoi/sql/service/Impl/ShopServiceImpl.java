package com.nov.jhpoi.sql.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.sql.mapper.ShopMapper;
import com.nov.jhpoi.sql.model.Shop;
import com.nov.jhpoi.sql.model.ShopExample;
import com.nov.jhpoi.sql.model.ShopKey;
import com.nov.jhpoi.sql.service.ShopService;
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
@Service(value = "ShopService")
public class ShopServiceImpl implements ShopService {
    @Autowired(required = false)
    private ShopMapper shopMapper;

    @Override
    public List<Shop> getShopByExample(ShopExample shopExample) {
        return shopMapper.selectByExample(shopExample);
    }

    @Override
    public long getCountByExample(ShopExample shopExample) {
        return shopMapper.countByExample(shopExample);
    }

    @Override
    public Shop getShopByKey(ShopKey shopKey) {
        return shopMapper.selectByPrimaryKey(shopKey);
    }

    @Override
    public int save(Shop shop) {
        return shopMapper.insert(shop);
    }

    @Override
    public int deleteByKey(ShopKey shopKey) {
        return shopMapper.deleteByPrimaryKey(shopKey);
    }

    @Override
    public int deleteByExampleAll(ShopExample shopExample) {
        return shopMapper.deleteByExample(shopExample);
    }

    @Override
    public int updateByKey(Shop shop) {
        return shopMapper.updateByPrimaryKey(shop);
    }

    @Override
    public int updateByExampleAll(Shop shop, ShopExample shopyExample) {
        return shopMapper.updateByExample(shop, shopyExample);
    }

    @Override
    public PageInfo getMenus(Integer page, Integer limit, ShopExample shopExample) {
        PageHelper.startPage(page, limit);
        List<Shop> shopList = shopMapper.selectByExample(shopExample);
        PageInfo<Shop> pageInfo = new PageInfo<Shop>(shopList);
        return pageInfo;
    }
}

