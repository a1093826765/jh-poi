package com.nov.jhpoi.utils.poi;

import cn.hutool.core.date.DateUtil;
import com.nov.jhpoi.bean.PoiBean;
import com.nov.jhpoi.sql.model.*;
import com.nov.jhpoi.sql.service.AccountService;
import com.nov.jhpoi.sql.service.ShopService;
import com.nov.jhpoi.sql.service.WeChatService;
import com.nov.jhpoi.utils.pojo.ResultUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 文件上传
     * @param file
     * @return
     */
    @Override
    public ResultUtils fileUpload(MultipartFile file) {
        int successNum=0;
        if (!Utils.checkExtension(file)) {
            return ResultUtils.fail(5000,"请求文件类型错误:后缀名错误"+"-->成功:"+successNum+"行数据");
        }
        try {
            if (Utils.isOfficeFile(file)) {
                //正确的文件类型 自动判断2003或者2007
                Workbook workbook = Utils.getWorkbookAuto(file);
                //默认只有一个sheet
                Sheet sheet = workbook.getSheetAt(0);
                //获得sheet有多少行
                int rows = sheet.getPhysicalNumberOfRows();

                PoiBean poiBean=new PoiBean();

                //读第一个sheet
                for (int i = 2; i < rows; i++) {
                    Row row = sheet.getRow(i);
                    Cell cell=row.getCell(0);
                    cell.setCellType(CellType.STRING);
                    if("".equals(cell.getStringCellValue())){
                        //序号为空，停止读取
                        break;
                    }
                    for (int j = 0; j < 7; j++) {
                        cell = row.getCell(j);
                        if (cell != null) {
                            cell.setCellType(CellType.STRING);
                            switch (j){
                                case 1:
                                    poiBean.setAccountNum(cell.getStringCellValue());
                                    break;
                                case 2:
                                    poiBean.setWeChatNum(cell.getStringCellValue());
                                    break;
                                case 3:
                                    Date date =HSSFDateUtil.getJavaDate(Double.parseDouble(cell.getStringCellValue()));
                                    poiBean.setTime(date);
                                    break;
                                case 4:
                                    poiBean.setShopName(cell.getStringCellValue());
                                    break;
                                case 5:
                                    poiBean.setShopMoney(cell.getStringCellValue());
                                    break;
                                case 6:
                                    poiBean.setShopWeChatNum(cell.getStringCellValue());
                                    break;
                            }

                        }else {
                            return ResultUtils.fail(5000,"文件内部数据存在空值"+"-->成功:"+successNum+"行数据");
                        }
                    }
                    setSqlData(poiBean);
                    successNum++;

                }
            } else {
                return ResultUtils.fail(5000,"请求文件类型错误:文件类型错误"+"-->成功:"+successNum+"行数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtils.success("成功:"+successNum+"行数据");
    }

    /**
     * 文件下载
     * @param response
     */
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
    AccountExample accountExample=new AccountExample();
    Shop shopSql=new Shop();
    ShopExample shopExample=new ShopExample();
    WeChatExample weChatExample=new WeChatExample();
    WeChat weChatSql=new WeChat();


    /**
     * 将文档每行数据写入数据库
     * @param poiBean
     */
    public void setSqlData(PoiBean poiBean){
        pdWeChat(poiBean.getShopWeChatNum());
        pdAccount(poiBean.getAccountNum(),poiBean.getWeChatNum());
        pdShop(poiBean.getShopMoney(),poiBean.getTime(),poiBean.getShopName());
    }

    /**
     * 判断店铺是否存在，不存在则写入，存在则忽略
     * @param shopMoney
     * @param shopTime
     * @param shopName
     */
    public void pdShop(String shopMoney,Date shopTime,String shopName){
        ShopExample.Criteria shopExampleCriteria = shopExample.createCriteria();
        shopExampleCriteria.andShopmoneyEqualTo(shopMoney);
        shopExampleCriteria.andShoptimeEqualTo(shopTime);
        shopExampleCriteria.andShopnameEqualTo(shopName);
        List<Shop> shopList = shopService.getShopByExample(shopExample);
        if(shopList.size()<=0){
            shopSql.setShopmoney(shopMoney);
            shopSql.setShoptime(shopTime);
            shopSql.setShopname(shopName);
            shopSql.setId(accountSql.getId());
            shopSql.setShopid(UUID.randomUUID().toString());
            shopService.save(shopSql);
        }else {
            shopSql=shopList.get(0);
        }
        shopExample.clear();
    }

    /**
     * 判断本地微信号是否存在，不存在则写入，存在则忽略
     * @param weChatNum
     */
    public void pdWeChat(String weChatNum){

        WeChatExample.Criteria weChatExampleCriteria = weChatExample.createCriteria();
        weChatExampleCriteria.andWechatnumEqualTo(weChatNum);
        List<WeChat> weChatList = weChatService.getWeChatByExample(weChatExample);
        if(weChatList.size()<=0){
            weChatSql.setWechatid(UUID.randomUUID().toString());
            weChatSql.setWechatnum(weChatNum);
            weChatService.save(weChatSql);
        }else{
            weChatSql=weChatList.get(0);
        }
        weChatExample.clear();
    }

    /**
     * 判断旺旺是否存在，不存在则写入，存在则忽略
     * @param account
     * @param weChatNum
     */
    public void pdAccount(String account,String weChatNum){
        AccountExample.Criteria accountExampleCriteria = accountExample.createCriteria();
        accountExampleCriteria.andAccountEqualTo(account);
        List<Account> accountList = accountService.getAccountByExample(accountExample);
        if(accountList.size()<=0){
            accountSql.setId(UUID.randomUUID().toString());
            accountSql.setTime(new Date());
            accountSql.setSex("");
            accountSql.setAccount(account);

            accountSql.setWechat(weChatNum);
            accountSql.setWechatid(weChatSql.getWechatid());
            accountService.save(accountSql);
        }else{
            accountSql=accountList.get(0);
        }
        accountExample.clear();
    }
}

