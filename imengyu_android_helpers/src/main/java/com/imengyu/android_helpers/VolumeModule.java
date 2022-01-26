package com.imengyu.android_helpers;

import android.media.AudioManager;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.VibrateUtils;
import com.blankj.utilcode.util.VolumeUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class VolumeModule extends WXModule {

    @Keep
    @UniJSMethod
    public void cancel() {
        VibrateUtils.cancel();
    }

    @Keep
    @UniJSMethod
    public int stringToStreamType(String streamTypeString) {
        int streamType = AudioManager.STREAM_MUSIC;
        switch(streamTypeString) {
            case "voiceCall": streamType = AudioManager.STREAM_VOICE_CALL; break;
            case "system": streamType = AudioManager.STREAM_SYSTEM; break;
            case "ring": streamType = AudioManager.STREAM_RING; break;
            case "music": streamType = AudioManager.STREAM_MUSIC; break;
            case "alarm": streamType = AudioManager.STREAM_ALARM; break;
            case "notification": streamType = AudioManager.STREAM_NOTIFICATION; break;
            case "dtmf": streamType = AudioManager.STREAM_DTMF; break;
        }
        return streamType;
    }

    @Keep
    @UniJSMethod
    public void getVolume(String streamType) {
        VolumeUtils.getVolume(stringToStreamType(streamType));
    }
    @Keep
    @UniJSMethod
    public void setVolume(JSONObject options) {
        if(options.containsKey("streamType") && options.containsKey("volume")) {
            int flags = 0;

            if(options.containsKey("showUI") && options.getBoolean("showUI"))
                flags |= AudioManager.FLAG_SHOW_UI;
            if(options.containsKey("vibrate") && options.getBoolean("vibrate"))
                flags |= AudioManager.FLAG_VIBRATE;
            if(options.containsKey("playSound") && options.getBoolean("playSound"))
                flags |= AudioManager.FLAG_PLAY_SOUND;

            VolumeUtils.setVolume(stringToStreamType(options.getString("streamType")),
                    options.getInteger("volume"),
                    flags);
        }
    }

}

