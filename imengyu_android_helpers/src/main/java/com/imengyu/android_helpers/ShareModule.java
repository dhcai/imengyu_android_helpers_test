package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imengyu.android_helpers.utils.ShareUtils;
import com.taobao.weex.common.WXModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class ShareModule extends WXModule {

    /**
     * 调用系统分享多个本地文件
     * @param options
     * {
     *     files: string[], //文件路径数组，必须是本地绝对路径
     *     title: string //分享标题，默认是 “分享 %d 个文件”， %d 是文件个数。
     * }
     */
    @Keep
    @UniJSMethod
    public void shareFiles(JSONObject options) {
        List<File> fileList = new ArrayList<>();
        String title = "分享 %d 个文件";
        if(options.containsKey("files")) {
            JSONArray files = options.getJSONArray("files");
            for (int i = 0; i < files.size(); i++) {
                fileList.add(new File(files.getString(i)));
            }
        }
        if(options.containsKey("title"))
            title = options.getString("title");

        ShareUtils.shareStreamMultiple(mWXSDKInstance.getContext(), fileList, title);
    }
    /**
     * 调用系统分享本地文件
     * @param options
     * {
     *     file: string, //文件路径，必须是本地绝对路径
     *     title: string //分享标题，默认是 “分享文件”
     * }
     */
    @Keep
    @UniJSMethod
    public void shareFile(JSONObject options) {
        String file = null;
        String title = "分享文件";
        if(options.containsKey("file"))
            file = options.getString("file");
        if(options.containsKey("title"))
            title = options.getString("title");

        if(file != null && !file.isEmpty())
            ShareUtils.shareFile(mWXSDKInstance.getContext(), file, title);
    }

}

