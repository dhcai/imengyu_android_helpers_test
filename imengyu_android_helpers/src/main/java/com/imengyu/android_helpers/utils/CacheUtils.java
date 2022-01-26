package com.imengyu.android_helpers.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.Objects;

public class CacheUtils {
    private static final String TAG = "CacheUtils";

    /**
     * 获取缓存中的路径
     * @param context 上下文
     * @param dirName 子目录路径
     * @param name 文件名
     * @return 文件
     */
    public static File getCachePath(Context context, String dirName, String name) {
        try {
            String dirPath = context.getCacheDir() + dirName;
            File dir = new File(dirPath);
            if(!dir.exists())
                if(!dir.mkdirs())
                    return null;

            File file = new File(dirPath + name + ".jpg");
            file.deleteOnExit();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App缓存大小
     * @param context 上下文
     * @return 文件大小，已自动转为字符串
     */
    public static String getAppCache(Context context) {
        long fileSize = 0;
        File cacheDir = context.getCacheDir();
        Log.d(TAG, "getAppCache: cacheDir大小: "+getDirSize(cacheDir));
        fileSize += getDirSize(cacheDir);

        String fileSizeStr = StringUtils.formatFileSize(fileSize);
        Log.d(TAG, "getAppCache: 总缓存大小: "+fileSizeStr);
        return fileSizeStr;
    }

    /**
     * 获取目录大小(字节为单位)
     * @param dir 目录
     * @return 大小(字节为单位)
     */
    private static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        if(files != null)
            for (File file : files) {
                if (file.isFile()) {
                    dirSize += file.length();//文件的长度就是文件的大小
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += getDirSize(file); // 递归调用继续统计
                }
            }
        return dirSize;
    }

    public interface OnClearAppCacheFinishListener {
        public void onClearAppCacheFinish();
    }

    /**
     * 清除App缓存
     * @param activity Activity
     * @return
     */
    public static void clearAppCache(final Activity activity, final OnClearAppCacheFinishListener listener) {
        new Thread(() -> {
            Log.d(TAG, "run: ");
            try {
                clearCacheFolder(activity.getCacheDir(), System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(() -> {
                if(listener != null)
                    listener.onClearAppCacheFinish();
            });
        }).start();
    }

    /**
     * 清除缓存目录
     * @param dir 目录
     * @param curTime 当前系统时间
     */
    private static int clearCacheFolder(File dir,long curTime){
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            Log.d(TAG, "clearCacheFolder: 清除目录: " + dir.getAbsolutePath());
            try {
                for (File child : Objects.requireNonNull(dir.listFiles())) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }
}
