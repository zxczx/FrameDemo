package com.example.framedemo.rx.rxbus;

/**
 * RxBus  消息传递实体类
 */
public class RxBusEvent {
    private String message;
    private int data;

    public RxBusEvent(String message) {
        this.message = message;
    }

    public RxBusEvent(String message, int data) {
        this.message = message;
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
