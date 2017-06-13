package org.yxdroid.droidtools.os;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: yxfang
 * Date: 2016-07-29
 * Time: 20:20
 * ------------- Description -------------
 * 文件处理工具类
 * ---------------------------------------
 */
public class FileUtil {
    /**
     * 写入字符串到sd卡目录中
     *
     * @param dir
     * @param fileName
     */
    public static void writeStringToFile(String dir, String fileName, String string) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        File saveFile = new File(dir, fileName);

        FileWriter out = null;
        try {
            out = new FileWriter(saveFile);
            out.write(string); //在此可以直接写入字符串，不用转化为字节数组
        } catch (IOException e) {

        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 輸入流转字符串
     *
     * @param is
     * @return
     */
    public static String inputStream2String(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return null;
    }
}
