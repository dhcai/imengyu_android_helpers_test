package com.imengyu.android_helpers.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class StringUtils {
    /**
     * 点赞字符串格式化
     * @param n 数量
     * @return 字符串
     */
    public static String getNumberForUser(long n) {
        if(n < 9999)
            return Long.toString(n);
        else if(n == 10000)
            return "1w";
        else {
            return (n / 10000) + "w+";
        }
    }
    /**
     * 获取可读时间字符串
     * @param millisecond 毫秒
     * @return 字符串
     */
    public static String getTimeString(long millisecond) {
        long hour = millisecond / (1000 * 3600);
        long min = millisecond / (1000 * 60) - hour * 60;
        long sec = millisecond / 1000 - (hour * 60 + min) * 60;

        StringBuilder sb = new StringBuilder();
        if(hour > 0) {
            sb.append(hour);
            sb.append(':');
            if(min < 10)
                sb.append('0');
        }

        sb.append(min);
        sb.append(':');
        if(sec < 10)
            sb.append('0');
        sb.append(sec);

        return sb.toString();
    }



    /**
     * 格式化文件长度
     * @param fileSize 长度，字节
     * @return 格式化之后的字符串
     */
    public static String formatFileSize(long fileSize){
        DecimalFormat df = new DecimalFormat("#0.00");//表示小数点前至少一位,0也会显示,后保留两位

        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 读取txt全部内容为字符串
     * @param file 文件
     * @return 返回字符串，如果失败，则返回错误信息
     */
    public static String readFileToString(File file) {
        try {
            FileInputStream fin = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fin);
            BufferedReader buffReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String strTmp;
            while((strTmp = buffReader.readLine()) != null){
                sb.append(strTmp);
                sb.append('\n');
            }
            buffReader.close();
            return sb.toString();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }
}
