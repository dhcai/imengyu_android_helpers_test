package com.imengyu.android_helpers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imengyu.android_helpers.holder.ShareListViewHolder;
import com.imengyu.android_helpers.model.ShareDialogItem;

import java.util.List;

public class ShareListListAdapter extends RecyclerView.Adapter<ShareListViewHolder> {

    private final int layoutId;
    private final List<ShareDialogItem> list;
    private AdapterView.OnItemClickListener listOnItemClickListener = null;

    public ShareListListAdapter(int layoutId, List<ShareDialogItem> list) {
        super();
        this.layoutId = layoutId;
        this.list = list;
    }

    public void setListOnItemClickListener(AdapterView.OnItemClickListener listOnItemClickListener) {
        this.listOnItemClickListener = listOnItemClickListener;
    }

    @NonNull
    @Override
    public ShareListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ShareListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShareListViewHolder viewHolder, int position) {
        final ShareDialogItem item = list.get(position);

        viewHolder.itemView.setOnClickListener((v) -> {
            if(listOnItemClickListener != null)
                listOnItemClickListener.onItemClick(null, v, position, 0);
        });

        viewHolder.image.setImageResource(item.iconResId);
        viewHolder.text.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
