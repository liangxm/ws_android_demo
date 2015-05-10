package com.lxm.pwhelp;

import android.app.Application;
import com.lxm.pwhelp.utils.LockPatternUtils;

public class App extends Application {
    private static App mInstance;
    private LockPatternUtils mLockPatternUtils;
    
    public static App getInstance() {
        return mInstance;
    }
    
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mLockPatternUtils = new LockPatternUtils(this);
    }
    
    public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }
}