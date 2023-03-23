package com.eventloggercollectutils.db;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_order_data")
public class OrderDate implements Parcelable {


    public OrderDate() {

    }


    @NonNull
    @PrimaryKey
    private String key;

    private String num;

    private String type;

    private long time = 0;

    private int checkNum = 0;

    private String msg="";

    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    protected OrderDate(Parcel in) {
        key = in.readString();
        num = in.readString();
        type = in.readString();
        time = in.readLong();
        checkNum = in.readInt();
        msg = in.readString();
    }

    public static final Creator<OrderDate> CREATOR = new Creator<OrderDate>() {
        @Override
        public OrderDate createFromParcel(Parcel in) {
            return new OrderDate(in);
        }

        @Override
        public OrderDate[] newArray(int size) {
            return new OrderDate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(num);
        parcel.writeString(type);
        parcel.writeLong(time);
        parcel.writeInt(checkNum);
        parcel.writeString(msg);
    }
}
