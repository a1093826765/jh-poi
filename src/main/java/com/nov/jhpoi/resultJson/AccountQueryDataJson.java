package com.nov.jhpoi.resultJson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nov.jhpoi.resultJson.Data.ShopData;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:18 下午
 */
@Data
public class AccountQueryDataJson {
    private JSONObject cmdTbData;
    private JSONObject cmdWxData;
    private ShopData shopData;
    private String id;

    public AccountQueryDataJson(String id, JSONObject cmdTbData,JSONObject cmdWxData, ShopData shopData) {
        this.id=id;
        this.cmdWxData=cmdWxData;
        this.cmdTbData = cmdTbData;
        this.shopData = shopData;
    }

    public JSONObject toJson() {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("cmdTbData",cmdTbData);
        jsonObject.put("cmdWxData",cmdWxData);
        jsonObject.put("shopData",shopData.getJsonArray());
        return jsonObject;
    }
}
