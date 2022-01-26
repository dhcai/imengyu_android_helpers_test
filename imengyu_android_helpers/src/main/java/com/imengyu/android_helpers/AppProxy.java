package com.imengyu.android_helpers;

import android.app.Application;
import android.content.Context;

import androidx.annotation.Keep;

import com.hjq.toast.ToastUtils;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.BaseDialog;

import io.dcloud.feature.uniapp.UniAppHookProxy;

@Keep
public class AppProxy implements UniAppHookProxy {

    private static final String TAG = "AppProxy";
    private static Context appContext;

    public static Context getAppContext() { return appContext; }
    
    @Override
    public void onSubProcessCreate(Application application) {
        appContext = application;
        DialogX.useActivityLayoutTranslationNavigationBar = false;
        BaseDialog.init(appContext);
        ToastUtils.init(application);
        ToastUtils.setView(R.layout.toast_custom_view);
    }
    @Override
    public void onCreate(Application application) {
        appContext = application;
        DialogX.useActivityLayoutTranslationNavigationBar = false;
        BaseDialog.init(appContext);
        ToastUtils.init(application);
        ToastUtils.setView(R.layout.toast_custom_view);
    }
}
