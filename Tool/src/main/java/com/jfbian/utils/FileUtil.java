package com.jfbian.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    /**
     * 获取一个文件夹下的所有文件全路径
     * @param path
     * @param listFileName
     */
    public static void getAllFileName(String path, List<String> listFileName) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        if (names != null) {
            String[] completNames = new String[names.length];
            for (int i = 0; i < names.length; i++) {
                completNames[i] = path + names[i];
            }
            listFileName.addAll(Arrays.asList(completNames));
        }
        for (File a : files) {
            if (a.isDirectory()) {
                getAllFileName(a.getAbsolutePath() + File.separator, listFileName);
            }
        }
    }
}