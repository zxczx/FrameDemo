package com.example.framedemo.util.sp;

public interface FrameMMkv {
    FrameMMkvImpl loadConfig();

    FrameMMkvImpl loadConfig(String mmkvName);

    void putString(String var1, String var2);

    void putInt(String var1, int var2);

    void putBoolean(String var1, Boolean var2);

    void putByte(String var1, byte[] var2);

    void putShort(String var1, short var2);

    void putLong(String var1, long var2);

    void putFloat(String var1, float var2);

    void putDouble(String var1, double var2);

    void putString(int var1, String var2);

    void putInt(int var1, int var2);

    void putBoolean(int var1, Boolean var2);

    void putByte(int var1, byte[] var2);

    void putShort(int var1, short var2);

    void putLong(int var1, long var2);

    void putFloat(int var1, float var2);

    void putDouble(int var1, double var2);

    String getString(String var1, String var2);

    int getInt(String var1, int var2);

    boolean getBoolean(String var1, Boolean var2);

    byte[] getByte(String var1, byte[] var2);

    short getShort(String var1, Short var2);

    long getLong(String var1, Long var2);

    float getFloat(String var1, Float var2);

    double getDouble(String var1, Double var2);

    String getString(int var1, String var2);

    int getInt(int var1, int var2);

    boolean getBoolean(int var1, Boolean var2);

    byte[] getByte(int var1, byte[] var2);

    short getShort(int var1, Short var2);

    long getLong(int var1, Long var2);

    float getFloat(int var1, Float var2);

    double getDouble(int var1, Double var2);

    void remove(String var1);

    void remove(String... var1);

    void clear();
}
