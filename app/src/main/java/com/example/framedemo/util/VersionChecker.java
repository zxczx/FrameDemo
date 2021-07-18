package com.example.framedemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import com.example.framedemo.R;
import com.example.framedemo.api.Config;
import com.example.framedemo.common.firebase.FrameFirebaseRemoteConfig;
import com.example.framedemo.model.NewVersionInfo;
import com.example.framedemo.util.sp.FrameMMkvImpl;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.json.JSONObject;

public class VersionChecker {

    private static final String PREF_KEY_FIRST_INSTALL_VERSION_CODE = "VersionChecker_first_install_version_code";
    private static final String PREF_KEY_PREV_VERSION_CODE = "VersionChecker_prev_version_code";
    private static final String PREF_KEY_CURRENT_VERSION_CODE = "VersionChecker_current_version_code";
    public static final String PREF_KEY_SHOW_WHATS_NEW = "VersionChecker_show_whats_new";
    private static final String PREF_KEY_BOOLEAN_FIRST_INSTALL = "boolean_first_install";


    public static final String INT_PREV_APP_VERSION_CODE_IN_STOR = "int_prev_app_version_code";
    public static final String INT_APP_VERSION_CODE_IN_STORE = "int_versioncode";
    public static final String STR_APP_VERSION_NAME_IN_STORE = "str_versionname";
    public static final String STR_APP_WHAT_NEW_CONTENT = "str_whatsnew";
    public static final String STR_APP_FORCE_UPGRADE = "force_upgrade";

    public static final String PREF_KEY_INSTALL_DAY_START_TIME = "pref_key_install_day_start_time";
    public static final String PREF_KEY_INSTALL_TIME = "pref_key_install_time";

    public static final int UNINIT_VERSION_CODE = -1;

    public static void updateVersionChecker(Context ctx) {
        FrameMMkvImpl sp = FrameMMkvImpl.getFrameMMkvImpl().loadConfig();

        // this package version code
        int versionCode = getVersionCode(ctx);

        // version code get from prefs
        int currentCode = sp.getInt(PREF_KEY_CURRENT_VERSION_CODE, UNINIT_VERSION_CODE);

        if (sp.getLong(PREF_KEY_INSTALL_DAY_START_TIME, Config.INVALID) == Config.INVALID) {
            long getFirstDayTime = TimeUtil.getStartTimeInMillisOfDay();
            if (Math.abs(getFirstDayTime - System.currentTimeMillis()) < Config.ONE_DAY) {
                sp.putLong(PREF_KEY_INSTALL_DAY_START_TIME, getFirstDayTime);
            }
        }

        if (sp.getLong(PREF_KEY_INSTALL_TIME, Config.INVALID) == Config.INVALID) {
            sp.putLong(PREF_KEY_INSTALL_TIME, System.currentTimeMillis());
        }

        if (currentCode == UNINIT_VERSION_CODE) {
            // version info not init yet. It is first install
            sp.putBoolean(PREF_KEY_BOOLEAN_FIRST_INSTALL, true);
            sp.putInt(PREF_KEY_CURRENT_VERSION_CODE, versionCode);
            sp.putInt(PREF_KEY_FIRST_INSTALL_VERSION_CODE, versionCode);
            sp.putInt(PREF_KEY_PREV_VERSION_CODE, versionCode);
            return;
        }

        if (versionCode != currentCode) {
            // update from previous version , so update version info
            sp.putBoolean(PREF_KEY_BOOLEAN_FIRST_INSTALL, false);
            sp.putInt(PREF_KEY_PREV_VERSION_CODE, currentCode);
            sp.putInt(PREF_KEY_CURRENT_VERSION_CODE, versionCode);
            // for judge is show release notes
            sp.putBoolean(PREF_KEY_SHOW_WHATS_NEW, true);
            sp.putBoolean(Config.PREF_SHOW_UPDATE_SUMMARY, true);
            return;
        }

        // versionCode == currentCode
        // version info has been updated, just return
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {

        }
        return UNINIT_VERSION_CODE;
    }

    public static int getFirstInstallVersion(Context ctx) {
        FrameMMkvImpl sp = FrameMMkvImpl.getFrameMMkvImpl().loadConfig();
        return sp.getInt(PREF_KEY_FIRST_INSTALL_VERSION_CODE, UNINIT_VERSION_CODE);
    }

    public static int getPrevInstallVersion(Context ctx) {
        FrameMMkvImpl sp = FrameMMkvImpl.getFrameMMkvImpl().loadConfig();
        return sp.getInt(PREF_KEY_PREV_VERSION_CODE, UNINIT_VERSION_CODE);
    }

