package com.example.framedemo.api.service;

import com.example.framedemo.api.ApiResponse;
import com.example.framedemo.db.user.User;

import java.util.Map;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 登陆相关接口  其他模块可另建Service
 */
public interface LoginService {

    @POST("/login")
    Single<ApiResponse<User>> login(@Body Map<String, Object> params);

}
