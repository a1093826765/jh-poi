package com.nov.jhpoi.resultJson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 5:51 下午
 */
@Data
public class AccountQueryJson {
    private Integer page;
    private Integer pageSize;
    private Integer dataSize;
    private JSONArray jsonArray;

    public AccountQueryJson(Integer dataSize,Integer page, Integer pageSize, JSONArray jsonArray) {
        this.dataSize=dataSize;
        this.page = page;
        this.pageSize = pageSize;
        this.jsonArray = jsonArray;
    }

    public JSONObject toJson(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("dataSize",dataSize);
        jsonObject.put("pageSize",pageSize);
        jsonObject.put("list",jsonArray);
        return jsonObject;
    }
}
