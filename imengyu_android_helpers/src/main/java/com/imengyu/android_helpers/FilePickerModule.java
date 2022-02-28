package com.imengyu.android_helpers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imengyu.android_helpers.filepicker.FilePickerActivity;
import com.imengyu.android_helpers.filepicker.PickerManager;
import com.imengyu.android_helpers.filepicker.model.FileEntity;
import com.imengyu.android_helpers.filepicker.model.FileType;
import com.imengyu.android_helpers.utils.FileUtils;
import com.imengyu.android_helpers.utils.ShareUtils;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.ArrayList;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class FilePickerModule extends WXModule {

    private JSCallback callback = null;
    private JSCallback callback2 = null;

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
     * 调用选择器选择本地文件
     *
     * @param options  {
     *                      maxCount: number, //可选数量
     *                      docType: boolean, //如果为 true，则忽略types，设置选择器只选择文档类型，包括ppt，word，excel等等
     *                      type: string[] //自定义类型，设置可以选择的文件类型，写后缀名，如： [ "xls","xlt","xlsx","xltx" ]
     *                 }
     * @param callback {
     *                      paths: string[] //选择的文件路径
     *                      files: {
     *                          name: string, //名称
     *                          path: string, //路径
     *                          mimeType: string, //文件的mime类型
     *                          size: string, //大小
     *                          date: string, //修改日期
     *                      }[] //选择的文件路径
     *                 }
     */
    @Keep
    @UniJSMethod
    public void pickFiles(JSONObject options, final JSCallback callback) {
        this.callback2 = callback;
        Intent intent = new Intent(((Activity) mWXSDKInstance.getContext()), FilePickerActivity.class);
        if (options.containsKey("maxCount")) {
            PickerManager.getInstance().setMaxCount(options.getInteger("maxCount"));
        }
        if (options.containsKey("docType") && options.getBoolean("docType")) {
            PickerManager.getInstance().mFileTypes.clear();
            PickerManager.getInstance().addDocTypes();
        }
        else if (options.containsKey("types")) {
            PickerManager.getInstance().mFileTypes.clear();

            JSONArray arr = options.getJSONArray("types");
            ArrayList<String> filters = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                filters.add(arr.getString(i));
            }

            PickerManager.getInstance().mFileTypes.add(new FileType("CUSTOM", (String[])filters.toArray(),R.mipmap.file_picker_def));
        }
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(intent, 1232);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1231 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if(this.callback != null) {
                JSONObject o = new JSONObject();
                o.put("path", FileUtils.getPath(mWXSDKInstance.getContext(), uri));
                this.callback.invoke(o);
                this.callback = null;
            }
        }
        else if (requestCode == 1232 && resultCode == Activity.RESULT_OK) {
            if(this.callback2 != null) {
                JSONObject o = new JSONObject();
                JSONArray filesPaths = new JSONArray();
                JSONArray files = new JSONArray();
                for (int i = 0; i < PickerManager.getInstance().files.size(); i++) {
                    FileEntity entity = PickerManager.getInstance().files.get(i);
                    filesPaths.add(entity.getPath());
                    files.add(entity);
                }

                o.put("paths", filesPaths);
                o.put("files", files);
                this.callback2.invoke(o);
                this.callback2 = null;
            }
        }
    }

    /**
     * 调用系统选择App打开本地文件
     *
     * @param options  {
     *     file: string, //文件路径，必须是本地绝对路径
     *     title: string //对话框标题，默认是 “选择应用打开此文件”
     * }
     */
    @Keep
    @UniJSMethod
    public void openFileWithApp(JSONObject options) {
        String file = null;
        String title = "分享文件";
        if(options.containsKey("file"))
            file = options.getString("file");
        if(options.containsKey("title"))
            title = options.getString("title");

        if(file != null && !file.isEmpty())
            FileUtils.openFileWithApp(mWXSDKInstance.getContext(), file, title);
    }

}