    public static NewVersionInfo getNewVersionInStore() {
        String versionInfo = "";
        NewVersionInfo newVersionInfo = null;
        try {
            FrameFirebaseRemoteConfig remoteConfig = FrameFirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfig firebaseRemoteConfig = remoteConfig.getRemoteConfigRef();
            versionInfo = firebaseRemoteConfig.getString(FrameFirebaseRemoteConfig.STRING_APP_VERSION_CODE_IN_STORE);
//            versionInfo = "{\"int_versioncode\":\"207\",\"str_versionname\":\"2.0.7\"," +
//                    "\"str_whatsnew\":\"Upgrade to the latest version to enjoy more cool features\",\"force_upgrade\":true}";
            if (!TextUtils.isEmpty(versionInfo)) {
                String str = new String(versionInfo);
                JSONObject jsonObject = new JSONObject(str);
                int code = jsonObject.getInt(INT_APP_VERSION_CODE_IN_STORE);
                String versionName = jsonObject.getString(STR_APP_VERSION_NAME_IN_STORE);
                String whatnew = jsonObject.getString(STR_APP_WHAT_NEW_CONTENT);

                newVersionInfo = new NewVersionInfo();
                try {
                    boolean forceUpgrade = jsonObject.getBoolean(STR_APP_FORCE_UPGRADE);
                    newVersionInfo.setForceUpgrade(forceUpgrade);
                } catch (Exception ex) {
                }
                newVersionInfo.setVersionCode(code);
                newVersionInfo.setVersionName(versionName);
                newVersionInfo.setWhatsNew(whatnew);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newVersionInfo;
    }

    public static boolean hasNewVersionInStore(Context context) {
        boolean hasNewVersion = false;
        FrameMMkvImpl sp = FrameMMkvImpl.getFrameMMkvImpl().loadConfig();

        try {
            NewVersionInfo newVersionInfo = getNewVersionInStore();
            int versionCode = 0;
            int curAppVersionCode = getVersionCode(context);
            if (newVersionInfo != null) {
                versionCode = newVersionInfo.getVersionCode();
            } else {
                versionCode = curAppVersionCode;
            }


            if (versionCode > curAppVersionCode) {
                hasNewVersion = true;
                int prveVersionCode = sp.getInt(INT_PREV_APP_VERSION_CODE_IN_STOR, curAppVersionCode);
                if (versionCode > prveVersionCode || newVersionInfo.isForceUpgrade()) {
                    sp.putBoolean(Config.IS_SHOW_UPDATE_VERSION_DIALOG, true);
                    sp.putInt(VersionChecker.INT_PREV_APP_VERSION_CODE_IN_STOR, versionCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hasNewVersion = false;
        }
        return hasNewVersion;
    }

    public static void showUpdatedVersionDialog(final Activity activity) {

        NewVersionInfo newVersionInfo = getNewVersionInStore();
        FrameMMkvImpl sp = FrameMMkvImpl.getFrameMMkvImpl().loadConfig();
        if (sp.getBoolean(Config.IS_SHOW_UPDATE_VERSION_DIALOG, true)) {
            if (newVersionInfo != null) {
                View view = LayoutInflater.from(activity).inflate(R.layout.update_new_version_dialog, null);
                TextView whatsNewTv = (TextView) view.findViewById(R.id.update_version_content);
                String versionInfo = "";
                if (!TextUtils.isEmpty(newVersionInfo.getWhatsNew())) {
                    String[] str = newVersionInfo.getWhatsNew().split(";");
                    for (int i = 0; i < str.length; i++) {
                        versionInfo = versionInfo + str[i] + "\n";
                    }

                }
                final boolean forceUpgrade = newVersionInfo.isForceUpgrade();
                whatsNewTv.setText(versionInfo);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setView(view)
                        .setPositiveButton(R.string.update_buttontext, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PromotionManager.searchAppOnPlay(activity, activity.getPackageName());
                                if (!forceUpgrade) {
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (forceUpgrade) {
                                    activity.finish();
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && forceUpgrade) {
                            return true;
                        }
                        return false;
                    }
                });
                if (!ActivityUtil.isActivityDestroyed(activity)) {
                    dialog.show();
                    final Resources resources = activity.getResources();
                    final Button cancelButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    if (cancelButton != null) {
                        cancelButton.setTextColor(resources.getColor(R.color.contact_picker_button_text_color));
                    }
                    final Button addButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    if (addButton != null) {
                        addButton.setTextColor(resources.getColor(R.color.contact_picker_button_text_color));
                    }
                }
            }
        }
    }

    public static long getFirstInstallDayTime() {
        FrameMMkvImpl sp = FrameMMkvImpl.getFrameMMkvImpl().loadConfig();
        long startTime = sp.getLong(PREF_KEY_INSTALL_DAY_START_TIME, Config.INVALID);
        if (startTime == Config.INVALID) {
            startTime = TimeUtil.getStartTimeInMillisOfDay();
            sp.putLong(PREF_KEY_INSTALL_DAY_START_TIME, startTime);
        }
        return startTime;
    }

    public static long getFirstInstallTime() {
        FrameMMkvImpl sp = FrameMMkvImpl.getFrameMMkvImpl().loadConfig();
        long currentTime = sp.getLong(PREF_KEY_INSTALL_TIME, Config.INVALID);
        if (currentTime == Config.INVALID) {
            currentTime = System.currentTimeMillis();
            sp.putLong(PREF_KEY_INSTALL_TIME, System.currentTimeMillis());
        }
        return currentTime;
    }


    public static boolean isFirstInstall(Context context) {
        FrameMMkvImpl sp = FrameMMkvImpl.getFrameMMkvImpl().loadConfig();
        return sp.getBoolean(PREF_KEY_BOOLEAN_FIRST_INSTALL, true);
    }
}