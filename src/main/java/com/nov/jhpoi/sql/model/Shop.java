package com.nov.jhpoi.sql.model;

import java.util.Date;

public class Shop extends ShopKey {
    private String shopnameid;

    private String shopmoney;

    private Date shoptime;

    private String id;

    public String getShopnameid() {
        return shopnameid;
    }

    public void setShopnameid(String shopnameid) {
        this.shopnameid = shopnameid == null ? null : shopnameid.trim();
    }

    public String getShopmoney() {
        return shopmoney;
    }

    public void setShopmoney(String shopmoney) {
        this.shopmoney = shopmoney == null ? null : shopmoney.trim();
    }

    public Date getShoptime() {
        return shoptime;
    }

    public void setShoptime(Date shoptime) {
        this.shoptime = shoptime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
}