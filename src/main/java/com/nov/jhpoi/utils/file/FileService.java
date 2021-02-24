package com.nov.jhpoi.utils.file;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/2/5 2:30 下午
 */
public interface FileService {
    /**
     * 写入生成文件
     * @param fileName
     * @param cmdJson
     */
    void updateTxtFile(String fileName, JSONObject cmdJson,String path);

    /**
     * 根据id查询文件
     * @param id
     * @return
     */
    String queryTxtFilePath(String id,String path);

}
