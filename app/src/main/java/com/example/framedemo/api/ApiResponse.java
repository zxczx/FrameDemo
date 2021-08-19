package com.example.framedemo.api;

import com.google.gson.annotations.SerializedName;

/**
 * api返回参数统一实体类（可根据后台统一配置）
 */
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
