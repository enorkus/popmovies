package com.enorkus.popmovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {

    private String key;

    public Video(String key) {
        this.key = key;
    }

    protected Video(Parcel in) {
        key = in.readString();
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
    }
}
