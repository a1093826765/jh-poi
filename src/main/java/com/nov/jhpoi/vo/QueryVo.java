package com.nov.jhpoi.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:34 上午
 */
@Data
public class QueryVo {
    private Date StartTime;
    private Date StopTime;
    private Integer typeId;
    private Integer page;
    private Integer limit;
}
