package com.nov.jhpoi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nov.jhpoi.sql.model.*;
import com.nov.jhpoi.sql.service.AccountService;
import com.nov.jhpoi.sql.service.ShopNameService;
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

    @Autowired
    ShopNameService shopNameService;

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
        ShopNameExample shopNameExample=new ShopNameExample();
        ShopNameExample.Criteria shopNameExampleCriteria = shopNameExample.createCriteria();
        shopNameExampleCriteria.andShopnameEqualTo(insertVo.getShopName());
        List<ShopName> shopNameList = shopNameService.getShopNameByExample(shopNameExample);
        if(shopNameList.size()>0){
            //店铺存在
            shop.setShopnameid(shopNameList.get(0).getShopnameid());
        }else{
            //店铺不存在
            UUID shopNameId = UUID.randomUUID();
            ShopName shopName=new ShopName();
            shopName.setShopnameid(shopNameId.toString());
            shopName.setShopname(insertVo.getShopName());
            shopNameService.save(shopName);
            shop.setShopnameid(shopNameId.toString());
        }
        shop.setShopmoney(insertVo.getShopMoney());
        shop.setShoptime(insertVo.getShopTime());
        shopService.save(shop);
        return ResultUtils.success(id);
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

//    /**
//     * 查询店铺
//     * @param
//     * @return
//     */
//    @PostMapping("/query")
//    public ResultUtils query(){
//        List<Shop> shopList = shopService.getShopByExample(new ShopExample());
//        JSONArray jsonArray=new JSONArray();
//        ShopNameKey shopNameKey=new ShopNameKey();
//        ShopName shopName;
//        for(Shop shop:shopList){
//            JSONObject jsonObject=new JSONObject();
//            jsonObject.put("shopId",shop.getId());
//            shopNameKey.setShopnameid(shop.getShopnameid());
//            shopName = shopNameService.getShopNameByKey(shopNameKey);
//            jsonObject.put("shopName",shopName.getShopname());
//            jsonArray.add(jsonObject);
//        }
//        return ResultUtils.success(jsonArray);
//    }
}
