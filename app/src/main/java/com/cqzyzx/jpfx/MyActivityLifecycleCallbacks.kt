package com.cqzyzx.jpfx
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

class MyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // Do something when activity is created
        Log.e("MyActivityLifecycleCallbacks", "onActivityCreated: ", )
    }

    override fun onActivityStarted(activity: Activity) {
        // Do something when activity is started
        Log.e("MyActivityLifecycleCallbacks", "onActivityStarted: ", )
    }

    override fun onActivityResumed(activity: Activity) {
        // 应用进入前台
        Log.e("MyActivityLifecycleCallbacks", "onActivityResumed: ", )
    }

    override fun onActivityPaused(activity: Activity) {
        // 应用进入后台
        Log.e("MyActivityLifecycleCallbacks", "onActivityPaused: ", )
    }

    override fun onActivityStopped(activity: Activity) {
        // Do something when activity is stopped
        Log.e("MyActivityLifecycleCallbacks", "onActivityStopped: ", )
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Do something when activity's instance state is saved
        Log.e("MyActivityLifecycleCallbacks", "onActivitySaveInstanceState: ", )
    }

    override fun onActivityDestroyed(activity: Activity) {
        // Do something when activity is destroyed
        Log.e("MyActivityLifecycleCallbacks", "onActivityDestroyed: ", )
    }
}