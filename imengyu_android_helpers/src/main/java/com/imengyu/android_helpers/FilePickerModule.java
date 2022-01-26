package com.imengyu.android_helpers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imengyu.android_helpers.utils.FileUtils;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import net.muliba.fancyfilepickerlibrary.FilePicker;

import java.util.List;
import java.util.function.Function;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Keep
public class FilePickerModule extends WXModule {

    private JSCallback callback = null;

    /**
     * 调用系统选择器选择本地文件
     *
     * @param options  {
     *                      type: string //文件类型可以是，image/*：选择图片； audio/* 选择音频； video/* 选择视频； video/*;image/* 同时选择视频和图片；* /* 无限制
     *                 }
     * @param callback {
     *                      path: string //选择的文件路径
     *                 }
     */
    @Keep
    @UniJSMethod
    public void pickFileWithSystem(JSONObject options, final JSCallback callback) {
        this.callback = callback;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (options.containsKey("type"))
            intent.setType(options.getString("type"));
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(intent, 1231);
    }
    /**
     * 选择本地文件
     *
     * @param options  {
     *                      single: boolean //是否是单选，默认是
     *                 }
     * @param callback {
     *                      files: string //选择的文件路径
     *                 }
     */
    @Keep
    @UniJSMethod
    public void pickFile(JSONObject options, final JSCallback callback) {
        boolean single = true;
        if(options.containsKey("single"))
            single = options.getBoolean("single");

        new FilePicker().withActivity((Activity) mWXSDKInstance.getContext())
                .chooseType(single ? FilePicker.CHOOSE_TYPE_SINGLE() : FilePicker.CHOOSE_TYPE_MULTIPLE())
                .forResult(strings -> {
                    JSONObject o = new JSONObject();
                    JSONArray files = new JSONArray();
                    files.addAll(strings);
                    o.put("files", files);
                    callback.invoke(o);
                    return Unit.INSTANCE;
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if(this.callback != null) {
                JSONObject o = new JSONObject();
                o.put("path", FileUtils.getPath(mWXSDKInstance.getContext(), uri));
                this.callback.invoke(o);
                this.callback = null;
            }
        }
    }
}

