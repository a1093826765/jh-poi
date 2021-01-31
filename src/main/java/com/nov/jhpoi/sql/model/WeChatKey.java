package com.nov.jhpoi.sql.model;

public class WeChatKey {
    private String wechatid;

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid == null ? null : wechatid.trim();
    }
}