package com.example.framedemo.module;

import android.util.Log;


import com.example.framedemo.api.Config;
import com.example.framedemo.api.RequestInterceptor;
import com.example.framedemo.api.ResponseInterceptor;
import com.example.framedemo.api.service.LoginService;
import com.example.framedemo.file.FileManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Singleton
    @Provides
    public static RequestInterceptor provideRequestInterceptor() {
        return new RequestInterceptor();
    }

    @Singleton
    @Provides
    public static ResponseInterceptor provideResponseInterceptor() {
        return new ResponseInterceptor();
    }


    @Singleton
    @Provides
    public static OkHttpClient provideOkHttpClient(RequestInterceptor requestInterceptor, ResponseInterceptor responseInterceptor) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Cache cache = new Cache(FileManager.getCacheFile(), 1024 * 1024 * 50);
        builder.addInterceptor(requestInterceptor);
        builder.addNetworkInterceptor(responseInterceptor);
        builder.cache(cache);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        return builder.build();
    }

    @Singleton
    @Provides
    public static Retrofit.Builder provideRetrofit(OkHttpClient okHttpClient) {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return builder;
    }

    @Singleton
    @Provides
    public static LoginService provideLoginService(Retrofit.Builder retrofit) {
        retrofit.baseUrl(Config.RequsetPath);
        return retrofit.build().create(LoginService.class);
    }
}
