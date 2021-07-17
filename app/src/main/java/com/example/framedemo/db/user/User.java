package com.example.framedemo.db.user;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_user")
public class User implements Parcelable {

    @NonNull
    @PrimaryKey
    public String uid;

    public String area;

    public String phone;

    @ColumnInfo(name = "date_created")
    public String dateCreated;

    @ColumnInfo(name = "nick_name")
    public String nickName;

    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;

    public User(@NonNull String uid) {
        this.uid = uid;
    }

    protected User(Parcel in) {
        uid = in.readString();
        area = in.readString();
        phone = in.readString();
        dateCreated = in.readString();
        nickName = in.readString();
        avatarUrl = in.readString();
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(area);
        dest.writeString(phone);
        dest.writeString(dateCreated);
        dest.writeString(nickName);
        dest.writeString(avatarUrl);
    }
}
