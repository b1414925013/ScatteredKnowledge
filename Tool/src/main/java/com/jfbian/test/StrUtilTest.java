package com.jfbian.test;

import java.text.MessageFormat;

import com.alibaba.fastjson.JSONObject;
import com.jfbian.utils.JsonUtil;
import com.jfbian.utils.StrUtil;

/**
 * @ClassName:  StrUtilTest
 * @Description:
 * @author: bianjianfeng
 * @date:   2020-04-27 23:28:33
 */
public class StrUtilTest {
    public static void main(String... args) {
        //{}被转义，不会被替换
        System.out.println(StrUtil.parse("{", "}", "我的名字是\\{},结果是{}，可信度是%{}", "雷锋", true, 100));
        System.out.println(StrUtil.parse0("我的名字是${},结果是${}，可信度是%${}", "雷锋", true, 100));
        System.out.println(StrUtil.parse1("我的名字是{},结果是{}，可信度是%{}", "雷锋", true, 100));
        System.out.println(MessageFormat.format("我是{0},我来自{1},今年{2}岁", "中国人", "北京", "22"));
        System.out.println(StrUtil.parse0("{\"id\":\"${}\",\"name\":\"${}\"}", "编号", "姓名"));
        System.out.println(StrUtil.parse0("{'id':'${}','name':'${}'}", "编号", "姓名"));

        String parse0 = StrUtil.parse0("{'id':'${}','name':'${}'}", "编号", "姓名");
        JSONObject jsonObject = JsonUtil.toJSONObject(parse0);
        System.out.println(jsonObject);
    }
}
