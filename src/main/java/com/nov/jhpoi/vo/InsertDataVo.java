package com.nov.jhpoi.vo;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 12:53 下午
 */
@Data
public class InsertDataVo {
    private String id;

    private String shopName;

    private Date shopTime;
}
