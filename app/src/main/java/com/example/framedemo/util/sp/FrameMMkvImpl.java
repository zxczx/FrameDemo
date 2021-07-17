package com.example.framedemo.util.sp;

import android.content.Context;

import com.tencent.mmkv.MMKV;

public class FrameMMkvImpl implements FrameMMkv {
    private static FrameMMkvImpl frameMMkvImpl;
    private Context mContext;
    private MMKV mmkv = null;


    public static FrameMMkvImpl getFrameMMkvImpl() {
        return frameMMkvImpl;
    }

    public FrameMMkvImpl(Context mContext, String mmkvName) {
        this.mContext = mContext;
        if (mmkvName == null || mmkvName.isEmpty()) {
            loadConfig();
        } else {
            loadConfig(mmkvName);
        }
    }

    public static FrameMMkvImpl init(Context context, String mmkvName) {
        if (frameMMkvImpl == null) {
            synchronized (FrameMMkvImpl.class) {
                if (frameMMkvImpl == null) {
                    frameMMkvImpl = new FrameMMkvImpl(context, mmkvName);
                }
            }
        }
        return frameMMkvImpl;
    }

    @Override
    public FrameMMkvImpl loadConfig() {
        MMKV.initialize(mContext);//初始化mmkv
        mmkv = MMKV.mmkvWithID("framePreference", MMKV.MULTI_PROCESS_MODE);
        return this;
    }

    @Override
    public FrameMMkvImpl loadConfig(String mmkvName) {
        MMKV.initialize(mContext);//初始化mmkv
        mmkv = MMKV.mmkvWithID(mmkvName, MMKV.MULTI_PROCESS_MODE);
        return this;
    }

    @Override
    public void putString(String key, String value) {
        mmkv.encode(key, value);
    }

    @Override
    public void putInt(String key, int value) {
        mmkv.encode(key, value);
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        mmkv.encode(key, value);
    }

    @Override
    public void putByte(String key, byte[] value) {
        this.putString(key, String.valueOf(value));
    }

    @Override
    public void putShort(String key, short value) {
        this.putString(key, String.valueOf(value));
    }

    @Override
    public void putLong(String key, long value) {
        mmkv.encode(key, value);
    }

    @Override
    public void putFloat(String key, float value) {
        mmkv.encode(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        this.putString(key, String.valueOf(value));
    }

    @Override
    public void putString(int resID, String value) {
        this.putString(this.mContext.getString(resID), value);
    }

    @Override
    public void putInt(int resID, int value) {
        this.putInt(this.mContext.getString(resID), value);
    }

    @Override
    public void putBoolean(int resID, Boolean value) {
        this.putBoolean(this.mContext.getString(resID), value);
    }

    @Override
    public void putByte(int resID, byte[] value) {
        this.putByte(this.mContext.getString(resID), value);
    }

    @Override
    public void putShort(int resID, short value) {
        this.putShort(this.mContext.getString(resID), value);
    }

    @Override
    public void putLong(int resID, long value) {
        this.putLong(this.mContext.getString(resID), value);
    }

    @Override
    public void putFloat(int resID, float value) {
        this.putFloat(this.mContext.getString(resID), value);
    }

    @Override
    public void putDouble(int resID, double value) {
        this.putDouble(this.mContext.getString(resID), value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return mmkv.decodeString(key, defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return mmkv.decodeInt(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, Boolean defaultValue) {
        return mmkv.decodeBool(key, defaultValue);
    }

    @Override
    public byte[] getByte(String key, byte[] defaultValue) {
        try {
            return this.getString(key, "").getBytes();
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    @Override
    public short getShort(String key, Short defaultValue) {
        try {
            return Short.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    @Override
    public long getLong(String key, Long defaultValue) {
        return mmkv.decodeLong(key, defaultValue);
    }

    @Override
    public float getFloat(String key, Float defaultValue) {
        return mmkv.decodeFloat(key, defaultValue);
    }

    @Override
    public double getDouble(String key, Double defaultValue) {
        try {
            return Double.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    @Override
    public String getString(int resID, String defaultValue) {
        return this.getString(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public int getInt(int resID, int defaultValue) {
        return this.getInt(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public boolean getBoolean(int resID, Boolean defaultValue) {
        return this.getBoolean(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public byte[] getByte(int resID, byte[] defaultValue) {
        return this.getByte(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public short getShort(int resID, Short defaultValue) {
        return this.getShort(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public long getLong(int resID, Long defaultValue) {
        return this.getLong(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public float getFloat(int resID, Float defaultValue) {
        return this.getFloat(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public double getDouble(int resID, Double defaultValue) {
        return this.getDouble(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public void remove(String key) {
        mmkv.removeValueForKey(key);
    }

    @Override
    public void remove(String... keys) {
        String[] var2 = keys;
        int var3 = keys.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            this.remove(key);
        }

    }

    @Override
    public void clear() {
        mmkv.clearAll();
    }
}
