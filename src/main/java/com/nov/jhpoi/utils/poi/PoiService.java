package com.nov.jhpoi.utils.poi;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/1/21 12:28 下午
 */
public interface PoiService {
    /**
     * 上传/导入
     * @param file
     * @return
     */
    ResponseEntity fileUpload(MultipartFile file);

    /**
     * 下载/导出
     * @param response
     */
    void downLoadExcel(HttpServletResponse response);
}
