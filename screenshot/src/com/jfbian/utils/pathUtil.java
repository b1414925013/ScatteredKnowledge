package com.jfbian.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @ClassName:  pathUtil
 * @Description:路径工具类
 * @author: bianjianfeng
 * @date:   2020-04-12 08:57:14
 */
public class PathUtil {
    /**
     *
     * @Title: getClassPath
     * @Description: 获取项目classPath目录
     * @return
     * @return: String
     */
    public static String getClassPath() {
        URI uri;
        try {
            uri = PathUtil.class.getResource("/").toURI();
            File file = new File(uri);
            return file.getAbsolutePath() + File.separator;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    *
    * @Title: getRootPath
    * @Description: 获取项目根目录
    * @return: String
    */
    public static String getRootPath() {
        return new File(PathUtil.getClassPath()).getParent() + File.separator;
    }

    /**
     *
     * @Title: getTemporaryFolder
     * @Description: 获取临时文件夹
     * @return: String
     */
    public static String getTemporaryFolder() {
        return System.getProperty("java.io.tmpdir");
    }
}
