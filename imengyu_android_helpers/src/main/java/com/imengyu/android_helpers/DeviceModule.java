package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.DeviceUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class DeviceModule extends WXModule {

    /**
     * 获取系统是否启用ADB连接
     */
    @Keep
    @UniJSMethod
    public boolean isAdbEnabled() {
        return DeviceUtils.isAdbEnabled();
    }
    /**
     * 获取设备是否Root
     */
    @Keep
    @UniJSMethod
    public boolean isDeviceRooted() {
        return DeviceUtils.isDeviceRooted();
    }
    /**
     * 获取设备是否是模拟器
     */
    @Keep
    @UniJSMethod
    public boolean isEmulator() {
        return DeviceUtils.isEmulator();
    }
    /**
     * 获取设备是否开启了开发者模式
     */
    @Keep
    @UniJSMethod
    public boolean isDevelopmentSettingsEnabled() {
        return DeviceUtils.isDevelopmentSettingsEnabled();
    }
    /**
     * 获取设备是否是平板
     */
    @Keep
    @UniJSMethod
    public boolean isTablet() {
        return DeviceUtils.isTablet();
    }
    /**
     * 获取AndroidID
     */
    @Keep
    @UniJSMethod
    public String getAndroidID() {
        return DeviceUtils.getAndroidID();
    }
    /**
     * 获取设备制造商
     */
    @Keep
    @UniJSMethod
    public String getManufacturer() {
        return DeviceUtils.getManufacturer();
    }
    /**
     * 获取设备模型
     */
    @Keep
    @UniJSMethod
    public String getModel() {
        return DeviceUtils.getModel();
    }
    /**
     * 获取设备唯一ID
     */
    @Keep
    @UniJSMethod
    public String getUniqueDeviceId() {
        return DeviceUtils.getUniqueDeviceId();
    }
    /**
     * 获取设备唯一ID(带缓存)
     */
    @Keep
    @UniJSMethod
    public String getUniqueDeviceIdWithCache() {
        return DeviceUtils.getUniqueDeviceId(true);
    }
    /**
     * 获取设备唯一ID（加前缀）
     */
    @Keep
    @UniJSMethod
    public String getUniqueDeviceIdWithPrefix(String prefix) {
        return DeviceUtils.getUniqueDeviceId(prefix);
    }
    /**
     * 获取设备唯一ID（加前缀带缓存）
     */
    @Keep
    @UniJSMethod
    public String getUniqueDeviceIdWithPrefixAndCache(String prefix) {
        return DeviceUtils.getUniqueDeviceId(prefix, true);
    }
    /**
     * 获取设备MAC地址
     */
    @Keep
    @UniJSMethod
    public String getMacAddress() {
        return DeviceUtils.getMacAddress();
    }
    /**
     * 获取SDK版本名称
     */
    @Keep
    @UniJSMethod
    public String getSDKVersionName() {
        return DeviceUtils.getSDKVersionName();
    }
    /**
     * 获取SDK版本号
     */
    @Keep
    @UniJSMethod
    public int getSDKVersionCode() {
        return DeviceUtils.getSDKVersionCode();
    }
}

