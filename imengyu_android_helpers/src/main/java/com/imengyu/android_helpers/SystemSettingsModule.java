package com.imengyu.android_helpers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.Keep;

import com.imengyu.android_helpers.utils.MobileInfoUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

/**
 * 系统设置页面跳转工具类
 */
@Keep
public class SystemSettingsModule extends WXModule {

    /**
     * 跳转到系统的当前App“自启动设置”页面，通常可以用于引导用户打开本APP的自启动
     */
    @Keep
    @UniJSMethod
    public void goSystemStartSettings() {
        MobileInfoUtils.jumpStartInterface(mWXSDKInstance.getContext());
    }

    /**
     * 跳转到系统的当前App“通知设置”页面，通常可以用于引导用户打开本APP的通知权限
     */
    @Keep
    @UniJSMethod
    public void goSystemNotificationSetting() {
        MobileInfoUtils.gotoNotificationSetting((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 跳转到系统的当前App的权限页面，通常可以用于引导用户打开本APP的权限
     */
    @Keep
    @UniJSMethod
    public void goSystemPermissionSetting() {
        MobileInfoUtils.gotoPermissionSetting((Activity) mWXSDKInstance.getContext());
    }

    /**
     * 跳转到系统的“辅助功能设置”页面，通常可以用于引导用户打开本APP的辅助功能权限
     */
    @Keep
    @UniJSMethod
    public void goSystemAccessibilitySetting() {
        Intent intent =  new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“飞行模式设置”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemAirplaneModeSetting() {
        Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“Wifi设置”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemWifiSetting() {
        Intent intent =  new Intent(Settings.ACTION_WIFI_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“APN设置”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemApnSetting() {
        Intent intent =  new Intent(Settings.ACTION_APN_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 转到指定App的系统设置页面
     * @param packageName 包名
     */
    @Keep
    @UniJSMethod
    public void goSystemAppDetailsSetting(String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 转到本App的系统设置页面
     */
    @Keep
    @UniJSMethod
    public void goSystemMyAppDetailsSetting() {
        Uri packageURI = Uri.parse("package:" + mWXSDKInstance.getContext().getPackageName());
        Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“开发人员选项界面”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemApplicationDevelopmentSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“应用程序列表界面”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemApplicationSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“蓝牙设置”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemBluetoothSetting() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“日期时间设置”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemDateSetting() {
        Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“移动网络设置”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemDataDoamingSetting() {
        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“手机显示”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemDisplaySetting() {
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“语言和输入设备设置”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemInputMethodsSetting() {
        Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统的“显示设置选择网络运营商设置”页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemNetworkOperatorSetting() {
        Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    /**
     * 跳转到系统设置页面.
     */
    @Keep
    @UniJSMethod
    public void goSystemSetting() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS );
        mWXSDKInstance.getContext().startActivity(intent);
    }
}

