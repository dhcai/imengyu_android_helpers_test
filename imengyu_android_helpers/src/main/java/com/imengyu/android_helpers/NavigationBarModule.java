package com.imengyu.android_helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.BarUtils;
import com.imengyu.android_helpers.utils.NavigationBarUtils;
import com.kongzue.dialogx.dialogs.FullScreenDialog;
import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.io.File;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class NavigationBarModule extends WXModule {

    /**
     * 隐藏底部导航条
     */
    @Keep
    @UniJSMethod
    public void hideNavigationBar() {
        BarUtils.setNavBarVisibility((Activity) mWXSDKInstance.getContext(), false);
    }

    /**
     * 显示底部导航条
     */
    @Keep
    @UniJSMethod
    public void showNavigationBar() {
        BarUtils.setNavBarVisibility((Activity) mWXSDKInstance.getContext(), true);
    }

    /**
     * 设置底部导航条为透明色
     */
    @Keep
    @UniJSMethod
    public void setTransparentNavigationBar() {
        NavigationBarUtils.setTransparentNavigationBar((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 设置底部导航条为黑色
     */
    @Keep
    @UniJSMethod
    public void setBlackNavigationBar() {
        NavigationBarUtils.setBlackNavigationBar((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 重置底部导航条状态（重置为白色或者是默认状态）
     */
    @Keep
    @UniJSMethod
    public void resetBlackNavigationBar() {
        NavigationBarUtils.resetBlackNavigationBar((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 获取底部导航条的高度（像素）
     */
    @Keep
    @UniJSMethod
    public int getBlackNavigationBarHeight() {
        return NavigationBarUtils.getNavigationBarHeight(mWXSDKInstance.getContext());
    }

    /**
     * 获取底部导航条是否在亮色模式
     */
    @Keep
    @UniJSMethod
    public boolean getNavigationBarIsLightMode() {
        return BarUtils.isNavBarLightMode((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 获取底部导航条是否显示
     */
    @Keep
    @UniJSMethod
    public boolean getNavigationBarVisible() {
        return BarUtils.isNavBarVisible((Activity) mWXSDKInstance.getContext());
    }
}

