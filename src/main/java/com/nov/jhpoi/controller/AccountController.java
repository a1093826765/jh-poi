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
import com.nov.jhpoi.sql.service.WeChatService;
import com.nov.jhpoi.utils.linux.CommandService;
import com.nov.jhpoi.utils.pojo.ResultUtils;
import com.nov.jhpoi.vo.account.QueryTbDataVo;
import com.nov.jhpoi.vo.account.QueryVo;
import com.nov.jhpoi.vo.account.QueryWxDataVo;
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
@RequestMapping(value = "/api/account",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    CommandService commandService;

    @Autowired
    ShopService shopService;

    @Autowired
    WeChatService weChatService;

    /**
     * 查询旺旺号详细数据
     * @param queryTbDataVo
     * @return
     */
    @PostMapping("/queryTbData")
    public ResultUtils queryTbData(@Validated @RequestBody QueryTbDataVo queryTbDataVo){
        String cmdData = commandService.executeCmd("python3 /root/ZYJspider/spider.py tb "+queryTbDataVo.getAccount());
        ResultUtils resultUtils = pdCmdData(cmdData);
        if(resultUtils==null){
            JSONObject cmdDataJson;
            try{
                cmdDataJson=JSONObject.parseObject(cmdData.replaceAll("'","\"").replaceAll("False","false"));
            }catch (Exception e){
                return ResultUtils.fail(500,cmdData);
            }
            String sex = (String) cmdDataJson.get("sex");
            AccountExample accountExample = new AccountExample();
            AccountExample.Criteria accountExampleCriteria = accountExample.createCriteria();
            accountExampleCriteria.andAccountEqualTo(queryTbDataVo.getAccount());
            List<Account> accountList = accountService.getAccountByExample(accountExample);

            String id;
            ShopData shopData=new ShopData(new JSONArray());
            Account account;
            JSONObject cmdWxDataJson = null;
            if (accountList.size() <= 0) {
                //旺旺号不存在
                Date date = DateUtil.date(System.currentTimeMillis());
                id = UUID.randomUUID().toString();
                account = new Account();
                account.setId(id);
                account.setAccount(queryTbDataVo.getAccount());
                account.setSex(sex);
                account.setTime(date);
                accountService.save(account);
            }else {
                //旺旺号存在
                account=accountList.get(0);
                id=account.getId();
                ShopExample shopExample=new ShopExample();
                shopExample.setOrderByClause("shopTime desc");
                ShopExample.Criteria shopExampleCriteria = shopExample.createCriteria();
                shopExampleCriteria.andIdEqualTo(account.getId());
                List<Shop> shopList = shopService.getShopByExample(shopExample);
                for(Shop shop:shopList){
                    shopData.addShopData(shop.getShopid(),shop.getShopname(),shop.getShoptime(),shop.getShopmoney());
                }
                if(account.getWechat()!=null){
                    String cmdWxData = commandService.executeCmd("python3 /root/ZYJspider/spider.py wx "+account.getWechat());
                    try{
                        cmdWxDataJson=JSONObject.parseObject(cmdWxData.replaceAll("'","\"").replaceAll("False","false"));
                    }catch (Exception e){
                        return ResultUtils.fail(500,"WX:  "+cmdData);
                    }
                }
            }
            return ResultUtils.success(new AccountQueryDataJson(id,cmdDataJson,cmdWxDataJson,shopData).toJson());
        }else {
            return resultUtils;
        }
    }

    /**
     * 查询微信号详细数据
     * @param queryWxDataVo
     * @return
     */
    @PostMapping("/queryTbData")
    public ResultUtils queryWxData(@Validated @RequestBody QueryWxDataVo queryWxDataVo){
        String cmdData = commandService.executeCmd("python3 /root/ZYJspider/spider.py wx "+queryWxDataVo.getWeChatNum());
        ResultUtils resultUtils = pdCmdData(cmdData);
        if(resultUtils==null){
            JSONObject cmdDataJson;
            try{
                cmdDataJson=JSONObject.parseObject(cmdData.replaceAll("'","\"").replaceAll("False","false"));
            }catch (Exception e){
                return ResultUtils.fail(500,cmdData);
            }
            return ResultUtils.success(cmdDataJson);
        }else {
            return resultUtils;
        }
    }

    /**
     * 批量查询
     * @param queryVo
     * @return
     */
    @PostMapping("/query")
    public ResultUtils query(@Validated @RequestBody QueryVo queryVo){

        //筛选
        AccountExample accountExample=new AccountExample();
        accountExample.setOrderByClause("time desc");
        AccountExample.Criteria accountExampleCriteria = accountExample.createCriteria();
        accountExampleCriteria.andSexEqualTo(queryVo.getSex());
        accountExampleCriteria.andSexEqualTo("");
        accountExampleCriteria.andIdEqualTo(queryVo.getId());
        accountExampleCriteria.andWechatidEqualTo(queryVo.getWeChatId());
        PageInfo pageInfo = accountService.getMenus(queryVo.getPage(), queryVo.getLimit(), accountExample);
        List<Account> accountList=pageInfo.getList();

        JSONArray jsonArray=new JSONArray();
        int num;
        for(Account account:accountList) {
            String cmdData = commandService.executeCmd("python3 /root/ZYJspider/spider.py tb " + account.getAccount());
            if ("{'iff': 'y'}".equals(cmdData)) {
                return ResultUtils.fail(5002, "请登录捉妖镜输入验证码");
            }
            JSONObject cmdDataJson = null;
            if ("0".equals(cmdData)) {
                //账号不存在
                cmdDataJson.put("status","账号不存在");
            } else {
                //账号存在
               
                try {
                    cmdDataJson = JSONObject.parseObject(cmdData.replaceAll("'", "\"").replaceAll("False", "false"));
                } catch (Exception e) {
                    return ResultUtils.fail(500, cmdData);
                }

                //微信号与本地微信号
                cmdDataJson.put("weChat", account.getWechat());
                WeChatKey weChatKey = new WeChatKey();
                weChatKey.setWechatid(account.getWechatid());
                WeChat weChat = weChatService.getWeChatByKey(weChatKey);
                cmdDataJson.put("ShopWeChat", weChat.getWechatnum());

                //筛选已降权与未降权
                num = 0;
                for (int i = 1; i <= 6; i++) {
                    num += (Integer) cmdDataJson.get("type" + i);
                }
                cmdDataJson.put("account", account.getAccount());
                if (queryVo.getPowerDownId() == 0 && num == 0) {
                    //未降权
                    cmdDataJson.put("status", "未打标");
                } else if (queryVo.getPowerDownId() == 1 && num != 0) {
                    //已降权
                    cmdDataJson.put("status", "已打标");
                }
                cmdDataJson.put("num", num);

            }
            jsonArray.add(cmdDataJson);
        }
        return ResultUtils.success(new AccountQueryJson(accountList.size(),pageInfo.getPageNum(), pageInfo.getPages(), jsonArray).toJson());
    }

    /**
     * 判断python返回
     * @param cmdData
     * @return
     */
    public ResultUtils pdCmdData(String cmdData){
        switch (cmdData) {
            case "0":
                return ResultUtils.fail(5000, "账号不存在");
            case "1":
                return ResultUtils.fail(5001, "提交长度非法，请重新提交！");
            case "{'iff': 'y'}":
                return ResultUtils.fail(5002, "请登录捉妖镜输入验证码");
            default:
                return null;
        }
    }

}
