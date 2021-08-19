package com.example.framedemo.api;

/**
 * api请求异常实体类
 */
public class ApiException extends RuntimeException {
    public static final int NETWORK_NOT_CONNECTED = 504;
    private int code;
    private String message;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
