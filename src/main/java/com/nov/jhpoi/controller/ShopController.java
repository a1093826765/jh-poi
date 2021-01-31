package com.nov.jhpoi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nov.jhpoi.sql.model.*;
import com.nov.jhpoi.sql.service.AccountService;
import com.nov.jhpoi.sql.service.ShopService;
import com.nov.jhpoi.utils.pojo.ResultUtils;
import com.nov.jhpoi.vo.shop.DeleteVo;
import com.nov.jhpoi.vo.shop.InsertVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/31 6:54 下午
 */
@RestController
@RequestMapping(value = "/api/shop",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {
    @Autowired
    AccountService accountService;

    @Autowired
    ShopService shopService;

    /**
     * 添加店铺
     * @param insertVo
     * @return
     */
    @PostMapping("/insert")
    public ResultUtils insert(@Validated @RequestBody InsertVo insertVo){
        AccountKey accountKey=new Account();
        accountKey.setId(insertVo.getId());
        Account account = accountService.getAccountByKey(accountKey);
        Shop shop=new Shop();
        UUID id = UUID.randomUUID();
        shop.setShopid(id.toString());
        shop.setId(account.getId());
        shop.setShopname(insertVo.getShopName());
        shop.setShopmoney(insertVo.getShopMoney());
        shop.setShoptime(insertVo.getShopTime());
        shopService.save(shop);
        return ResultUtils.success();
    }

    /**
     * 删除店铺
     * @param deleteVo
     * @return
     */
    @PostMapping("/delete")
    public ResultUtils delete(@Validated @RequestBody DeleteVo deleteVo){
        ShopKey shopKey=new ShopKey();
        shopKey.setShopid(deleteVo.getShopId());
        shopService.deleteByKey(shopKey);
        return ResultUtils.success();
    }

    /**
     * 查询店铺
     * @param 
     * @return
     */
//    @PostMapping("/query")
//    public ResultUtils query(){
//        List<Shop> shopList = shopService.getShopByExample(new ShopExample());
//        JSONArray jsonArray=new JSONArray();
//        for(Shop shop:shopList){
//            JSONObject jsonObject=new JSONObject();
//            jsonObject.put("shopId",shop.getId());
//            jsonObject.put("shopName",shop.getShopname());
//            jsonObject.put("id",shop.getId());
//            jsonArray.add(jsonObject);
//        }
//        return ResultUtils.success(jsonArray);
//    }
}
