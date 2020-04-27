package com.jfbian.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
* @ClassName:  GZipUtil
* @Description: .gz文件的解压缩
* @author: bianjianfeng
* @date:   2020-04-24 23:17:08
*/
public class GZipUtil {
    /**
     *
     * @Title: compressGZip
     * @Description: 压缩文件为.gz格式
     * @param inFileName
     * @return: void
     */
    public static void compressGZip(String inFileName) {
        try {
            String outFileName = inFileName + ".gz";
            GZIPOutputStream out = null;
            try {
                out = new GZIPOutputStream(new FileOutputStream(outFileName));
            } catch (FileNotFoundException e) {
                System.err.println("Could not create file: " + outFileName);
                System.exit(1);
            }
            FileInputStream in = null;
            try {
                in = new FileInputStream(inFileName);
            } catch (FileNotFoundException e) {
                System.err.println("File not found. " + inFileName);
                System.exit(1);
            }
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.finish();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @Title: unGZip
     * @Description: 解压.gz格式文件
     * @param inFileName
     * @return: void
     */
    public static void deCompressGZip(String inFileName) {
        try {
            if (!getExtension(inFileName).equalsIgnoreCase("gz")) {
                System.err.println("File name must have extension of \".gz\"");
                System.exit(1);
            }
            GZIPInputStream in = null;
            try {
                in = new GZIPInputStream(new FileInputStream(inFileName));
            } catch (FileNotFoundException e) {
                System.err.println("File not found. " + inFileName);
                System.exit(1);
            }
            String outFileName = getFileName(inFileName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                System.err.println("Could not write to file. " + outFileName);
                System.exit(1);
            }
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @Title: getExtension
     * @Description: 获取文件后缀名
     * @param fileName
     * @return: String
     */
    private static String getExtension(String fileName) {
        String ext = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            ext = fileName.substring(i + 1);
        }
        return ext;
    }

    /**
    *
    * @Title: getFileName
    * @Description: 获取文件名
    * @param f
    * @return: String
    */
    private static String getFileName(String f) {
        String fname = "";
        int i = f.lastIndexOf('.');
        if (i > 0 && i < f.length() - 1) {
            fname = f.substring(0, i);
        }
        return fname;
    }
}