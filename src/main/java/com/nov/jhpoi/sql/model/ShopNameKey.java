package com.nov.jhpoi.sql.model;

public class ShopNameKey {
    private String shopnameid;

    public String getShopnameid() {
        return shopnameid;
    }

    public void setShopnameid(String shopnameid) {
        this.shopnameid = shopnameid == null ? null : shopnameid.trim();
    }
}