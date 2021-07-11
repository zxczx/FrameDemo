package com.example.framedemo;

import android.content.Context;
import android.util.Log;
import com.example.framedemo.api.ApiException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import timber.log.Timber;



public class ErrorHandler {
    public static void handlerError(Context context, Throwable throwable) {

        if (isNetworkError(throwable)) {
            if (throwable instanceof SocketTimeoutException) {
//                Toaster.show(R.string.request_timed_out);
            } else {
//                Toaster.show(R.string.error_network);
            }
        } else if (throwable instanceof ApiException) {

            ApiException apiException = (ApiException) throwable;

        } else {
            Log.e("ErrorHandler", "handlerError: "+throwable.getMessage() );

        }
    }

    public static boolean isNetworkError(Throwable throwable) {
        if (throwable instanceof ApiException) {
            ApiException apiException = (ApiException) throwable;
            return apiException.getCode() == ApiException.NETWORK_NOT_CONNECTED;
        } else if (throwable instanceof UnknownHostException) {
            return true;
        } else {
            return false;
        }
    }
}
