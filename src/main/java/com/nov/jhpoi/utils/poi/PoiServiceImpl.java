//package com.nov.jhpoi.utils.poi;
//
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
///**
// * Created by IntelliJ IDEA.
// *
// * @Author: november
// * Date: 2021/1/21 12:29 下午
// */
//@Service
//public class PoiServiceImpl implements PoiService {
//
//    @Autowired
//    private PoiDao poiDao;
//
//    @Override
//    public ResponseEntity fileUpload(MultipartFile file) {
//        if (!Utils.checkExtension(file)) {
//            return new ResponseEntity("请求文件类型错误:后缀名错误", HttpStatus.BAD_REQUEST);
//        }
//        try {
//            if (Utils.isOfficeFile(file)) {
//                //正确的文件类型 自动判断2003或者2007
//                Workbook workbook = Utils.getWorkbookAuto(file);
//                //默认只有一个sheet
//                Sheet sheet = workbook.getSheetAt(0);
//                //获得sheet有多少行
//                int rows = sheet.getPhysicalNumberOfRows();
//                //读第一个sheet
//                for (int i = 1; i < rows; i++) {
//                    Row row = sheet.getRow(i);
//                    for (int j = 0; j < row.getLastCellNum(); j++) {
//                        Cell cell = row.getCell(j);
//                        if (cell != null) {
//                            System.out.println(cell.toString());
//                        }else {
//                            System.out.println(" ");
//                        }
//                    }
//                }
//            } else {
//                return new ResponseEntity("请求文件类型错误:文件类型错误", HttpStatus.BAD_REQUEST);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @Override
//    public void downLoadExcel(HttpServletResponse response) {
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("信息表");
//
//        List<Teacher> classmateList = poiDao.getTeachers();
//
//        //设置要导出的文件的名字
//        String fileName = "userinf" + ".xls";
//        //新增数据行，并且设置单元格数据
//
//        int rowNum = 1;
//
//        String[] headers = {"学号", "姓名", "身份类型", "登录密码"};
//        //headers表示excel表中第一行的表头
//
//        HSSFRow row = sheet.createRow(0);
//        //在excel表中添加表头
//
//        for (int i = 0; i < headers.length; i++) {
//            HSSFCell cell = row.createCell(i);
//            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//            cell.setCellValue(text);
//        }
//
//        //在表中存放查询到的数据放入对应的列
//        for (Teacher teacher : classmateList) {
//            HSSFRow row1 = sheet.createRow(rowNum);
//            row1.createCell(0).setCellValue(teacher.getNumber());
//            row1.createCell(1).setCellValue(teacher.getName());
//            row1.createCell(2).setCellValue(teacher.getIdCard());
//            row1.createCell(3).setCellValue(teacher.getPassword());
//            rowNum++;
//        }
//
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//        try {
//            response.flushBuffer();
//            workbook.write(response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
