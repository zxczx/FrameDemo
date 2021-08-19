package com.example.framedemo.common.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * firebase push 必须的实现类
 */
public class FrameFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage == null) {
            return;
        }
        Map<String, String> data = remoteMessage.getData();
        if (data != null && data.size() > 0) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            if (notification != null) {
                FramePushMsgManager.handlePushMsg(this, data, true);
            }
        }
    }
}
