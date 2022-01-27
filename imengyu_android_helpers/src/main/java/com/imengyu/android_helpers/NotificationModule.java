package com.imengyu.android_helpers;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.NotificationUtils;
import com.taobao.weex.common.WXModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

/**
 * 通知模块
 */
public class NotificationModule extends WXModule {

    /**
     * 获取当前App是否开启了通知
     */
    @Keep
    @UniJSMethod
    public boolean areNotificationsEnabled() {
        return NotificationUtils.areNotificationsEnabled();
    }

    /**
     * 取消某个ID的通知
     * @param id ID
     */
    @Keep
    @UniJSMethod
    public void cancel(int id) {
        NotificationUtils.cancel(id);
    }

    /**
     * 取消所有通知
     */
    @Keep
    @UniJSMethod
    public void cancelAll() {
        NotificationUtils.cancelAll();
    }

    /**
     * 取消某个Tag的通知
     * @param tag Tag
     * @param id ID
     */
    @Keep
    @UniJSMethod
    public void cancelWithTag(String tag, int id) {
        NotificationUtils.cancel(tag, id);
    }

    /**
     * 发送原生通知
     * @param options 参数
     * {
     *                id: number,
     *                channelId: number,
     *                tag: string,
     *                color: string,
     *                text: string,
     *                title: string,
     *                smallIcon: number,
     *                largeIcon: string,
     *                category: string,
     *                autoCancel: boolean,
     *                onlyAlertOnce: boolean,
     *                ongoing: string,
     *                number: number,
     *                priority: number,
     *                vibrate: number[],
     * }
     * @return 返回信息
     * {
     *      success: boolean,
     *      errMsg: string,
     * }
     */
    @Keep
    @UniJSMethod
    public JSONObject notify(JSONObject options) {
        boolean success = false;
        String errMsg = "ok";
        if(options.containsKey("id")) {
            int id = options.getInteger("id");
            String tag = "";

            if(options.containsKey("tag"))
                tag = options.getString("tag");

            NotificationUtils.notify(tag, id, builder -> {
                if(options.containsKey("color"))
                    builder.setColor(Color.parseColor(options.getString("color")));
                if(options.containsKey("text"))
                    builder.setContentText(options.getString("text"));
                if(options.containsKey("title"))
                    builder.setContentTitle(options.getString("title"));
                if(options.containsKey("autoCancel"))
                    builder.setAutoCancel(options.getBoolean("autoCancel"));
                if(options.containsKey("ongoing"))
                    builder.setOngoing(options.getBoolean("ongoing"));
                if(options.containsKey("onlyAlertOnce"))
                    builder.setOnlyAlertOnce(options.getBoolean("onlyAlertOnce"));
                if(options.containsKey("autoCancel"))
                    builder.setAutoCancel(options.getBoolean("autoCancel"));
                if(options.containsKey("channelId"))
                    builder.setChannelId(options.getString("channelId"));
                if(options.containsKey("category"))
                    builder.setCategory(options.getString("category"));
                if(options.containsKey("number"))
                    builder.setNumber(options.getInteger("number"));
                if(options.containsKey("priority"))
                    builder.setPriority(options.getInteger("priority"));
                if(options.containsKey("vibrate")) {
                    JSONArray vibrate = options.getJSONArray("vibrate");
                    long[] vibrateR = new long[vibrate.size()];
                    for (int i = 0; i < vibrate.size(); i++)
                        vibrateR[i] = vibrate.getLong(i);
                    builder.setVibrate(vibrateR);
                }
                if(options.containsKey("sound")) {
                    String sound = options.getString("sound");
                    File file = new File(sound);
                    if(file.exists())
                        builder.setSound(Uri.fromFile(file));
                }
                if(options.containsKey("smallIcon"))
                    builder.setSmallIcon(options.getInteger("smallIcon"));
                if(options.containsKey("largeIcon")) {
                    String path = options.getString("largeIcon");
                    if(FileUtils.isFileExists(path))
                        builder.setLargeIcon(BitmapFactory.decodeFile(path));
                }
            });
            success = true;
        } else {
            errMsg = "id is required";
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", success);
        jsonObject.put("errMsg", errMsg);
        return jsonObject;
    }

}
