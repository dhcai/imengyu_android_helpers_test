package com.imengyu.android_helpers;

import android.media.AudioManager;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.VolumeUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

/**
 * 音量工具类
 */
@Keep
public class VolumeModule extends WXModule {

    /**
     * 字符串转 AudioManager.STREAM_*
     * @param streamTypeString 'voiceCall'|'system'|'ring'|'music'|'alarm'|'notification'|'dtmf'
     * @return 返回 AudioManager.STREAM_*
     */
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

    /**
     * 获取某个类型的音量
     * @param streamType 'voiceCall'|'system'|'ring'|'music'|'alarm'|'notification'|'dtmf'
     */
    @Keep
    @UniJSMethod
    public void getVolume(String streamType) {
        VolumeUtils.getVolume(stringToStreamType(streamType));
    }

    /**
     * 设置某个类型的音量
     * @param options 参数：
     * {
     *     streamType: 'voiceCall'|'system'|'ring'|'music'|'alarm'|'notification'|'dtmf', //类型
     *     showUI: boolean, //是否显示UI，默认true
     *     vibrate: boolean, //是否添加AudioManager.FLAG_VIBRATE，默认false
     *     playSound: boolean, //是否添加AudioManager.FLAG_PLAY_SOUND，默认false
     * }
     */
    @Keep
    @UniJSMethod
    public void setVolume(JSONObject options) {
        if(options.containsKey("streamType") && options.containsKey("volume")) {
            int flags = AudioManager.FLAG_SHOW_UI;

            if(options.containsKey("showUI") && !options.getBoolean("showUI"))
                flags ^= AudioManager.FLAG_SHOW_UI;
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

