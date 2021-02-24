package com.nov.jhpoi.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nov.jhpoi.config.Address;
import com.nov.jhpoi.resultJson.AccountQueryJson;
import com.nov.jhpoi.resultJson.Data.ShopData;
import com.nov.jhpoi.resultJson.AccountQueryDataJson;
import com.nov.jhpoi.sql.model.*;
import com.nov.jhpoi.sql.service.AccountService;
import com.nov.jhpoi.sql.service.ShopNameService;
import com.nov.jhpoi.sql.service.ShopService;
import com.nov.jhpoi.sql.service.WeChatService;
import com.nov.jhpoi.utils.file.FileService;
import com.nov.jhpoi.utils.linux.CommandService;
import com.nov.jhpoi.utils.pojo.ResultUtils;
import com.nov.jhpoi.vo.account.UpdateWeChatVo;
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

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/27 1:08 上午
 */
@RestController
@RequestMapping(value = "/api/account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    CommandService commandService;

    @Autowired
    ShopService shopService;

    @Autowired
    WeChatService weChatService;

    @Autowired
    FileService fileService;

    @Autowired
    ShopNameService shopNameService;

    /**
     * 查询旺旺号详细数据
     *
     * @param queryTbDataVo
     * @return
     */
    @PostMapping("/queryTbData")
    public ResultUtils queryTbData(@Validated @RequestBody QueryTbDataVo queryTbDataVo) {
        String cmdData = commandService.executeCmd("python3 /root/ZYJspider/spider.py tb " + queryTbDataVo.getAccount());
//        System.out.println(cmdData);
//        String cmdData = "{'type1Ph': 0, 'result': '正常', 'nearWeekShop': 11, 'sex': '男', 'vip_level': '0', 'badNum': 0, 'lastWeekShop': 5, 'type6Ph': 0, 'type3Ph': 0, 'yunBlack': 0, 'lowVip3': 'no', 'received_rate': '100.00%', 'downNum': 1,'costType': 'costType5', 'created': '2003-06-22 06:59:25(约17.62年)', 'type1': 0, 'goodNum': 0, 'type3': 1, 'type2': 1, 'type2Ph': 0, 'type5': 0, 'type4': 1, 'type6': 0, 'isNeedCost': False, 'item_num': 0, 'costMonsteoin': 0, 'aliimSim': '哈哈', 'proveNum': 0, 'nameconform_word': '未实名', 'buyerAvg': '0.00', 'buyerCre': '0心', 'queryTime': '2021-01-31 19:30:34', 'vip_info': 'vip0', 'type5Ph': 0, 'registDay': '6433天', 'sellerCre':'未开店', 'nameconform_word_color': '#808080', 'ifShow': 'yes', 'type4Ph': 0}";
        ResultUtils resultUtils = pdCmdDataOne(cmdData);
        if (resultUtils == null) {
            //查询结果无问题
            JSONObject cmdDataJson;
            try {
                //查询结果转json
                cmdDataJson = JSONObject.parseObject(cmdData.replaceAll("'", "\"").replaceAll("False", "false"));
            } catch (Exception e) {
                return ResultUtils.fail(500, cmdData);
            }

            //打标数
            int tbNum = 0;
            for (int i = 1; i <= 6; i++) {
                tbNum += (Integer) cmdDataJson.get("type" + i);
            }
            cmdDataJson.put("tbNum", tbNum);

            //查询旺旺是否存在，存在则获取，不存在则保存到数据库
            AccountExample accountExample = new AccountExample();
            AccountExample.Criteria accountExampleCriteria = accountExample.createCriteria();
            accountExampleCriteria.andAccountEqualTo(queryTbDataVo.getAccount());
            List<Account> accountList = accountService.getAccountByExample(accountExample);

            //旺旺号id
            String id;
            //店铺返回
            ShopData shopData = new ShopData(new JSONArray());
            Account account;
            //微信查询json
            JSONObject cmdWxDataJson = new JSONObject();
            if (accountList.size() <= 0) {
                //旺旺号不存在,保存旺旺
                id = UUID.randomUUID().toString();
                account = new Account();
                account.setId(id);
                account.setAccount(queryTbDataVo.getAccount());
                account.setSex((String) cmdDataJson.get("sex"));
                TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
                TimeZone.setDefault(timeZone);
                account.setTime(new Date());
                accountService.save(account);
            } else {
                //旺旺号存在，获取旺旺号
                account = accountList.get(0);
                id = account.getId();

                //根据旺旺号查询店铺
                ShopExample shopExample = new ShopExample();
                shopExample.setOrderByClause("shopTime desc");
                ShopExample.Criteria shopExampleCriteria = shopExample.createCriteria();
                shopExampleCriteria.andIdEqualTo(account.getId());
                List<Shop> shopList = shopService.getShopByExample(shopExample);

                ShopNameKey shopNameKey = new ShopNameKey();
                ShopName shopName;
                for (Shop shop : shopList) {
                    //向店铺返回添加数据
                    shopNameKey.setShopnameid(shop.getShopnameid());
                    shopName = shopNameService.getShopNameByKey(shopNameKey);
                    shopData.addShopData(shop.getShopid(), shopName.getShopname(), shop.getShoptime(), shop.getShopmoney());
                }

                if (account.getWechat() != null && !"".equals(account.getWechat())) {
                    //旺旺对应的微信号不为空
                    cmdWxDataJson = twoQueryWxData(cmdWxDataJson, account);
                    cmdDataJson.put("weChatNum", account.getWechat());
                }
            }
            String shopWeChatNum = null;
            if (account.getWechatid() != null) {
                WeChatKey weChatKey = new WeChatKey();
                weChatKey.setWechatid(account.getWechatid());
                shopWeChatNum = weChatService.getWeChatByKey(weChatKey).getWechatnum();
            }
            cmdDataJson.put("shopWeChatNum", shopWeChatNum);
            fileService.updateTxtFile(account.getId() + ".txt", cmdDataJson,Address.FILE_PATH);
            return ResultUtils.success(new AccountQueryDataJson(id, cmdDataJson, cmdWxDataJson, shopData).toJson());
        } else {
            return resultUtils;
        }
    }

    /**
     * 查询微信号详细数据
     *
     * @param queryWxDataVo
     * @return
     */
    @PostMapping("/queryWxData")
    public ResultUtils queryWxData(@Validated @RequestBody QueryWxDataVo queryWxDataVo) {
//        String cmdData = "{'name': 'aa1610148754', 'fox': '有', 'crocodile': '无'}";
        String cmdData = commandService.executeCmd("python3 /root/ZYJspider/spider.py wx " + queryWxDataVo.getWeChatNum());
        ResultUtils resultUtils = pdCmdDataOne(cmdData);
        if (resultUtils == null) {
            JSONObject cmdDataJson;
            try {
                cmdDataJson = JSONObject.parseObject(cmdData.replaceAll("'", "\"").replaceAll("False", "false"));
            } catch (Exception e) {
                return ResultUtils.fail(500, cmdData);
            }
            return ResultUtils.success(cmdDataJson);
        } else {
            return resultUtils;
        }
    }

    /**
     * 批量查询
     *
     * @param queryVo
     * @return
     */
    @PostMapping("/query")
    public ResultUtils query(@Validated @RequestBody QueryVo queryVo) {

        //筛选
        AccountExample accountExample = new AccountExample();
        //时间倒序
        accountExample.setOrderByClause("time desc");
        //条件一
        AccountExample.Criteria accountExampleCriteria = accountExample.createCriteria();
        accountExampleCriteria.andSexEqualTo(queryVo.getSex());
        accountExampleCriteria.andWechatidEqualTo(queryVo.getWeChatId());

        //条件二
        AccountExample.Criteria accountExampleCriteria2 = accountExample.createCriteria();
        accountExampleCriteria2.andSexEqualTo("");
        accountExampleCriteria2.andWechatidEqualTo(queryVo.getWeChatId());
        //条件一 or 条件二
        accountExample.or(accountExampleCriteria2);
        //查询
        List<Account> accountList = accountService.getAccountByExample(accountExample);

        JSONArray jsonArray = new JSONArray();
        JSONObject cmdDataJson;
        AccountKey accountKey = new AccountKey();
        ShopExample shopExample = new ShopExample();
        long shopCount;

        for (Account account : accountList) {
            //筛选店铺（未登记店铺）
            shopExample.clear();
            ShopExample.Criteria shopExampleCriteria = shopExample.createCriteria();
            shopExampleCriteria.andShopnameidEqualTo(queryVo.getShopNameId());
            shopExampleCriteria.andIdEqualTo(account.getId());
            shopCount = shopService.getCountByExample(shopExample);
            if (shopCount <= 0) {
                File file = new File(Address.FILE_PATH + account.getId() + ".txt");
                if (file.exists()) {
                    //文件存在
                    String conTxt = fileService.queryTxtFilePath(account.getId(),Address.FILE_PATH);
                    cmdDataJson = JSONObject.parseObject(conTxt);
                    jsonArray = queryUtils(account, jsonArray, cmdDataJson, queryVo);
                } else {
                    //文件不存在
//            String cmdData = "{'type1Ph': 0, 'result': '正常', 'nearWeekShop': 11, 'sex': '男', 'vip_level': '0', 'badNum': 0, 'lastWeekShop': 5, 'type6Ph': 0, 'type3Ph': 0, 'yunBlack': 0, 'lowVip3': 'no', 'received_rate': '100.00%', 'downNum': 1,'costType': 'costType5', 'created': '2003-06-22 06:59:25(约17.62年)', 'type1': 0, 'goodNum': 0, 'type3': 1, 'type2': 1, 'type2Ph': 0, 'type5': 0, 'type4': 1, 'type6': 0, 'isNeedCost': False, 'item_num': 0, 'costMonsteoin': 0, 'aliimSim': '哈哈', 'proveNum': 0, 'nameconform_word': '未实名', 'buyerAvg': '0.00', 'buyerCre': '0心', 'queryTime': '2021-01-31 19:30:34', 'vip_info': 'vip0', 'type5Ph': 0, 'registDay': '6433天', 'sellerCre':'未开店', 'nameconform_word_color': '#808080', 'ifShow': 'yes', 'type4Ph': 0}";
//                String cmdData="0";
                    String cmdData = commandService.executeCmd("python3 /root/ZYJspider/spider.py tb " + account.getAccount());
                    if ("{'iff': 'y'}".equals(cmdData)) {
                        return ResultUtils.fail(5002, "请登录捉妖镜输入验证码");
                    }
                    if ("0".equals(cmdData)) {
                        //账号不存在
                        accountKey.setId(account.getId());
                        accountService.deleteByKey(accountKey);
                    } else {
                        //账号存在

                        try {
                            cmdDataJson = JSONObject.parseObject(cmdData.replaceAll("'", "\"").replaceAll("False", "false"));
                        } catch (Exception e) {
                            return ResultUtils.fail(500, cmdData);
                        }
                        fileService.updateTxtFile(account.getId() + ".txt", cmdDataJson,Address.FILE_PATH);
                        jsonArray = queryUtils(account, jsonArray, cmdDataJson, queryVo);
                    }
                }
            }
        }
        return ResultUtils.success(new AccountQueryJson(jsonArray).toJson());
    }

    /**
     * 修改本地微信号与微信号
     *
     * @param
     * @return
     */
    @PostMapping("/updateWeChat")
    public ResultUtils updateWeChat(@Validated @RequestBody UpdateWeChatVo updateWeChatVo) {
        AccountKey accountKey = new AccountKey();
        accountKey.setId(updateWeChatVo.getId());
        Account account = accountService.getAccountByKey(accountKey);

        if (account != null) {
            if(updateWeChatVo.getShopWeChatNum().equals("") || updateWeChatVo.getShopWeChatNum()==null){
                account.setWechatid(null);
            }else {
                WeChatExample weChatExample = new WeChatExample();
                WeChatExample.Criteria weChatExampleCriteria = weChatExample.createCriteria();
                weChatExampleCriteria.andWechatnumEqualTo(updateWeChatVo.getShopWeChatNum());
                List<WeChat> weChatList = weChatService.getWeChatByExample(weChatExample);
                String weChatId;
                if (weChatList.size() > 0) {
                    WeChat weChat = weChatList.get(0);
                    weChat.setWechatnum(updateWeChatVo.getShopWeChatNum());
                    weChatService.updateByKey(weChat);
                    weChatId = weChat.getWechatid();
                } else {
                    WeChat weChat = new WeChat();
                    weChatId = UUID.randomUUID().toString();
                    weChat.setWechatid(weChatId);
                    weChat.setWechatnum(updateWeChatVo.getShopWeChatNum());
                    weChatService.save(weChat);
                }
                account.setWechatid(weChatId);
            }
            account.setWechat(updateWeChatVo.getWeChatNum());
            accountService.updateByKey(account);
            return ResultUtils.success();
        } else {
            return ResultUtils.fail(5000, "该账号不存在");
        }
    }

    /**
     * 打标数
     */
    int wxNum = 0;

    /**
     * 二级微信查询
     *
     * @param cmdWxDataJson
     * @param account
     * @return
     */
    public JSONObject twoQueryWxData(JSONObject cmdWxDataJson, Account account) {
//        String cmdWxData = "{'name': 'aa1610148754', 'fox': '有', 'crocodile': '无'}";
        String cmdWxData = commandService.executeCmd("python3 /root/ZYJspider/spider.py wx " + account.getWechat());
        switch (cmdWxData) {
            case "0":
                //此微信号不存在，删除微信号
//                account.setWechat("");
//                accountService.updateByKey(account);
                break;
            case "1":
                //捉妖镜出错
                break;
            default:
                try {
                    //微信号查询json
                    cmdWxDataJson = JSONObject.parseObject(cmdWxData.replaceAll("'", "\"").replaceAll("False", "false").replaceAll("有","yes").replaceAll("无","no"));
                    fileService.updateTxtFile(account.getWechat()+".txt",cmdWxDataJson,Address.WX_FILE_PATH);
                    //打标数
                    wxNum = 0;
                    if ("yes".equals(cmdWxDataJson.get("fox"))) {
                        wxNum++;
                    }
                    if ("yes".equals(cmdWxDataJson.get("crocodile"))) {
                        wxNum++;
                    }
                    cmdWxDataJson.put("wxNum", wxNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return cmdWxDataJson;
    }

    /**
     * 判断python返回(一次查询)
     *
     * @param cmdData
     * @return
     */
    public ResultUtils pdCmdDataOne(String cmdData) {
        switch (cmdData) {
            case "0":
                return ResultUtils.fail(5000, "账号不存在");
            case "1":
                return ResultUtils.fail(5001, "捉妖镜出错，请检查账号是否正确，捉妖镜是否正常运行");
            case "2":
                return ResultUtils.fail(5002, "提交长度非法，请重新提交！");
            case "{'iff': 'y'}":
                return ResultUtils.fail(5003, "请登录捉妖镜输入验证码");
            default:
                return null;
        }
    }

    /**
     * 批量查询工具方法
     *
     * @param account
     * @param jsonArray
     * @param cmdDataJson
     * @param queryVo
     * @return
     */
    WeChatKey weChatKey = new WeChatKey();
    JSONObject cmdWxDataJson;

    public JSONArray queryUtils(Account account, JSONArray jsonArray, JSONObject cmdDataJson, QueryVo queryVo) {
        //更新性别，并筛选
        cmdWxDataJson = new JSONObject();
        String sex = (String) cmdDataJson.get("sex");
        if (!account.getSex().equals(sex)) {
            account.setSex(sex);
            accountService.updateByKey(account);
        }
        if (sex.equals(queryVo.getSex())) {
            //微信号与本地微信号
            weChatKey.setWechatid(account.getWechatid());
            WeChat weChat = weChatService.getWeChatByKey(weChatKey);
            cmdDataJson.put("ShopWeChat", weChat.getWechatnum());


            if (account.getWechat() != null && !"".equals(account.getWechat())) {
                //旺旺对应的微信号不为空
                File file = new File(Address.WX_FILE_PATH + account.getWechat() + ".txt");
                if(file.exists()){
                    //文件存在
                    cmdWxDataJson=JSONObject.parseObject(fileService.queryTxtFilePath(account.getWechat(),Address.WX_FILE_PATH));
                    //打标数
                    wxNum = 0;
                    if ("yes".equals(cmdWxDataJson.get("fox"))) {
                        wxNum++;
                    }
                    if ("yes".equals(cmdWxDataJson.get("crocodile"))) {
                        wxNum++;
                    }
                    cmdWxDataJson.put("wxNum", wxNum);
                }else {
                    //文件不存在
                    cmdWxDataJson = twoQueryWxData(cmdWxDataJson, account);
                }
                cmdDataJson.put("weChatNum", account.getWechat());
            }
            cmdDataJson.put("wxData", cmdWxDataJson);

            //筛选已降权与未降权
            int num = 0;
            for (int i = 1; i <= 6; i++) {
                num += (Integer) cmdDataJson.get("type" + i);
            }
            cmdDataJson.put("account", account.getAccount());
            cmdDataJson.put("num", num);
            if (queryVo.getPowerDownId() == 0 && num == 0) {
                //未降权
                jsonArray.add(cmdDataJson);
            } else if (queryVo.getPowerDownId() == 1 && num != 0) {
                //已降权
                jsonArray.add(cmdDataJson);
            }


        }
        return jsonArray;
    }


}
