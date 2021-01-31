package com.nov.jhpoi.sql.service;

import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.sql.model.WeChat;
import com.nov.jhpoi.sql.model.WeChatExample;
import com.nov.jhpoi.sql.model.WeChatKey;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/31 6:43 下午
 */
public interface WeChatService {
    List<WeChat> getWeChatByExample(WeChatExample currencyExample);
    long getCountByExample(WeChatExample currencyExample);
    WeChat getWeChatByKey(WeChatKey currencyKey);
    int save(WeChat currency);
    int deleteByKey(WeChatKey currencyKey);
    int deleteByExampleAll(WeChatExample currencyExample);
    int updateByKey(WeChat currency);
    int updateByExampleAll(WeChat currency, WeChatExample currencyyExample);
    PageInfo getMenus(Integer page, Integer limit, WeChatExample currencyExample);
}
