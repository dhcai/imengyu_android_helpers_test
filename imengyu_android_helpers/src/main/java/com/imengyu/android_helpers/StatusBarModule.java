package com.imengyu.android_helpers;

import android.app.Activity;
import android.graphics.Color;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.BarUtils;
import com.imengyu.android_helpers.utils.StatusBarUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class StatusBarModule extends WXModule {

    /**
     * 设置状态栏为亮色模式
     */
    @Keep
    @UniJSMethod
    public void setLightMode() {
        StatusBarUtils.setLightMode((Activity) mWXSDKInstance.getContext());
    }
    /**
     * 设置状态栏为暗色模式
     */
    @Keep
    @UniJSMethod
    public void setDarkMode() {
        StatusBarUtils.setDarkMode((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 设置状态栏颜色
     * @param color 颜色字符串 支持 #fff 这种格式
     */
    @Keep
    @UniJSMethod
    public void setStatusBarColor(String color) {
        StatusBarUtils.setStatusBarColor((Activity) mWXSDKInstance.getContext(), Color.parseColor(color));
    }

    /**
     * 获取状态栏的高度（像素）
     */
    @Keep
    @UniJSMethod
    public int getStatusBarBarHeight() {
        return StatusBarUtils.getStatusBarHeight(mWXSDKInstance.getContext());
    }

    /**
     * 获取状态栏是否是亮色模式
     */
    @Keep
    @UniJSMethod
    public boolean getStatusBarIsLightMode() {
        return BarUtils.isStatusBarLightMode((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 获取状态栏是否显示
     */
    @Keep
    @UniJSMethod
    public boolean getStatusBarVisible() {
        return BarUtils.isStatusBarVisible((Activity) mWXSDKInstance.getContext());
    }
}

