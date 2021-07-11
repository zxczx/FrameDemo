package com.example.framedemo.api;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {

    @SerializedName("resultCode")
    public int code;
    @SerializedName("resultMessage")
    public String message;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("resultData")
    public T data;

}
