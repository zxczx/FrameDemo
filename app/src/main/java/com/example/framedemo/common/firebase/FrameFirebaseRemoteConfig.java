package com.example.framedemo.common.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.framedemo.BuildConfig;
import com.example.framedemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;


/**
 * Created by mengzhao on 16/5/30.
 */
public class FrameFirebaseRemoteConfig {

    private static final String TAG = "FirebaseRemoteConfig";

    // get app version code config
    public static final String STRING_APP_VERSION_CODE_IN_STORE = "json_app_version_in_store";

    private static final long CACHE_EXPIRATION = 3600; // 1 HOUR

    //短信验证部分
    public static final String LONG_VERIFICATION_SOLUTION_TYPE = "long_verification_solution_type";
    public static final String LONG_FIREBASE_VERIFY_LIMIT = "long_firebase_verify_limit";
    public static final String LONG_TWILIO_VERIFY_LIMIT = "long_twilio_verify_limit";

    public static final String KEY_CALL_FLASH_DELAY = "key_call_flash_delay";
    public static final String KEY_CALL_FLASH_SWITCH = "key_call_flash_switch";

    private static FrameFirebaseRemoteConfig sInstance = null;


    private final FirebaseRemoteConfig mFirebaseRemoteConfig;

    public static FrameFirebaseRemoteConfig getInstance() {
        if (sInstance == null) {
            sInstance = new FrameFirebaseRemoteConfig();
        }

        return sInstance;
    }

    private FrameFirebaseRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Create Remote Config Setting to enable developer mode.
        // Fetching configs from the server is normally limited to 5 requests per hour.
        // Enabling developer mode allows many more requests to be made per hour, so developers
        // can test different config values during development.
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        // set default values, edit res/xml/firebase_remote_config_defaults.xml
        mFirebaseRemoteConfig.setDefaults(R.xml.firebase_remote_config_defaults);

        fetchConfig();
    }

    /**
     * Fetch remote config from server.
     */
    public void fetchConfig() {

        Log.d(TAG, "fetchConfig()");

        // cacheExpirationSeconds is set to CACHE_EXPIRATION here, indicating that any previously
        // fetched and cached config would be considered expired because it would have been fetched
        // more than cacheExpiration seconds ago. Thus the next fetch would go to the server unless
        // throttling is in progress. The default expiration duration is 43200 (12 hours).
        mFirebaseRemoteConfig.fetch(CACHE_EXPIRATION)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Remote Config Fetch Succeeded");
                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Log.d(TAG, "Remote Config Fetch failed");
                        }
                    }
                });
    }


    public FirebaseRemoteConfig getRemoteConfigRef() {
        return mFirebaseRemoteConfig;
    }
}
