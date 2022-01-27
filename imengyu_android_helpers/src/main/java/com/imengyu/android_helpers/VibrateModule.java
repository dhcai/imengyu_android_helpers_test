package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.VibrateUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class VibrateModule extends WXModule {

    @Keep
    @UniJSMethod
    public void cancel() {
        VibrateUtils.cancel();
    }
    @Keep
    @UniJSMethod
    public void vibrate(long ms) {
        VibrateUtils.vibrate(ms);
    }
}

