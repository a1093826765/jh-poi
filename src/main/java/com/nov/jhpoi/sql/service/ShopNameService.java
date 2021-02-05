package com.nov.jhpoi.sql.service;

import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.sql.model.ShopName;
import com.nov.jhpoi.sql.model.ShopNameExample;
import com.nov.jhpoi.sql.model.ShopNameKey;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:02 下午
 */
public interface ShopNameService {
    List<ShopName> getShopNameByExample(ShopNameExample currencyExample);
    long getCountByExample(ShopNameExample currencyExample);
    ShopName getShopNameByKey(ShopNameKey currencyKey);
    int save(ShopName currency);
    int deleteByKey(ShopNameKey currencyKey);
    int deleteByExampleAll(ShopNameExample currencyExample);
    int updateByKey(ShopName currency);
    int updateByExampleAll(ShopName currency, ShopNameExample currencyyExample);
    PageInfo getMenus(Integer page, Integer limit, ShopNameExample currencyExample);
}
