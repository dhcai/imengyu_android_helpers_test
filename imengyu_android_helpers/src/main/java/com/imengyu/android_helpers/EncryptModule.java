package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.Utils;
import com.imengyu.android_helpers.utils.ClipboardUtils;
import com.imengyu.android_helpers.utils.FileUtils;
import com.taobao.weex.common.WXModule;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class EncryptModule extends WXModule {

    /**
     * 对字符串进行MD5
     * @param content 字符串
     */
    @Keep
    @UniJSMethod
    public String MD5(String content) {
        return EncryptUtils.encryptMD5ToString(content);
    }
    /**
     * 对字符串进行SHA1
     * @param content 字符串
     */
    @Keep
    @UniJSMethod
    public String SHA1(String content) {
        return EncryptUtils.encryptSHA1ToString(content);
    }

    /**
     * 对字符串进行SHA256加密
     * @param content 字符串
     * @param key 密钥
     */
    @Keep
    @UniJSMethod
    public String SHA256(String content, String key) {
        return EncryptUtils.encryptHmacSHA256ToString(content, key);
    }
    /**
     * 对字符串进行SHA512加密
     * @param content 字符串
     * @param key 密钥
     */
    @Keep
    @UniJSMethod
    public String SHA512(String content, String key) {
        return EncryptUtils.encryptHmacSHA512ToString(content, key);
    }
    /**
     * 对文件进行MD5运算
     * @param filePath 文件路径
     */
    @Keep
    @UniJSMethod
    public String MD5File(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            byte[] bytes = EncryptUtils.encryptMD5File(file);
            return new String(bytes, StandardCharsets.UTF_8);
        }
        return "";
    }
}

