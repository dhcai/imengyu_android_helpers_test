package com.imengyu.android_helpers.model;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.PermissionChecker;

import com.imengyu.android_helpers.model.entity.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 手机视频查询模型
 */
public class VideoModel {


    private static final String TAG = "VideoModel";
    public static VideoModel instance;
    private ArrayList<Video> videos = null;

    private VideoModel() {
        videos = new ArrayList<>();
    }
    public ArrayList<Video> getItems() {
        return videos;
    }

    public static VideoModel getInstance() {
        if (null == instance) {
            synchronized (VideoModel.class) {
                if (null == instance) {
                    instance = new VideoModel();
                }
            }
        }
        return instance;
    }

    public interface CallBack {
        void onVideoWorkedCallBack();
    }



    public volatile boolean canRun = true;

    /**
     * 专辑查询
     *
     * @param context  调用查询方法的context
     * @param callBack 查询完成后的回调
     */
    public void query(Context context, final CallBack callBack) {
        final Context appCxt = context.getApplicationContext();
        if (PermissionChecker.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            if (null != callBack) callBack.onVideoWorkedCallBack();
            return;
        }
        canRun = true;
        new Thread(() -> {
            initAlbum(appCxt);
            if (null != callBack) callBack.onVideoWorkedCallBack();
        }).start();
    }

    /**
     * 终止查询
     */
    public void stopQuery() {
        canRun = false;
    }

    private synchronized void initAlbum(Context context) {
        videos.clear();

        //查询媒体库，获取系统中的视频

        Uri uri = MediaStore.Video.Media.getContentUri("external");

        String[] projections = {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,//名称
            MediaStore.Video.Media.DURATION,//时长
            MediaStore.Video.Media.SIZE,//大小
            MediaStore.Video.Media.DATA,//路径
            MediaStore.Video.Media.DATE_MODIFIED, //日期
        };

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, projections,null,null,null);

        while(cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            long duration = cursor.getLong(2);
            long size = cursor.getLong(3);
            String path = cursor.getString(4);
            long modified_date = cursor.getLong(5);

            if (duration > 0 && new File(path).exists())
                videos.add(new Video(id, name, duration, size, path, modified_date));
        }
        cursor.close();

        Collections.sort(videos, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                return -Long.compare(o1.date, o2.date);
            }
        });
    }
}
