package com.nov.jhpoi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nov.jhpoi.sql.model.ShopName;
import com.nov.jhpoi.sql.model.ShopNameExample;
import com.nov.jhpoi.sql.service.ShopNameService;
import com.nov.jhpoi.utils.pojo.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/2/5 4:08 下午
 */
@RestController
@RequestMapping(value = "/api/shopName",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
public class ShopNameController {
    @Autowired
    ShopNameService ShopNameService;

    /**
     * 查询店铺名
     * @param
     * @return
     */
    @PostMapping("/query")
    public ResultUtils query(){
        List<ShopName> ShopNameList = ShopNameService.getShopNameByExample(new ShopNameExample());
        JSONArray jsonArray=new JSONArray();
        for(ShopName ShopName:ShopNameList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("ShopNameId",ShopName.getShopnameid());
            jsonObject.put("ShopNameName",ShopName.getShopname());
            jsonArray.add(jsonObject);
        }
        return ResultUtils.success(jsonArray);
    }
}
