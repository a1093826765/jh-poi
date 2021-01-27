package com.nov.jhpoi.sql.service;

import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.sql.model.Shop;
import com.nov.jhpoi.sql.model.ShopExample;
import com.nov.jhpoi.sql.model.ShopKey;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:02 下午
 */
public interface ShopService {
    List<Shop> getShopByExample(ShopExample currencyExample);
    long getCountByExample(ShopExample currencyExample);
    Shop getShopByKey(ShopKey currencyKey);
    int save(Shop currency);
    int deleteByKey(ShopKey currencyKey);
    int deleteByExampleAll(ShopExample currencyExample);
    int updateByKey(Shop currency);
    int updateByExampleAll(Shop currency, ShopExample currencyyExample);
    PageInfo getMenus(Integer page, Integer limit, ShopExample currencyExample);
}
