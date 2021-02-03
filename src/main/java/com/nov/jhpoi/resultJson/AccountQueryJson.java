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
    private JSONArray jsonArray;

    public AccountQueryJson(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONObject toJson(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("list",jsonArray);
        return jsonObject;
    }
}
