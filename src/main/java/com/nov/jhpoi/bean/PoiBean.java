package com.nov.jhpoi.bean;

import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/2/1 6:13 下午
 */
@Data
public class PoiBean {
    private String accountNum;
    private String weChatNum;
    private Date Time;
    private String ShopName;
    private String shopMoney;

    /**
     * 本地微信号
     */
    private String shopWeChatNum;
}
