package com.example.framedemo.rx;


import com.example.framedemo.api.ApiException;
import com.example.framedemo.api.ApiResponse;

import io.reactivex.functions.Function;


public class ResponseTransformer <T> implements Function<ApiResponse<T>, T> {


    @Override
    public T apply(ApiResponse<T> apiResponse) throws Exception {
        if (apiResponse.code != 0) {
            throw new ApiException(apiResponse.code, apiResponse.message);
        }
        return apiResponse.data;
    }
}
