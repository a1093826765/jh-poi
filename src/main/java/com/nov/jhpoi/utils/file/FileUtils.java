package com.nov.jhpoi.utils.file;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: november
 * Date: 2021/2/5 2:37 下午
 */
public class FileUtils {
    /**
     * 使用BufferedWriter类写文本文件
     */
    public static void writeMethod(String fileNamePath, String conTxt) {
        try {
            System.out.println("文件写入："+conTxt);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(fileNamePath), StandardCharsets.UTF_8));
            out.write(new String(conTxt.getBytes(StandardCharsets.UTF_8)));
            out.newLine();  //注意\n不一定在各种计算机上都能产生换行的效果
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 使用BufferedReader类读文本文件
     */
    public static String readMethod(String fileNamePath) {
        String line;
        StringBuilder str = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileNamePath), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
                str.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
