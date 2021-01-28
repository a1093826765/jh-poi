package com.nov.jhpoi.controller;

import com.nov.jhpoi.utils.poi.PoiService;
import com.nov.jhpoi.utils.pojo.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/21 12:36 下午
 */
@RestController
public class PoiController {

    @Autowired
    private PoiService poiService;

    /**
     * 上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        return poiService.fileUpload(file);
    }

    /**
     * 下载
     * @param response
     */
    @RequestMapping(value = "/downLoadExcel", method = RequestMethod.GET)
    public void downLoadExcel(HttpServletResponse response){
        poiService.downLoadExcel(response);
    }
}
