package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.DeviceUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class DeviceModule extends WXModule {

    @Keep
    @UniJSMethod
    public boolean isAdbEnabled() {
        return DeviceUtils.isAdbEnabled();
    }
    @Keep
    @UniJSMethod
    public boolean isDeviceRooted() {
        return DeviceUtils.isDeviceRooted();
    }
    @Keep
    @UniJSMethod
    public boolean isEmulator() {
        return DeviceUtils.isEmulator();
    }
    @Keep
    @UniJSMethod
    public boolean isDevelopmentSettingsEnabled() {
        return DeviceUtils.isDevelopmentSettingsEnabled();
    }
    @Keep
    @UniJSMethod
    public boolean isTablet() {
        return DeviceUtils.isTablet();
    }
    @Keep
    @UniJSMethod
    public String getAndroidID() {
        return DeviceUtils.getAndroidID();
    }
    @Keep
    @UniJSMethod
    public String getManufacturer() {
        return DeviceUtils.getManufacturer();
    }
    @Keep
    @UniJSMethod
    public String getModel() {
        return DeviceUtils.getModel();
    }
    @Keep
    @UniJSMethod
    public String getUniqueDeviceId() {
        return DeviceUtils.getUniqueDeviceId();
    }
    @Keep
    @UniJSMethod
    public String getUniqueDeviceIdWithCache() {
        return DeviceUtils.getUniqueDeviceId(true);
    }
    @Keep
    @UniJSMethod
    public String getUniqueDeviceIdWithPrefix(String prefix) {
        return DeviceUtils.getUniqueDeviceId(prefix);
    }
    @Keep
    @UniJSMethod
    public String getMacAddress() {
        return DeviceUtils.getMacAddress();
    }
    @Keep
    @UniJSMethod
    public String getSDKVersionName() {
        return DeviceUtils.getSDKVersionName();
    }
    @Keep
    @UniJSMethod
    public int getSDKVersionCode() {
        return DeviceUtils.getSDKVersionCode();
    }
}

