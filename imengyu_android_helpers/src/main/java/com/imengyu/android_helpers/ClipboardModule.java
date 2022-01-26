package com.imengyu.android_helpers;

import androidx.annotation.Keep;
import com.imengyu.android_helpers.utils.ClipboardUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class ClipboardModule extends WXModule {
    /**
     * 复制文字到剪贴板中
     * @param content 文字
     */
    @Keep
    @UniJSMethod
    public void copyContentToClipboard(String content) {
        ClipboardUtils.copyContentToClipboard(content, mWXSDKInstance.getContext());
    }
    /**
     * 复制文件到剪贴板中
     * @param path 文件路径，必须是绝对路径
     */
    @Keep
    @UniJSMethod
    public void copyFileToClipboard(String path) {
        ClipboardUtils.copyFileToClipboard(path, mWXSDKInstance.getContext());
    }
}

