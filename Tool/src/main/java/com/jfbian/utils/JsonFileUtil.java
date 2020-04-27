package com.jfbian.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
* @ClassName:  JsonFileUtil
* @Description:解析.json文件工具类
* @author: bianjianfeng
* @date:   2020-04-27 20:00:27
*/
public class JsonFileUtil {

    /**
     * 读取文件数据为JSONObject
     * @return JSONObject
     * @throws IOException
     */
    public static JSONObject readJsonObjectData(String fileFullPath) {
        File file = new File(fileFullPath);
        JSONObject parseObject = null;
        try {
            parseObject = JSONObject.parseObject(FileUtils.readFileToString(file, Charset.forName("utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseObject;
    }

    /**
     * 读取文件数据为JSONArray
     * @return JSONArray
     * @throws IOException
     */
    public static JSONArray readJsonArrayData(String fileFullPath) {
        File file = new File(fileFullPath);
        JSONArray parseArray = null;
        try {
            parseArray = JSONArray.parseArray(FileUtils.readFileToString(file, Charset.forName("utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseArray;
    }
}
