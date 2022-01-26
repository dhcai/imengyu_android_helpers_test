package com.imengyu.android_helpers.utils;

import androidx.annotation.Keep;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.RomUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

public class RomModule  extends WXModule {
    @Keep
    @UniJSMethod
    public boolean isHuawei() {
        return RomUtils.isHuawei();
    }
    @Keep
    @UniJSMethod
    public boolean isVivo() {
        return RomUtils.isVivo();
    }
    @Keep
    @UniJSMethod
    public boolean isXiaomi() {
        return RomUtils.isXiaomi();
    }
    @Keep
    @UniJSMethod
    public boolean isOppo() {
        return RomUtils.isOppo();
    }
    @Keep
    @UniJSMethod
    public boolean isLeeco() {
        return RomUtils.isLeeco();
    }
    @Keep
    @UniJSMethod
    public boolean is360() {
        return RomUtils.is360();
    }
    @Keep
    @UniJSMethod
    public boolean isZte() {
        return RomUtils.isZte();
    }
    @Keep
    @UniJSMethod
    public boolean isOneplus() {
        return RomUtils.isOneplus();
    }
    @Keep
    @UniJSMethod
    public boolean isNubia() {
        return RomUtils.isNubia();
    }
    @Keep
    @UniJSMethod
    public boolean isCoolpad() {
        return RomUtils.isCoolpad();
    }
    @Keep
    @UniJSMethod
    public boolean isLg() {
        return RomUtils.isLg();
    }
    @Keep
    @UniJSMethod
    public boolean isGoogle() {
        return RomUtils.isGoogle();
    }
    @Keep
    @UniJSMethod
    public boolean isSamsung() {
        return RomUtils.isSamsung();
    }
    @Keep
    @UniJSMethod
    public boolean isMeizu() {
        return RomUtils.isMeizu();
    }
    @Keep
    @UniJSMethod
    public boolean isLenovo() {
        return RomUtils.isLenovo();
    }
    @Keep
    @UniJSMethod
    public boolean isSmartisan() {
        return RomUtils.isSmartisan();
    }
    @Keep
    @UniJSMethod
    public boolean isHtc() {
        return RomUtils.isHtc();
    }
    @Keep
    @UniJSMethod
    public boolean isSony() {
        return RomUtils.isSony();
    }
    @Keep
    @UniJSMethod
    public boolean isGionee() {
        return RomUtils.isGionee();
    }
    @Keep
    @UniJSMethod
    public boolean isMotorola() {
        return RomUtils.isMotorola();
    }
    @Keep
    @UniJSMethod
    public JSONObject getRomInfo() {
        JSONObject o = new JSONObject();
        RomUtils.RomInfo info = RomUtils.getRomInfo();
        o.put("name", info.getName());
        o.put("version", info.getVersion());
        return o;
    }
}
