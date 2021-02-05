package com.nov.jhpoi.sql.model;

public class WeChat extends WeChatKey {
    private String wechatnum;

    public String getWechatnum() {
        return wechatnum;
    }

    public void setWechatnum(String wechatnum) {
        this.wechatnum = wechatnum == null ? null : wechatnum.trim();
    }
}