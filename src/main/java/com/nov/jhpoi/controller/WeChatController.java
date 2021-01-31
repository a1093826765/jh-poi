package com.nov.jhpoi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nov.jhpoi.sql.model.WeChat;
import com.nov.jhpoi.sql.model.WeChatExample;
import com.nov.jhpoi.sql.service.WeChatService;
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
 * Date: 2021/1/31 7:09 下午
 */
@RestController
@RequestMapping(value = "/api/weChat",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
public class WeChatController {
    @Autowired
    WeChatService weChatService;

    /**
     * 查询本地微信号
     * @param
     * @return
     */
    @PostMapping("/query")
    public ResultUtils insertData(){
        List<WeChat> weChatList = weChatService.getWeChatByExample(new WeChatExample());
        JSONArray jsonArray=new JSONArray();
        for(WeChat weChat:weChatList){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("weChatId",weChat.getWechatid());
            jsonObject.put("weChatNum",weChat.getWechatnum());
            jsonArray.add(jsonObject);
        }
        return ResultUtils.success(jsonArray);
    }
}
