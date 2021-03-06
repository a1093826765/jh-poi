package com.nov.jhpoi.utils.poi;

import com.nov.jhpoi.bean.PoiBean;
import com.nov.jhpoi.sql.model.*;
import com.nov.jhpoi.sql.service.AccountService;
import com.nov.jhpoi.sql.service.ShopNameService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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

    @Autowired
    private ShopNameService shopNameService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @Override
    public ResultUtils fileUpload(MultipartFile file) {
        int successNum=0;
        if (!PoiUtils.checkExtension(file)) {
            return ResultUtils.fail(5000,"请求文件类型错误:后缀名错误"+"-->成功:"+successNum+"行数据");
        }
        try {
            if (PoiUtils.isOfficeFile(file)) {
                //正确的文件类型 自动判断2003或者2007
                Workbook workbook = PoiUtils.getWorkbookAuto(file);
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
                    System.out.println("导入"+poiBean.toString());
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
        ShopNameKey shopNameKey=new ShopNameKey();
        ShopName shopName;
        //在表中存放查询到的数据放入对应的列
        for (Account account : accountList) {
            shopExampleCriteria.andIdEqualTo(account.getId());
            shopList = shopService.getShopByExample(shopExample);
            for(Shop shop:shopList){
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(rowNum);
                row1.createCell(1).setCellValue(account.getAccount());
                shopNameKey.setShopnameid(shop.getShopnameid());
                shopName = shopNameService.getShopNameByKey(shopNameKey);
                row1.createCell(2).setCellValue(shopName.getShopname());
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
    ShopName shopNameSql=new ShopName();
    ShopNameExample shopNameExample=new ShopNameExample();


    /**
     * 将文档每行数据写入数据库
     * @param poiBean
     */
    public void setSqlData(PoiBean poiBean){
        if(poiBean.getShopWeChatNum()==null || poiBean.getShopWeChatNum().equals("")){
            weChatSql.setWechatid(null);
        }else {
            pdWeChat(poiBean.getShopWeChatNum());
        }
        pdAccount(poiBean.getAccountNum(),poiBean.getWeChatNum());
        pdShopName(poiBean.getShopName());
        pdShop(poiBean.getShopMoney(),poiBean.getTime());
    }

    /**
     * 判断店铺是否存在，不存在则写入，存在则忽略
     * @param shopMoney
     * @param shopTime
     */
    public void pdShop(String shopMoney,Date shopTime){
        ShopExample.Criteria shopExampleCriteria = shopExample.createCriteria();
        shopExampleCriteria.andShopmoneyEqualTo(shopMoney);
        shopExampleCriteria.andShoptimeEqualTo(shopTime);
        shopExampleCriteria.andShopnameidEqualTo(shopNameSql.getShopnameid());
        List<Shop> shopList = shopService.getShopByExample(shopExample);
        if(shopList.size()<=0){
            shopSql.setShopmoney(shopMoney);
            shopSql.setShoptime(shopTime);
            shopSql.setShopnameid(shopNameSql.getShopnameid());
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
        System.out.println(account+"账号查询结果："+accountList.size());
        if(accountList.size()<=0){
            accountSql.setId(UUID.randomUUID().toString());

            TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
            TimeZone.setDefault(timeZone);
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

    /**
     * 判断店铺名是否存在，不存在则写入，存在则忽略
     * @param shopName
     */
    public void pdShopName(String shopName){
        ShopNameExample.Criteria shopNameExampleCriteria = shopNameExample.createCriteria();
        shopNameExampleCriteria.andShopnameEqualTo(shopName);
        List<ShopName> shopNameList = shopNameService.getShopNameByExample(shopNameExample);
        if(shopNameList.size()<=0){
            shopNameSql.setShopnameid(UUID.randomUUID().toString());
            shopNameSql.setShopname(shopName);
            shopNameService.save(shopNameSql);
        }else{
            shopNameSql=shopNameList.get(0);
        }
        shopNameExample.clear();
    }

}

