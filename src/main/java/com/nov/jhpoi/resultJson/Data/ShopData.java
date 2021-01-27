package com.nov.jhpoi.resultJson.Data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nov.jhpoi.sql.model.Shop;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:30 下午
 */
@Data
public class ShopData {
    private JSONArray jsonArray;

    public ShopData(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public void addShopData(String shopId, String shopName, Date shopTime) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("shopId",shopId);
        jsonObject.put("shopName",shopName);
        jsonObject.put("shopTime",shopTime);
        this.jsonArray.add(jsonObject);
    }
    
}
