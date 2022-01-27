package com.imengyu.android_helpers;

import android.app.Activity;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.BrightnessUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class BrightnessModule extends WXModule {

    @Keep
    @UniJSMethod
    public int getBrightness() {
        return BrightnessUtils.getBrightness();
    }
    @Keep
    @UniJSMethod
    public void setBrightness(int brightness) {
        BrightnessUtils.setBrightness(brightness);
    }
    @Keep
    @UniJSMethod
    public void setAutoBrightnessEnabled(boolean enabled) {
        BrightnessUtils.setAutoBrightnessEnabled(enabled);
    }
    @Keep
    @UniJSMethod
    public boolean isAutoBrightnessEnabled() {
        return BrightnessUtils.isAutoBrightnessEnabled();
    }

    @Keep
    @UniJSMethod
    public int getWindowBrightness() {
        return BrightnessUtils.getWindowBrightness(((Activity)mWXSDKInstance.getContext()).getWindow());
    }
    @Keep
    @UniJSMethod
    public void setWindowBrightness(int brightness) {
        BrightnessUtils.setWindowBrightness(((Activity)mWXSDKInstance.getContext()).getWindow(), brightness);
    }

}

