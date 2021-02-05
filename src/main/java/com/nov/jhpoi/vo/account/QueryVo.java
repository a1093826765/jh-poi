package com.nov.jhpoi.vo.account;

import lombok.Data;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:34 上午
 */
@Data
public class QueryVo {

    /**
     * 降权
     * 0：无，1：有
     */
    private Integer powerDownId;

    private String sex;

    /**
     * 微信号Id
     */
    private String weChatId;

    /**
     * 店铺Id
     */
    private String shopNameId;

}
