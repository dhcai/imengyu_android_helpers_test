package com.imengyu.android_helpers.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imengyu.android_helpers.R;

public class VideoListViewHolder extends RecyclerView.ViewHolder {

    public VideoListViewHolder(View v) {
        super(v);
        image_thumbnail = v.findViewById(R.id.image_thumbnail);
        text_time = v.findViewById(R.id.text_time);
    }

    public TextView text_time;
    public ImageView image_thumbnail;
}
