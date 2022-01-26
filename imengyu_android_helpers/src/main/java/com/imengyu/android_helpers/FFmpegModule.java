package com.imengyu.android_helpers;

import android.app.Activity;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.VibrateUtils;
import com.esay.ffmtool.FfmpegTool;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class FFmpegModule extends WXModule {

    @Keep
    @UniJSMethod
    public void cancel() {
        VibrateUtils.cancel();
    }
    @Keep
    @UniJSMethod
    public void vibrate(long ms) {
        FfmpegTool ffmpegTool = FfmpegTool.getInstance((Activity) mWXSDKInstance.getContext());
        ffmpegTool.
    }
}

