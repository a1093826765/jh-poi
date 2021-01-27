package com.nov.jhpoi.sql.model;

import java.util.Date;

public class Shop extends ShopKey {
    private String shopname;

    private Date shoptime;

    private String id;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
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