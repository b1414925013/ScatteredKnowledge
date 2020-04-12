package com.jfbian.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @ClassName:  pathUtil
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2020-04-12 08:57:14
 */
public class pathUtil {
    /**
     *
     * @Title: getClassPath
     * @Description: 获取项目classPath目录
     * @return
     * @return: String
     * @throws
     */
    public static String getClassPath() {
        URI uri;
        try {
            uri = pathUtil.class.getResource("/").toURI();
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
     * @return
     * @return: String
     * @throws
     */
    public static String getRootPath() {
        String a = new File(pathUtil.getClassPath()).getParent() + File.separator;
        return a;
    }
}
