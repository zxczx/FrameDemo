package com.example.framedemo.model;


public class NewVersionInfo {
    private int versionCode;
    private String versionName;
    private String whatsNew;
    private boolean compulsoryUpgrading;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getWhatsNew() {
        return whatsNew;
    }

    public void setWhatsNew(String whatsNew) {
        this.whatsNew = whatsNew;
    }

    public boolean isForceUpgrade() {
        return compulsoryUpgrading;
    }

    public void setForceUpgrade(boolean forceUpgrade) {
        this.compulsoryUpgrading = forceUpgrade;
    }
}
