package com.nov.jhpoi.utils.poi;

import cn.hutool.core.date.DateUtil;
import com.nov.jhpoi.sql.model.*;
import com.nov.jhpoi.sql.service.AccountService;
import com.nov.jhpoi.sql.service.ShopService;
import com.nov.jhpoi.sql.service.WeChatService;
import com.nov.jhpoi.utils.pojo.ResultUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/21 12:29 下午
 */
@Service
public class PoiServiceImpl implements PoiService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WeChatService weChatService;

    @Override
    public ResultUtils fileUpload(MultipartFile file) {
        if (!Utils.checkExtension(file)) {
            return ResultUtils.fail(5000,"请求文件类型错误:后缀名错误");
        }
        try {
            if (Utils.isOfficeFile(file)) {
                //正确的文件类型 自动判断2003或者2007
                Workbook workbook = Utils.getWorkbookAuto(file);
                //默认只有一个sheet
                Sheet sheet = workbook.getSheetAt(0);
                //获得sheet有多少行
                int rows = sheet.getPhysicalNumberOfRows();
                //用于验证文件数据是否足够
                int pd;
                //读第一个sheet
                for (int i = 2; i < rows; i++) {
                    Row row = sheet.getRow(i);
                   pd=0;
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        pd++;
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            //非空判断完成
                        }else {
                            return ResultUtils.fail(5000,"文件内部数据空值");
                        }
                    }
                    if(pd<=0 || pd>=7){
                        return ResultUtils.fail(5000,"文件内部数据有多余或空值");
                    }
                    setSqlData(row.getCell(1).toString(),row.getCell(2).toString(), DateUtil.parse(row.getCell(3).toString()),row.getCell(4).toString(),row.getCell(5).toString(),row.getCell(6).toString());

                }
            } else {
                return ResultUtils.fail(5000,"请求文件类型错误:文件类型错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtils.success();
    }

    @Override
    public void downLoadExcel(HttpServletResponse response) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        List<Account> accountList = accountService.getAccountByExample(new AccountExample());

        //设置要导出的文件的名字
        String fileName = "捉妖镜信息导出表" + ".xls";
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = {"序号", "旺旺号","微信号","时间", "店铺", "金额","本地微信号"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        List<Shop> shopList;
        ShopExample shopExample=new ShopExample();
        ShopExample.Criteria shopExampleCriteria = shopExample.createCriteria();
        //在表中存放查询到的数据放入对应的列
        for (Account account : accountList) {
            shopExampleCriteria.andIdEqualTo(account.getId());
            shopList = shopService.getShopByExample(shopExample);
            for(Shop shop:shopList){
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(rowNum);
                row1.createCell(1).setCellValue(account.getAccount());
                row1.createCell(2).setCellValue(shop.getShopname());
                row1.createCell(3).setCellValue(shop.getShoptime());
                rowNum++;
            }
            shopExample.clear();
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Account accountSql=new Account();
    String id;
    Shop shop=new Shop();
    public void setSqlData(String account, String weChatNum, Date shopTime, String shopName, String shopMoney, String shopWeChatNum){
        WeChat weChatSql=pdWeChat(shopWeChatNum);
        id=UUID.randomUUID().toString();
        accountSql.setId(id);
        accountSql.setSex("");
        accountSql.setAccount(account);
        accountSql.setTime(DateUtil.date(System.currentTimeMillis()));
        accountSql.setWechat(weChatNum);
        accountSql.setWechatid(weChatSql.getWechatid());

        shop.setShopmoney(shopMoney);
        shop.setShoptime(shopTime);
        shop.setShopname(shopName);
        shop.setId(id);
        shop.setShopid(UUID.randomUUID().toString());

        accountService.save(accountSql);
        shopService.save(shop);
    }

    WeChatExample weChatExample=new WeChatExample();
    WeChat weChat=new WeChat();
    public WeChat pdWeChat(String weChatNum){

        WeChatExample.Criteria weChatExampleCriteria = weChatExample.createCriteria();
        weChatExampleCriteria.andWechatnumEqualTo(weChatNum);
        List<WeChat> weChatList = weChatService.getWeChatByExample(weChatExample);
        if(weChatList.size()<=0){
            weChat.setWechatid(UUID.randomUUID().toString());
            weChat.setWechatnum(weChatNum);
            weChatService.save(weChat);
            return weChat;
        }else{
            return weChatList.get(0);
        }
    }
}

