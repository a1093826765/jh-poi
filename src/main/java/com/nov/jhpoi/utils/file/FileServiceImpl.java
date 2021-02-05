package com.nov.jhpoi.utils.file;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import static com.nov.jhpoi.config.Address.FILE_PATH;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/2/5 2:34 下午
 */
@Service
public class FileServiceImpl implements FileService{
    @Override
    public void updateTxtFile(String fileName, JSONObject cmdJson) {
        FileUtils.writeMethod(FILE_PATH+fileName,cmdJson.toJSONString());
    }

    @Override
    public String queryTxtFilePath(String id) {
        return FileUtils.readMethod(FILE_PATH+id+".txt");
    }

}
