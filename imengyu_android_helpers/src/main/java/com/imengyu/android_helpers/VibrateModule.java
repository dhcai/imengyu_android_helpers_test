package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.VibrateUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

/**
 * 震动模块
 */
@Keep
public class VibrateModule extends WXModule {

    /**
     * 停止震动
     */
    @Keep
    @UniJSMethod
    public void cancel() {
        VibrateUtils.cancel();
    }

    /**
     * 开始震动
     * @param ms 时长毫秒
     */
    @Keep
    @UniJSMethod
    public void vibrate(long ms) {
        VibrateUtils.vibrate(ms);
    }
}

