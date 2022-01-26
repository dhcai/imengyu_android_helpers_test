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
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.imengyu.android_helpers.dialog.ConfirmDialog;
import com.imengyu.android_helpers.utils.CacheUtils;
import com.imengyu.android_helpers.utils.ClipboardUtils;
import com.imengyu.android_helpers.utils.StringUtils;
import com.kongzue.dialogx.dialogs.FullScreenDialog;
import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.io.File;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class NativeHelperModule extends WXModule {

    /**
     * 安装 Apk 文件
     * @param filePath Apk 文件路径，必须是绝对路径
     */
    @Keep
    @UniJSMethod
    public void installApk(String filePath) {
        Context context = mWXSDKInstance.getContext();
        File file = new File(filePath.startsWith("file://") ? filePath.substring(7) : filePath);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, "com.ydt.ydtapp.app.dc.fileprovider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.getApplicationContext().startActivity(intent);
    }
    /**
     * 执行Shell命令
     * @param shell Shell命令
     * @param callback 完成回调
     */
    @Keep
    @UniJSMethod
    public void execShell(String shell, final JSCallback callback) {
        ShellUtils.execCmdAsync(shell, false, commandResult -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", commandResult.result);
            jsonObject.put("errorMsg", commandResult.errorMsg);
            jsonObject.put("successMsg", commandResult.successMsg);
            callback.invoke(jsonObject);
        });
    }
    /**
     * 执行Root Shell命令，设备必须Root才能成功执行
     * @param shell Shell命令
     * @param callback 完成回调
     */
    @Keep
    @UniJSMethod
    public void execRootShell(String shell, final JSCallback callback) {
        ShellUtils.execCmdAsync(shell, true, commandResult -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", commandResult.result);
            jsonObject.put("errorMsg", commandResult.errorMsg);
            jsonObject.put("successMsg", commandResult.successMsg);
            callback.invoke(jsonObject);
        });
    }

    @Keep
    @UniJSMethod
    public int getAppUid() {
        return AppUtils.getAppUid();
    }
    @Keep
    @UniJSMethod
    public int getAppVersionCode() {
        return AppUtils.getAppVersionCode();
    }
    @Keep
    @UniJSMethod
    public String getAppVersionName() {
        return AppUtils.getAppVersionName();
    }
    @Keep
    @UniJSMethod
    public String getAppName() {
        return AppUtils.getAppName();
    }

    @Keep
    @UniJSMethod
    public boolean isAppForeground() {
        return AppUtils.isAppForeground();
    }
    @Keep
    @UniJSMethod
    public boolean isAppRoot() {
        return AppUtils.isAppRoot();
    }
    @Keep
    @UniJSMethod
    public boolean isAppDebug() {
        return AppUtils.isAppDebug();
    }
    @Keep
    @UniJSMethod
    public boolean isAppSystem() {
        return AppUtils.isAppSystem();
    }
    @Keep
    @UniJSMethod
    public boolean isAppInstalled(String pkgName) {
        return AppUtils.isAppInstalled(pkgName);
    }
    @Keep
    @UniJSMethod
    public boolean isAppRunning(String pkgName) {
        return AppUtils.isAppRunning(pkgName);
    }
    @Keep
    @UniJSMethod
    public boolean isFirstTimeInstall() {
        return AppUtils.isFirstTimeInstall();
    }
    @Keep
    @UniJSMethod
    public boolean isFirstTimeInstalled() {
        return AppUtils.isFirstTimeInstalled();
    }
    @Keep
    @UniJSMethod
    public void launchApp(String pkgName) {
        AppUtils.launchApp(pkgName);
    }
    @Keep
    @UniJSMethod
    public void exitApp() {
        AppUtils.exitApp();
    }


    /**
     * 获取APP缓存目录大小
     * @return 大小，已转换为可读的字符串
     */
    @Keep
    @UniJSMethod
    public String getAppCacheSize() {
        return CacheUtils.getAppCache(mWXSDKInstance.getContext());
    }

    /**
     * 清空APP缓存目录
     * @param callback 完成回调
     */
    @Keep
    @UniJSMethod
    public void clearAppCache(final JSCallback callback) {
        CacheUtils.clearAppCache((Activity) mWXSDKInstance.getContext(), () -> {
            callback.invoke(new JSONObject());
        });
    }

    /**
     * 测试崩溃
     */
    @Keep
    @UniJSMethod
    public void testCrash() {
        ((Activity) mWXSDKInstance.getContext()).runOnUiThread(() -> {
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    ((Activity) mWXSDKInstance.getContext()).runOnUiThread(() -> {
                        ((AppCompatActivity) mWXSDKInstance.getContext()).findViewById(R.id.fab);
                    });
                }
            }, 400);
        });
    }

    /**
     * 检查APP上次是否异常崩溃，如果崩溃，则会返回异常信息的文本，可自由处理，提交错误报告到云端或者其他操作。
     * @param showDialog 是否显示一个包含错误信息的对话框，推荐在开发时显示，可以帮助您查看错误信息。
     * @return 返回错误信息文本，如果没有错误信息，则返回null
     */
    @Keep
    @UniJSMethod
    public String checkCrashLogOrReport(boolean showDialog) {
        final Context context = mWXSDKInstance.getContext();
        final File lastCrashLog = new File(context.getCacheDir().getAbsolutePath() + "/CrashLog.log");
        if(lastCrashLog.exists()) {
            final String errString = StringUtils.readFileToString(lastCrashLog);
            if(showDialog) {
                ConfirmDialog.Builder builder = ConfirmDialog.Builder(mWXSDKInstance.getContext());
                builder.setMessage("检查到应用上次异常崩溃，是否要查看错误信息并提交？提交错误信息可以让我们更好解决此问题，以防止再次发生。");
                builder.setTitle("异常崩溃提示");
                builder.setCancelable(false);
                builder.setOnConfirmClickListener("查看错误日志", (dialog, view) -> {
                    dialog.dismiss();

                    FullScreenDialog.show(new OnBindView<FullScreenDialog>(R.layout.layout_error_report) {
                        @Override
                        public void onBind(FullScreenDialog dialog, View rootView) {
                            TextView text_error = rootView.findViewById(R.id.text_error);
                            rootView.findViewById(R.id.btn_close).setOnClickListener((v) -> {
                                dialog.dismiss();
                                if (!lastCrashLog.delete()) lastCrashLog.deleteOnExit();
                            });
                            text_error.setText(errString);
                            text_error.setOnClickListener((v1) -> {
                                ClipboardUtils.copyContentToClipboard(errString, context);
                                PopTip.show(R.mipmap.ic_done_black, "复制成功");
                            });
                        }
                    });
                });
                builder.setOnCancelClickListener("取消", (dialog, view) -> {
                    dialog.dismiss();
                    if (!lastCrashLog.delete()) lastCrashLog.deleteOnExit();
                });
                builder.build().show();
            }
            return errString;
        }
        return null;
    }
}

