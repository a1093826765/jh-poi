package com.nov.jhpoi.sql.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.sql.mapper.WeChatMapper;
import com.nov.jhpoi.sql.model.WeChat;
import com.nov.jhpoi.sql.model.WeChatExample;
import com.nov.jhpoi.sql.model.WeChatKey;
import com.nov.jhpoi.sql.service.WeChatService;
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
@Service(value = "WeChatService")
public class WeChatServiceImpl implements WeChatService {
    @Autowired(required = false)
    private WeChatMapper weChatMapper;

    @Override
    public List<WeChat> getWeChatByExample(WeChatExample weChatExample) {
        return weChatMapper.selectByExample(weChatExample);
    }

    @Override
    public long getCountByExample(WeChatExample weChatExample) {
        return weChatMapper.countByExample(weChatExample);
    }

    @Override
    public WeChat getWeChatByKey(WeChatKey weChatKey) {
        return weChatMapper.selectByPrimaryKey(weChatKey);
    }

    @Override
    public int save(WeChat weChat) {
        return weChatMapper.insert(weChat);
    }

    @Override
    public int deleteByKey(WeChatKey weChatKey) {
        return weChatMapper.deleteByPrimaryKey(weChatKey);
    }

    @Override
    public int deleteByExampleAll(WeChatExample weChatExample) {
        return weChatMapper.deleteByExample(weChatExample);
    }

    @Override
    public int updateByKey(WeChat weChat) {
        return weChatMapper.updateByPrimaryKey(weChat);
    }

    @Override
    public int updateByExampleAll(WeChat weChat, WeChatExample weChatyExample) {
        return weChatMapper.updateByExample(weChat, weChatyExample);
    }

    @Override
    public PageInfo getMenus(Integer page, Integer limit, WeChatExample weChatExample) {
        PageHelper.startPage(page, limit);
        List<WeChat> weChatList = weChatMapper.selectByExample(weChatExample);
        PageInfo<WeChat> pageInfo = new PageInfo<WeChat>(weChatList);
        return pageInfo;
    }
}

