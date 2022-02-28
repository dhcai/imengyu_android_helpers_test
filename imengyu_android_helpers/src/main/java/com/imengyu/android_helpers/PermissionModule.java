package com.imengyu.android_helpers;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.PermissionUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.List;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class PermissionModule extends WXModule {

    /**
     * 获取当前App是否有存储管理权限（Android11+），Android11以下永远返回true
     */
    @Keep
    @UniJSMethod
    public boolean isExternalStorageManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return true;
    }

    /**
     * 获取当前是否是 Android11+ 设备
     */
    @Keep
    @UniJSMethod
    public boolean isAndroid11AndUp() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R);
    }

    /**
     * 获取当前App是否授予某个权限
     * @param name 权限的完整名称，例如 “android.permission.SYSTEM_ALERT_WINDOW”
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedPermission(String name) { return XXPermissions.isGranted(mWXSDKInstance.getContext(), name); }

    /**
     * 获取当前App是否授予悬浮窗权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PermissionUtils.isGrantedDrawOverlays();
        }
        return true;
    }
    /**
     * 获取当前App是否授予写入系统设置权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PermissionUtils.isGrantedWriteSettings();
        }
        return true;
    }
    /**
     * 获取当前App是否授予全局窗口权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedSystemAlertWindow() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.SYSTEM_ALERT_WINDOW); }
    /**
     * 获取当前App是否授予安装App权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedInstallPackages() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.INSTALL_PACKAGES); }
    /**
     * 获取当前App是否授予写入外置存储权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteExternalStorage() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE); }
    /**
     * 获取当前App是否授予读取外置存储权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReadExternalStorage() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE); }
    /**
     * 获取当前App是否授予管理外置存储权限（Android11+）
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedManageExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        }
        return true;
    }
    /**
     * 获取当前App是否授予读取日历权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReadCalender() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_CALENDAR); }
    /**
     * 获取当前App是否授予写入日历权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteCalender() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.WRITE_CALENDAR); }
    /**
     * 获取当前App是否授予摄像机权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedCamera() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.CAMERA); }
    /**
     * 获取当前App是否授予读取联系人权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReadContacts() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_CONTACTS); }
    /**
     * 获取当前App是否授予写入联系人权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteContacts() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.WRITE_CONTACTS); }
    /**
     * 获取当前App是否授予获取手机账户权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedGetAccounts() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.GET_ACCOUNTS); }
    /**
     * 获取当前App是否授予读取精确位置权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedAccessFineLocation() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ACCESS_FINE_LOCATION); }
    /**
     * 获取当前App是否授予读取粗略位置权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedAccessCoarseLocation() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION); }
    @Keep
    @UniJSMethod
    public boolean isGrantedAccessMediaLocation() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ACCESS_MEDIA_LOCATION); }
    @Keep
    @UniJSMethod
    public boolean isGrantedAccessBackgroundLocation() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION); }
    /**
     * 获取当前App是否授予读取手机信息权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReadPhoneState() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_PHONE_STATE); }
    /**
     * 获取当前App是否授予拨打电话权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedCallPhone() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.CALL_PHONE); }
    /**
     * 获取当前App是否授予读取通话记录权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReadCallLog() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_CALL_LOG); }
    /**
     * 获取当前App是否授予写入通话记录权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteCallLog() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.WRITE_CALL_LOG); }
    /**
     * 获取当前App是否授予添加声音邮件权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedAddVoiceMail() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ADD_VOICEMAIL); }
    /**
     * 获取当前App是否授予使用SIP权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedUseSIP() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.USE_SIP); }
    /**
     * 获取当前App是否授予传感器权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedBodySensors() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.BODY_SENSORS); }
    /**
     * 获取当前App是否授予发送短信权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedSendSms() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.SEND_SMS); }
    /**
     * 获取当前App是否授予接受短信权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReceiveSms() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.RECEIVE_SMS); }
    /**
     * 获取当前App是否授予读取短信权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReadSms() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_SMS); }
    /**
     * 获取当前App是否授予权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReceiveWapPush() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.RECEIVE_WAP_PUSH); }
    /**
     * 获取当前App是否授予权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedReceiveMms() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.RECEIVE_MMS); }
    /**
     * 获取当前App是否授予录音权限
     */
    @Keep
    @UniJSMethod
    public boolean isGrantedRecordAudio() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.RECORD_AUDIO); }


    private OnPermissionCallback getOnPermissionCallback(final JSCallback callback) {
        return new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> permissions, boolean all) {
                JSONObject result = new JSONObject();
                result.put("all", all);
                result.put("permissions", permissions);
                result.put("granted", true);
                result.put("never", false);
                callback.invoke(result);
            }
            @Override
            public void onDenied(List<String> permissions, boolean never) {
                JSONObject result = new JSONObject();
                result.put("never", never);
                result.put("permissions", permissions);
                result.put("granted", false);
                callback.invoke(result);
            }
        };
    }

    /**
     * 请求管理外置存储权限（Android11+）
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestManageExternalStorage(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.MANAGE_EXTERNAL_STORAGE).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求写入外置存储权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestWriteExternalStorage(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.WRITE_EXTERNAL_STORAGE).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求读取外置存储权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReadExternalStorage(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_EXTERNAL_STORAGE).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求录音权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestRecordAudio(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.RECORD_AUDIO).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求写入系统设置权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestWriteSettings(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.WRITE_SETTINGS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求精确定位权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestAccessFineLocation(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ACCESS_FINE_LOCATION).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求获取粗略位置权限权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestAccessCoarseLocation(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ACCESS_COARSE_LOCATION).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求ACCESS_BACKGROUND_LOCATION权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestAccessBackgroundLocation(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ACCESS_BACKGROUND_LOCATION).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求ACCESS_MEDIA_LOCATION权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestAccessMediaLocation(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ACCESS_MEDIA_LOCATION).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求读取日历权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReadCalender(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_CALENDAR).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求相机权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestCamera(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.CAMERA).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求读取通讯录权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReadContacts(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_CONTACTS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求写入通讯录权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestWriteContacts(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.WRITE_CONTACTS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求获取手机账号权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestGetAccounts(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.GET_ACCOUNTS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求读取手机状态权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReadPhoneState(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_PHONE_STATE).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求拨打电话权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestCallPhone(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.CALL_PHONE).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求读取通话记录权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReadCallLog(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_CALL_LOG).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求写入通话记录权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestWriteCallLog(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.WRITE_CALL_LOG).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求权ADD_VOICEMAIL限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestAddVoiceMail(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ADD_VOICEMAIL).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求USE_SIP权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestUseSIP(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.USE_SIP).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求身体传感器权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestBodySensors(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.BODY_SENSORS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求发送短信权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestSendSms(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.SEND_SMS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求接收短信权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReceiveSms(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.RECEIVE_SMS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求读取短信权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReadSms(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_SMS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求RECEIVE_WAP_PUSH权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReceiveWapPush(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.RECEIVE_WAP_PUSH).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求接收MMS权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestReceiveMms(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.RECEIVE_MMS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求蓝牙组权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestGroupBlueTooth(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.BLUETOOTH).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求日历组权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestGroupCalendar(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.CALENDAR).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求通讯录组权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestGroupContacts(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.CONTACTS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求传感器组权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestGroupSensors(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.SENSORS).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求存储组权限
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void requestGroupStorage(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.STORAGE).request(getOnPermissionCallback(callback));
    }
    /**
     * 请求指定名称的权限
     * @param permissionName 权限的完整名称，例如 “android.permission.SYSTEM_ALERT_WINDOW”
     * @param callback 回调：
     *                 {
     *                       granted: boolean, //是否授予权限
     *                       permissions: string[], //当前请求的权限名称列表
     *                       all: boolean, //用户是否授予了全部权限，如果是请求一组权限时，用户可能同意某个，可能会不同意另外几个
     *                       never: boolean, //用户是否点击取消并且永久不拒绝
     *                 }
     */
    @Keep
    @UniJSMethod
    public void request(String permissionName, final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext())
                .permission(permissionName)
                .request(getOnPermissionCallback(callback));
    }
}

