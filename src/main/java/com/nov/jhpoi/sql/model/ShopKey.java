package com.nov.jhpoi.sql.model;

public class ShopKey {
    private String shopid;

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid == null ? null : shopid.trim();
    }
}