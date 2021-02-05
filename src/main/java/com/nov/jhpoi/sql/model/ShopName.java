package com.nov.jhpoi.sql.model;

public class ShopName extends ShopNameKey {
    private String shopname;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
    }
}