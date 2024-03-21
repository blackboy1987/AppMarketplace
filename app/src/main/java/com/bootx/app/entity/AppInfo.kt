package com.bootx.app.entity

import android.graphics.drawable.Drawable

data class AppInfo(
    var appIcon: Drawable?=null,
    var appName: String="",
    var versionName: String="",
    var packageName: String="",
    var versionCode: String="",
    var appBytes: Long=0L,
    var targetSdkVersion: Int=0,
    var minSdkVersion: Int=0,
    var memo: String="",
    var introduce: String="",
    var updatedContent: String="",
)