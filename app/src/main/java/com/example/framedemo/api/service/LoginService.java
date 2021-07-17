package com.example.framedemo.api.service;

import com.example.framedemo.api.ApiResponse;
import com.example.framedemo.model.LoginModel;

import java.util.Map;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LoginService {

    @POST("/login")
    Single<ApiResponse<LoginModel>> login(@Body Map<String, Object> params);

    @POST("/upload")
    @Multipart
    Single<String> upload(@Part MultipartBody.Part token, @Part MultipartBody.Part file);
}
