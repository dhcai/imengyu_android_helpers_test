package com.imengyu.android_helpers;

import android.app.Activity;
import android.util.Size;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ScreenUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class ScreenModule extends WXModule {
    /**
     * 设置页面是否全屏
     * @param fullScreen 是否全屏
     */
    @Keep
    @UniJSMethod
    public void setFullScreen(boolean fullScreen) {
        if(fullScreen)
            ScreenUtils.setFullScreen((Activity) mWXSDKInstance.getContext());
        else
            ScreenUtils.setNonFullScreen((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 切换页面是否全屏状态
     */
    @Keep
    @UniJSMethod
    public void toggleFullScreen() {
        ScreenUtils.toggleFullScreen((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 获取页面是否全屏状态
     */
    @Keep
    @UniJSMethod
    public boolean isScreenLock() {
        return ScreenUtils.isScreenLock();
    }

    /**
     * 获取页面是否全屏状态
     */
    @Keep
    @UniJSMethod
    public boolean isLandscape() {
        return ScreenUtils.isLandscape();
    }

    /**
     * 判断是否横屏
     */
    @Keep
    @UniJSMethod
    public boolean isPortrait() {
        return ScreenUtils.isPortrait();
    }

    /**
     * 判断是否竖屏
     */
    @Keep
    @UniJSMethod
    public boolean isFullScreen() {
        return ScreenUtils.isFullScreen((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 获取屏幕旋转角度
     */
    @Keep
    @UniJSMethod
    public int getScreenRotation() {
        return ScreenUtils.getScreenRotation((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 设置屏幕为横屏
     */
    @Keep
    @UniJSMethod
    public void setLandscape() {
        ScreenUtils.setLandscape((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 设置屏幕为竖屏
     */
    @Keep
    @UniJSMethod
    public void setPortrait() {
        ScreenUtils.setPortrait((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 设置阻止休眠
     */
    @Keep
    @UniJSMethod
    public void setKeepScreenOn(boolean keepScreenOn) {
        ((Activity) mWXSDKInstance.getContext()).getWindow().getDecorView().setKeepScreenOn(keepScreenOn);
    }
    /**
     * 获取当前是否设置阻止休眠
     */
    @Keep
    @UniJSMethod
    public boolean getKeepScreenOn() {
        return ((Activity) mWXSDKInstance.getContext()).getWindow().getDecorView().getKeepScreenOn();
    }

    /**
     * 获取屏幕大小（宽高为竖屏时的大小）
     * @return 返回：
     * {
     *     width: number,
     *     height: number,
     * }
     */
    @Keep
    @UniJSMethod
    public JSONObject getScreenSize() {
        JSONObject o = new JSONObject();
        o.put("width", ScreenUtils.getScreenWidth());
        o.put("height", ScreenUtils.getScreenHeight());
        return o;
    }
}

