package com.jfbian.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
* @ClassName:  TarUtil
* @Description: .tar包解压缩
* @author: bianjianfeng
* @date:   2020-04-27 22:18:12
*/
public class TarUtil {
    /**
     *
     * @Title: tarFiles
     * @Description: 将文件打为.tar形式
     * @param destFileName 目标文件
     * @param files  文件数组
     * @throws IOException
     * @return: void
     */
    public static void tarFiles(String destFileName, File... files) throws IOException {
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            org.apache.commons.io.FileUtils.forceDelete(destFile);
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            BufferedOutputStream bufferedWriter = new BufferedOutputStream(fileOutputStream);
            TarArchiveOutputStream tar = new TarArchiveOutputStream(bufferedWriter)) {
            tar.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            for (File file : files) {
                addTarArchiveEntryToTarArchiveOutputStream(file, tar, "");
            }
        }
    }

    private static void addTarArchiveEntryToTarArchiveOutputStream(File file, TarArchiveOutputStream tar, String prefix)
        throws IOException {
        TarArchiveEntry entry = new TarArchiveEntry(file, prefix + File.separator + file.getName());
        if (file.isFile()) {
            entry.setSize(file.length());
            tar.putArchiveEntry(entry);
            try (FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream input = new BufferedInputStream(fileInputStream);) {
                IOUtils.copy(input, tar);
            }
            tar.closeArchiveEntry();
        } else {
            tar.putArchiveEntry(entry);
            tar.closeArchiveEntry();
            prefix += File.separator + file.getName();
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    addTarArchiveEntryToTarArchiveOutputStream(f, tar, prefix);
                }
            }
        }
    }

    /**
    *
    * @Title: deCompressTARFile
    * @Description: 解压.tar文件
    * @param file
    * @param isDelSourceFile 是否删除源文件
    * @return: void
    */
    public static void deCompressTARFile(File file, Boolean isDelSourceFile) {
        int buffersize = 2048;
        String basePath = file.getParent() + File.separator;
        TarArchiveInputStream is = null;
        try {
            is = new TarArchiveInputStream(new FileInputStream(file));
            while (true) {
                TarArchiveEntry entry = is.getNextTarEntry();
                if (entry == null) {
                    break;
                }
                if (entry.isDirectory()) {
                    new File(basePath + entry.getName()).mkdirs();
                } else {
                    FileOutputStream os = null;
                    try {
                        File f = new File(basePath + entry.getName());
                        if (!f.getParentFile().exists()) {
                            f.getParentFile().mkdirs();
                        }
                        if (!f.exists()) {
                            f.createNewFile();
                        }
                        os = new FileOutputStream(f);
                        byte[] bs = new byte[buffersize];
                        int len = -1;
                        while ((len = is.read(bs)) != -1) {
                            os.write(bs, 0, len);
                        }
                        os.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        os.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                if (isDelSourceFile) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}