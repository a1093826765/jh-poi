package com.nov.jhpoi.sql.service;

import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.sql.model.Account;
import com.nov.jhpoi.sql.model.AccountExample;
import com.nov.jhpoi.sql.model.AccountKey;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 12:54 上午
 */
public interface AccountService {
     List<Account> getAccountByExample(AccountExample currencyExample);
     long getCountByExample(AccountExample currencyExample);
     Account getAccountByKey(AccountKey currencyKey);
     int save(Account currency);
     int deleteByKey(AccountKey currencyKey);
     int deleteByExampleAll(AccountExample currencyExample);
     int updateByKey(Account currency);
     int updateByExampleAll(Account currency, AccountExample currencyyExample);
     PageInfo getMenus(Integer page, Integer limit, AccountExample currencyExample);
}
