package com.imengyu.android_helpers.model;

import android.app.Activity;

public class ShareDialogItem {
    public Activity activity;
    public Integer iconResId;
    public String title;

    public ShareDialogItem(Activity activity, Integer iconResId, String title) {
        this.activity = activity;
        this.iconResId = iconResId;
        this.title = title;
    }
}
