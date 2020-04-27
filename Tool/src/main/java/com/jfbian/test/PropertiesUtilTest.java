package com.jfbian.test;

import com.jfbian.utils.PropertiesUtil;

/**
 * @ClassName:  PropertiesUtilTest
 * @Description:
 * @author: bianjianfeng
 * @date:   2020-04-27 23:33:51
 */
public class PropertiesUtilTest {
    public static void main(String[] args) {
        // sysConfig.properties(配置文件)
        PropertiesUtil p = new PropertiesUtil("sysConfig.properties");
        System.out.println(p.getProperties().get("db.url"));
        System.out.println(p.readProperty("db.url"));
        PropertiesUtil q = new PropertiesUtil(
            "D:\\develop\\eclipse-workspace\\A\\TestTool\\src\\main\\resources\\sysConfig.properties");
        q.writeProperty("myUtils", "wang");
        System.exit(0);
    }
}
