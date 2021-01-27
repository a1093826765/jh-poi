package com.nov.jhpoi.sql.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.sql.mapper.AccountMapper;
import com.nov.jhpoi.sql.model.Account;
import com.nov.jhpoi.sql.model.AccountExample;
import com.nov.jhpoi.sql.model.AccountKey;
import com.nov.jhpoi.sql.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 12:56 上午
 */
@Transactional
@Service(value = "AccountService")
public class AccountServiceImpl implements AccountService {
    @Autowired(required = false)
    private AccountMapper accountMapper;

    @Override
    public List<Account> getAccountByExample(AccountExample accountExample) {
        return accountMapper.selectByExample(accountExample);
    }

    @Override
    public long getCountByExample(AccountExample accountExample) {
        return accountMapper.countByExample(accountExample);
    }

    @Override
    public Account getAccountByKey(AccountKey accountKey) {
        return accountMapper.selectByPrimaryKey(accountKey);
    }

    @Override
    public int save(Account account) {
        return accountMapper.insert(account);
    }

    @Override
    public int deleteByKey(AccountKey accountKey) {
        return accountMapper.deleteByPrimaryKey(accountKey);
    }

    @Override
    public int deleteByExampleAll(AccountExample accountExample) {
        return accountMapper.deleteByExample(accountExample);
    }

    @Override
    public int updateByKey(Account account) {
        return accountMapper.updateByPrimaryKey(account);
    }

    @Override
    public int updateByExampleAll(Account account, AccountExample accountyExample) {
        return accountMapper.updateByExample(account, accountyExample);
    }

    @Override
    public PageInfo getMenus(Integer page, Integer limit, AccountExample accountExample) {
        PageHelper.startPage(page, limit);
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        PageInfo<Account> pageInfo = new PageInfo<Account>(accountList);
        return pageInfo;
    }
}
