package com.imengyu.android_helpers.filepicker.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imengyu.android_helpers.R;

public class FilePickerViewHolder extends RecyclerView.ViewHolder {
    protected RelativeLayout layoutRoot;
    protected ImageView ivType,ivChoose;
    protected TextView tvName;
    protected TextView tvDetail;
    public FilePickerViewHolder(View itemView) {
        super(itemView);
        ivType = (ImageView) itemView.findViewById(R.id.iv_type);
        layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_item_root);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
        ivChoose = (ImageView) itemView.findViewById(R.id.iv_choose);
    }
}
