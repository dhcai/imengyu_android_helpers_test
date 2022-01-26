package com.imengyu.android_helpers;


import android.graphics.Color;
import android.net.Uri;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.NotificationUtils;
import com.taobao.weex.common.WXModule;

import java.io.File;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

public class NotificationModule extends WXModule {

    @Keep
    @UniJSMethod
    public boolean areNotificationsEnabled() {
        return NotificationUtils.areNotificationsEnabled();
    }
    @Keep
    @UniJSMethod
    public void cancel(int id) {
        NotificationUtils.cancel(id);
    }
    @Keep
    @UniJSMethod
    public void cancelAll() {
        NotificationUtils.cancelAll();
    }
    @Keep
    @UniJSMethod
    public void cancelWithTag(String tag, int id) {
        NotificationUtils.cancel(tag, id);
    }
    @Keep
    @UniJSMethod
    public JSONObject notify(JSONObject options) {
        boolean success = false;
        String errMsg = "ok";
        if(options.containsKey("id")) {
            int id = options.getInteger("id");
            NotificationUtils.notify(id, builder -> {
                if(options.containsKey("color"))
                    builder.setColor(Color.parseColor(options.getString("color")));
                if(options.containsKey("text"))
                    builder.setContentText(options.getString("text"));
                if(options.containsKey("title"))
                    builder.setContentTitle(options.getString("title"));
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
                if(options.containsKey("sound")) {
                    String sound = options.getString("sound");
                    File file = new File(sound);
                    if(file.exists())
                        builder.setSound(Uri.fromFile(file));
                }

            });
        } else {
            errMsg = "id is required";
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", success);
        jsonObject.put("errMsg", errMsg);
        return jsonObject;
    }

}
