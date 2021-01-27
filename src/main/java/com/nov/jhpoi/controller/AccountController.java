package com.nov.jhpoi.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.nov.jhpoi.resultJson.AccountQueryJson;
import com.nov.jhpoi.resultJson.Data.ShopData;
import com.nov.jhpoi.resultJson.AccountQueryDataJson;
import com.nov.jhpoi.sql.model.*;
import com.nov.jhpoi.sql.service.AccountService;
import com.nov.jhpoi.sql.service.ShopService;
import com.nov.jhpoi.utils.linux.CommandService;
import com.nov.jhpoi.utils.pojo.ResultUtils;
import com.nov.jhpoi.vo.InsertDataVo;
import com.nov.jhpoi.vo.QueryDataVo;
import com.nov.jhpoi.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:08 上午
 */
@RestController
@RequestMapping(value = "/account",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    CommandService commandService;

    @Autowired
    ShopService shopService;

    @PostMapping("/queryData")
    public ResultUtils queryData(@Validated @RequestBody QueryDataVo queryDataVo){
        String cmdData = commandService.executeCmd("ls /");
        System.out.println(cmdData);
        if(cmdData.equals("0")) {
            return ResultUtils.fail(5000,"账号不存在");
        }else {
            AccountExample accountExample = new AccountExample();
            AccountExample.Criteria accountExampleCriteria = accountExample.createCriteria();
            accountExampleCriteria.andAccountEqualTo(queryDataVo.getAccount());
            List<Account> accountList = accountService.getAccountByExample(accountExample);

            String id="";
            ShopData shopData=new ShopData(new JSONArray());
            if (accountList.size() <= 0) {
                //旺旺号不存在
                Date date = DateUtil.date();
                id = UUID.randomUUID().toString();
                Account account = new Account();
                account.setId(id);
                account.setAccount(queryDataVo.getAccount());
                account.setTime(date);
                accountService.save(account);
            }else {
                //旺旺号存在
                Account account=accountList.get(0);
                id=account.getId();
                ShopExample shopExample=new ShopExample();
                ShopExample.Criteria shopExampleCriteria = shopExample.createCriteria();
                shopExampleCriteria.andIdEqualTo(account.getId());
                List<Shop> shopList = shopService.getShopByExample(shopExample);
                for(Shop shop:shopList){
                    shopData.addShopData(shop.getShopid(),shop.getShopname(),shop.getShoptime());
                }
            }
            return ResultUtils.success(new AccountQueryDataJson(id,JSONObject.parseObject("{\"test\":\"test\"}"),shopData).toJson());
        }
    }

    @PostMapping("/query")
    public ResultUtils query(@Validated @RequestBody QueryVo queryVo){
        AccountExample accountExample=new AccountExample();
        AccountExample.Criteria accountExampleCriteria = accountExample.createCriteria();
        accountExampleCriteria.andTimeGreaterThanOrEqualTo(queryVo.getStartTime());
        accountExampleCriteria.andTimeLessThanOrEqualTo(queryVo.getStopTime());
        PageInfo pageInfo = accountService.getMenus(queryVo.getPage(), queryVo.getLimit(), accountExample);
        List<Account> accountList=pageInfo.getList();
        JSONArray jsonArray=new JSONArray();
        for(Account account:accountList) {
            String cmdData = commandService.executeCmd("ls /");
            System.out.println(cmdData);
            jsonArray.add(JSONObject.parseObject(cmdData));
        }
        return ResultUtils.success(new AccountQueryJson(pageInfo.getPageNum(), pageInfo.getPages(), jsonArray));
    }

    @PostMapping("/insertData")
    public ResultUtils insertData(@Validated @RequestBody InsertDataVo insertDataVo){
        AccountKey accountKey=new Account();
        accountKey.setId(insertDataVo.getId());
        Account account = accountService.getAccountByKey(accountKey);
        Shop shop=new Shop();
        UUID id = UUID.randomUUID();
        shop.setShopid(id.toString());
        shop.setId(account.getId());
        shop.setShopname(insertDataVo.getShopName());
        shop.setShoptime(insertDataVo.getShopTime());
        shopService.save(shop);
        return ResultUtils.success();
    }
}
