package com.imengyu.android_helpers.model;

import android.graphics.Bitmap;

public class VideoListItem {
    public long videoId;
    public String durationString;
    public String path;
    public Bitmap thumbnail;
    public boolean isCameraButton;
    public boolean thumbnailLoading;
    public boolean thumbnailLoadingStarted;
    public boolean thumbnailFail;
    public boolean thumbnailLoaded = false;
}
