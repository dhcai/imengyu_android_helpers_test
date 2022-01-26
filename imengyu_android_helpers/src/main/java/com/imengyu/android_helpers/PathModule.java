package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.PathUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

public class PathModule extends WXModule {
    @Keep
    @UniJSMethod
    public String getDataPath() {
        return PathUtils.getDataPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppDataPath() {
        return PathUtils.getExternalAppDataPath();
    }
    @Keep
    @UniJSMethod
    public String getRootPath() {
        return PathUtils.getRootPath();
    }
    @Keep
    @UniJSMethod
    public String getInternalAppDataPath() {
        return PathUtils.getInternalAppDataPath();
    }
    @Keep
    @UniJSMethod
    public String getDownloadCachePath() {
        return PathUtils.getDownloadCachePath();
    }
    @Keep
    @UniJSMethod
    public String getInternalAppCodeCacheDir() {
        return PathUtils.getInternalAppCodeCacheDir();
    }
    @Keep
    @UniJSMethod
    public String getInternalAppCachePath() {
        return PathUtils.getInternalAppCachePath();
    }
    @Keep
    @UniJSMethod
    public String getInternalAppDbsPath() {
        return PathUtils.getInternalAppDbsPath();
    }
    @Keep
    @UniJSMethod
    public String getInternalAppFilesPath() {
        return PathUtils.getInternalAppFilesPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalMusicPath() {
        return PathUtils.getExternalMusicPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalPodcastsPath() {
        return PathUtils.getExternalPodcastsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalStoragePath() {
        return PathUtils.getExternalStoragePath();
    }
    @Keep
    @UniJSMethod
    public String getExternalRingtonesPath() {
        return PathUtils.getExternalRingtonesPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAlarmsPath() {
        return PathUtils.getExternalAlarmsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalNotificationsPath() {
        return PathUtils.getExternalNotificationsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalPicturesPath() {
        return PathUtils.getExternalPicturesPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalMoviesPath() {
        return PathUtils.getExternalMoviesPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalDownloadsPath() {
        return PathUtils.getExternalDownloadsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalDcimPath() {
        return PathUtils.getExternalDcimPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalDocumentsPath() {
        return PathUtils.getExternalDocumentsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppFilesPath() {
        return PathUtils.getExternalAppFilesPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppCachePath() {
        return PathUtils.getExternalAppCachePath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppMusicPath() {
        return PathUtils.getExternalAppMusicPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppPodcastsPath() {
        return PathUtils.getExternalAppPodcastsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppRingtonesPath() {
        return PathUtils.getExternalAppRingtonesPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppAlarmsPath() {
        return PathUtils.getExternalAppAlarmsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppNotificationsPath() {
        return PathUtils.getExternalAppNotificationsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppPicturesPath() {
        return PathUtils.getExternalAppPicturesPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppMoviesPath() {
        return PathUtils.getExternalAppMoviesPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppDownloadPath() {
        return PathUtils.getExternalAppDownloadPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppDcimPath() {
        return PathUtils.getExternalAppDcimPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppDocumentsPath() {
        return PathUtils.getExternalAppDocumentsPath();
    }
    @Keep
    @UniJSMethod
    public String getExternalAppObbPath() {
        return PathUtils.getExternalAppObbPath();
    }
    @Keep
    @UniJSMethod
    public String getAppDataPathExternalFirst() {
        return PathUtils.getAppDataPathExternalFirst();
    }
    @Keep
    @UniJSMethod
    public String getFilesPathExternalFirst() {
        return PathUtils.getFilesPathExternalFirst();
    }
    @Keep
    @UniJSMethod
    public String getCachePathExternalFirst() {
        return PathUtils.getCachePathExternalFirst();
    }
}
