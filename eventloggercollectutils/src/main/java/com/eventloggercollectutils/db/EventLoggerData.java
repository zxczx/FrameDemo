package com.eventloggercollectutils.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_event_logger_data")
public class EventLoggerData implements Parcelable {

    @NonNull
    @PrimaryKey
    private String key;
    private String action;
    private int isCheck = 0;
    private int isImportance = 0;

    public EventLoggerData() {

    }

    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public int getIsImportance() {
        return isImportance;
    }

    public void setIsImportance(int isImportance) {
        this.isImportance = isImportance;
    }

    protected EventLoggerData(Parcel in) {
        key = in.readString();
        action = in.readString();
        isCheck = in.readInt();
        isImportance = in.readInt();
    }

    public static final Creator<EventLoggerData> CREATOR = new Creator<EventLoggerData>() {
        @Override
        public EventLoggerData createFromParcel(Parcel in) {
            return new EventLoggerData(in);
        }

        @Override
        public EventLoggerData[] newArray(int size) {
            return new EventLoggerData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(action);
        dest.writeInt(isCheck);
        dest.writeInt(isImportance);
    }
}
