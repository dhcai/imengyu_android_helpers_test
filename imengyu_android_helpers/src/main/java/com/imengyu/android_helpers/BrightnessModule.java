package com.imengyu.android_helpers;

import android.app.Activity;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.BrightnessUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

/**
 * 亮度模块
 */
@Keep
public class BrightnessModule extends WXModule {

    /**
     * 获取屏幕亮度（0-255）
     */
    @Keep
    @UniJSMethod
    public int getBrightness() {
        return BrightnessUtils.getBrightness();
    }
    /**
     * 设置屏幕亮度（0-255）
     */
    @Keep
    @UniJSMethod
    public void setBrightness(int brightness) {
        BrightnessUtils.setBrightness(brightness);
    }
    /**
     * 设置屏幕是否开启自动亮度
     */
    @Keep
    @UniJSMethod
    public void setAutoBrightnessEnabled(boolean enabled) {
        BrightnessUtils.setAutoBrightnessEnabled(enabled);
    }
    /**
     * 获取屏幕是否开启自动亮度
     */
    @Keep
    @UniJSMethod
    public boolean isAutoBrightnessEnabled() {
        return BrightnessUtils.isAutoBrightnessEnabled();
    }

    /**
     * 获取当前窗口的屏幕亮度
     */
    @Keep
    @UniJSMethod
    public int getWindowBrightness() {
        return BrightnessUtils.getWindowBrightness(((Activity)mWXSDKInstance.getContext()).getWindow());
    }
    /**
     * 设置当前窗口的屏幕亮度（0-255）
     */
    @Keep
    @UniJSMethod
    public void setWindowBrightness(int brightness) {
        BrightnessUtils.setWindowBrightness(((Activity)mWXSDKInstance.getContext()).getWindow(), brightness);
    }

}

