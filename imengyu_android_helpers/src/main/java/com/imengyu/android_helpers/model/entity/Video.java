package com.imengyu.android_helpers.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.util.Objects;

/**
 * 视频实体类
 */
@Keep
public class Video implements Parcelable {
    public String name;
    public long duration;
    public long size;
    public String path;
    public long videoId;
    public long date;

    public Video(long id, String name, long duration, long size, String path, long date) {
        this.videoId = id;
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.path = path;
        this.date = date;
    }

    protected Video(Parcel in) {
        videoId = in.readLong();
        name = in.readString();
        duration = in.readLong();
        size = in.readLong();
        path = in.readString();
        date = in.readLong();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }
        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", videoId=" + videoId +
                ", duration=" + duration +
                ", size=" + size +
                ", path='" + path + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return duration == video.duration && size == video.size && Objects.equals(name, video.name) && Objects.equals(path, video.path);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.videoId);
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeLong(this.duration);
        dest.writeLong(this.date);
    }
}
