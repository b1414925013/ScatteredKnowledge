package com.jfbian.utils;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @ClassName:  JsonUtil
 * @Description:json相关方法
 * @author: bianjianfeng
 * @date:   2020-04-24 22:22:38
 */
public class JsonUtil {

    /**
     *
     * @Title: toJSONArray
     * @Description: 将数组类型字符串转换为JSON对象数组
     * @param arrayStr
     * @return: JSONArray
     */
    public static JSONArray toJSONArray(String arrayStr) {
        return JSONArray.parseArray(arrayStr);
    }

    /**
    *
    * @Title: toJSONObject
    * @Description: 将对象类型字符串转换为JSON对象
    * @param objectStr
    * @return: JSONObject
    */
    public static JSONObject toJSONObject(String objectStr) {
        return JSONObject.parseObject(objectStr);
    }

    /**
     *
     * @Title: jsonStr2List
     * @Description: json数组类型字符串转换为list集合
     * @param jsonStr
     * @return: List<String>
     */
    public static List<String> jsonStr2List(String jsonStr) {
        List<String> parseArray = JSONArray.parseArray(jsonStr, String.class);
        return parseArray;
    }

    /**
     *
     * @Title: jsonStr2Map
     * @Description: json对象类型字符串转换为map集合
     * @param jsonStr  json对象类型字符串
     * @return: Map<String,Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonStr2Map(String jsonStr) {
        return JSONObject.parseObject(jsonStr, Map.class);
    }
}
