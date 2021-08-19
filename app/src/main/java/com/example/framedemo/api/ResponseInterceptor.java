package com.example.framedemo.api;

import androidx.annotation.NonNull;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 接收拦截器
 */
public class ResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        // 对返回code统一拦截
        if (response != null) {
            if (response.code() != 200) {
                throw new ApiException(response.code(), response.message());
            }
        }
        return response;

    }

}
