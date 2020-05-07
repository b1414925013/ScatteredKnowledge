package Utils;

import java.io.*;

/**
 * @author 水瓶座鬼才（zhaoyijie）
 * @Description:
 * @date 2020/3/8 17:31
 */
public class LrcWriteUtils {
    /**
     * 将字符串写入文件
     * @param str 字符串
     * @param LrcSavepath 歌词文件保存路径
     * @throws Exception
     */
    public static void writeFile(String str, String LrcSavepath) {
        File file = new File(LrcSavepath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e.toString());
                return;
            }
        }
        BufferedWriter bwriter;
        try
        {
            bwriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            bwriter.write(str);
            bwriter.flush();
            bwriter.close();
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
}
