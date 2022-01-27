package com.imengyu.android_helpers.adapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.imengyu.android_helpers.R;
import com.imengyu.android_helpers.holder.VideoListViewHolder;
import com.imengyu.android_helpers.model.VideoListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoListListAdapter extends RecyclerView.Adapter<VideoListViewHolder> {

    private final Activity activity;
    private final int layoutId;
    private final List<VideoListItem> list;
    private AdapterView.OnItemClickListener listOnItemClickListener = null;
    private final int itemSize;
    private final ContentResolver contentResolver;

    public VideoListListAdapter(Activity activity, int layoutId, List<VideoListItem> list) {
        super();
        this.activity = activity;
        this.layoutId = layoutId;
        this.list = list;
        Size size = new Size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
        itemSize = size.getWidth() / 4 - 10;
        contentResolver = activity.getApplicationContext().getContentResolver();
    }

    private final List<Integer> changedItems = new ArrayList<>();

    public List<Integer> getChangedItems() {
        return changedItems;
    }

    private void loadItemThumbnail(VideoListItem item, int position) {
        //在背景线程进行缩略图加载
        new Thread(() -> {
            Bitmap videoThumbnail = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                Uri thumbUri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, String.valueOf(item.videoId));
                try {
                    videoThumbnail = contentResolver.loadThumbnail(thumbUri, new Size(256, 256), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                videoThumbnail = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, item.videoId, MediaStore.Images.Thumbnails.MINI_KIND, null);
            }

            if (videoThumbnail != null) {
                item.thumbnail = videoThumbnail;
                item.thumbnailLoading = false;
            } else {
                item.thumbnailLoading = false;
                item.thumbnailFail = true;
            }
            changedItems.add(position);
        }).start();
    }

    public void setListOnItemClickListener(AdapterView.OnItemClickListener listOnItemClickListener) {
        this.listOnItemClickListener = listOnItemClickListener;
    }

    @NonNull
    @Override
    public VideoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        ViewGroup.LayoutParams l = v.getLayoutParams();
        l.width = this.itemSize;
        l.height = this.itemSize;
        v.setLayoutParams(l);
        return new VideoListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListViewHolder viewHolder, int position) {
        final VideoListItem item = list.get(position);

        viewHolder.image_thumbnail.setOnClickListener((v) -> {
            if(listOnItemClickListener != null)
                listOnItemClickListener.onItemClick(null, v, position, 0);
        });

        if (item != null) {
            if (item.isCameraButton)
                viewHolder.image_thumbnail.setImageResource(R.drawable.ic_camera);
            else if (item.thumbnailLoading)
                viewHolder.image_thumbnail.setImageDrawable(null);
            else if (item.thumbnailFail)
                viewHolder.image_thumbnail.setImageResource(R.drawable.ic_image_failed);
            else {
                if (!item.thumbnailLoadingStarted) {
                    item.thumbnailLoadingStarted = true;
                    loadItemThumbnail(item, position);
                } else
                    viewHolder.image_thumbnail.setImageBitmap(item.thumbnail);
            }
            viewHolder.text_time.setText(item.durationString);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
