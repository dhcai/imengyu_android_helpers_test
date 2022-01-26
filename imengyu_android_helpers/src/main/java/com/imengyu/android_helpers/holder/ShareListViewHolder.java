package com.imengyu.android_helpers.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imengyu.android_helpers.R;

public class ShareListViewHolder extends RecyclerView.ViewHolder {

    public ShareListViewHolder(View v) {
        super(v);
        image = v.findViewById(R.id.image);
        text = v.findViewById(R.id.text);
    }

    public TextView text;
    public ImageView image;
}
