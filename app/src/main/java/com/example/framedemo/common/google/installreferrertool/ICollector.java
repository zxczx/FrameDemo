package com.example.framedemo.common.google.installreferrertool;

import android.content.Context;
import android.os.Bundle;

public interface ICollector {
    void collect(Context context, String key);
    void collectWithParams(Context context, String key, Bundle params);
}
