package com.example.framedemo.common.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import timber.log.Timber;


public class FrameFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FBInstanceIDService";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        Timber.d("Refreshed token: " + fcmToken);
    }
}
