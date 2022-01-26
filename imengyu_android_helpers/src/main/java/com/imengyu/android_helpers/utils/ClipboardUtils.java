package com.imengyu.android_helpers.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;

import java.io.File;

public class ClipboardUtils {
    /**
     * 复制内容到剪贴板
     */
    public static void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
    /**
     * 复制文件到剪贴板
     */
    public static void copyFileToClipboard(String path, Context context) {
        File file = new File(path);
        if(file.exists()) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newUri(context.getContentResolver(), "URL", Uri.fromFile(file));
            cm.setPrimaryClip(mClipData);
        }
    }
}
