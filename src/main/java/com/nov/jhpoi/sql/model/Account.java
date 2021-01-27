package com.nov.jhpoi.sql.model;

import java.util.Date;

public class Account extends AccountKey {
    private String account;

    private Date time;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}