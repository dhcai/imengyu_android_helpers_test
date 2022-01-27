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

    @Keep
    @UniJSMethod
    public boolean isExternalStorageManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return true;
    }
    @Keep
    @UniJSMethod
    public boolean isAndroid11AndUp() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R);
    }

    @Keep
    @UniJSMethod
    public boolean isGrantedPermission(String name) { return XXPermissions.isGranted(mWXSDKInstance.getContext(), name); }
    @Keep
    @UniJSMethod
    public boolean isGrantedDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PermissionUtils.isGrantedDrawOverlays();
        }
        return true;
    }
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PermissionUtils.isGrantedWriteSettings();
        }
        return true;
    }
    @Keep
    @UniJSMethod
    public boolean isGrantedSystemAlertWindow() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.SYSTEM_ALERT_WINDOW); }
    @Keep
    @UniJSMethod
    public boolean isGrantedInstallPackages() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.INSTALL_PACKAGES); }
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteExternalStorage() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE); }
    @Keep
    @UniJSMethod
    public boolean isGrantedReadExternalStorage() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE); }
    @Keep
    @UniJSMethod
    public boolean isGrantedManageExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        }
        return true;
    }
    @Keep
    @UniJSMethod
    public boolean isGrantedReadCalender() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_CALENDAR); }
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteCalender() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.WRITE_CALENDAR); }
    @Keep
    @UniJSMethod
    public boolean isGrantedCamera() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.CAMERA); }
    @Keep
    @UniJSMethod
    public boolean isGrantedReadContacts() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_CONTACTS); }
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteContacts() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.WRITE_CONTACTS); }
    @Keep
    @UniJSMethod
    public boolean isGrantedGetAccounts() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.GET_ACCOUNTS); }
    @Keep
    @UniJSMethod
    public boolean isGrantedAccessFineLocation() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ACCESS_FINE_LOCATION); }
    @Keep
    @UniJSMethod
    public boolean isGrantedAccessMediaLocation() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ACCESS_MEDIA_LOCATION); }
    @Keep
    @UniJSMethod
    public boolean isGrantedAccessBackgrundLocation() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION); }
    @Keep
    @UniJSMethod
    public boolean isGrantedReadPhoneState() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_PHONE_STATE); }
    @Keep
    @UniJSMethod
    public boolean isGrantedCallPhone() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.CALL_PHONE); }
    @Keep
    @UniJSMethod
    public boolean isGrantedReadCallLog() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_CALL_LOG); }
    @Keep
    @UniJSMethod
    public boolean isGrantedWriteCallLog() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.WRITE_CALL_LOG); }
    @Keep
    @UniJSMethod
    public boolean isGrantedAddVoiceMail() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.ADD_VOICEMAIL); }
    @Keep
    @UniJSMethod
    public boolean isGrantedUseSIP() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.USE_SIP); }
    @Keep
    @UniJSMethod
    public boolean isGrantedBodySensors() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.BODY_SENSORS); }
    @Keep
    @UniJSMethod
    public boolean isGrantedSendSms() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.SEND_SMS); }
    @Keep
    @UniJSMethod
    public boolean isGrantedReceiveSms() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.RECEIVE_SMS); }
    @Keep
    @UniJSMethod
    public boolean isGrantedReadSms() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.READ_SMS); }
    @Keep
    @UniJSMethod
    public boolean isGrantedReceiveWapPush() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.RECEIVE_WAP_PUSH); }
    @Keep
    @UniJSMethod
    public boolean isGrantedReceiveMms() { return XXPermissions.isGranted(mWXSDKInstance.getContext(), Manifest.permission.RECEIVE_MMS); }
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

    @Keep
    @UniJSMethod
    public void requestManageExternalStorage(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.MANAGE_EXTERNAL_STORAGE).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestWriteExternalStorage(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.WRITE_EXTERNAL_STORAGE).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReadExternalStorage(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_EXTERNAL_STORAGE).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestRecordAudio(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.RECORD_AUDIO).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestWriteSettings(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.WRITE_SETTINGS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestAccessFineLocation(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ACCESS_FINE_LOCATION).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestAccessCoarseLocation(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ACCESS_COARSE_LOCATION).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestAccessBackgroundLocation(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ACCESS_BACKGROUND_LOCATION).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestAccessMediaLocation(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ACCESS_MEDIA_LOCATION).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReadCalender(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_CALENDAR).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestCamera(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.CAMERA).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReadContacts(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_CONTACTS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestWriteContacts(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.WRITE_CONTACTS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestGetAccounts(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.GET_ACCOUNTS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReadPhoneState(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_PHONE_STATE).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestCallPhone(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.CALL_PHONE).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReadCallLog(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_CALL_LOG).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestWWriteCallLog(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.WRITE_CALL_LOG).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestAddVoiceMail(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.ADD_VOICEMAIL).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestUseSIP(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.USE_SIP).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestBodySensors(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.BODY_SENSORS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestSendSms(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.SEND_SMS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReceiveSms(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.RECEIVE_SMS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReadSms(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.READ_SMS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReceiveWapPush(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.RECEIVE_WAP_PUSH).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestReceiveMms(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.RECEIVE_MMS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestGroupBlueTooth(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.BLUETOOTH).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestGroupCalendar(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.CALENDAR).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestGroupContacts(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.CONTACTS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestGroupSensors(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.SENSORS).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void requestGroupStorage(final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext()).permission(Permission.Group.STORAGE).request(getOnPermissionCallback(callback));
    }
    @Keep
    @UniJSMethod
    public void request(String permissionName, final JSCallback callback) {
        XXPermissions.with((Activity) mWXSDKInstance.getContext())
                .permission(permissionName)
                .request(getOnPermissionCallback(callback));
    }
}

