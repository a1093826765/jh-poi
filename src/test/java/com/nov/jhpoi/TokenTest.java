package com.nov.jhpoi;

import com.nov.jhpoi.config.Address;
import com.nov.jhpoi.utils.pojo.TokenUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:13 上午
 */
public class TokenTest {

    @Test
    public void get(){
        System.out.println(new Date());
        TimeZone timeZone = TimeZone.getTimeZone("GMT+0");
        TimeZone.setDefault(timeZone);
        System.out.println(new Date());
    }
}
