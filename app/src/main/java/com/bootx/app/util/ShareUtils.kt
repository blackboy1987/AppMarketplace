package com.bootx.app.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import com.bootx.app.entity.AppInfo


object ShareUtils {

   fun getShareAppList(context: Context):List<AppInfo>{
       val shareAppInfos = mutableListOf<AppInfo>()
       val packageManager = context.getPackageManager()
       val resolveInfos: List<ResolveInfo> = getShareApps(context);
       if (resolveInfos.isEmpty()) {
           return shareAppInfos
       } else {

           for (resolveInfo in resolveInfos) {
               val appInfo = AppInfo()
               appInfo.packageName=resolveInfo.activityInfo.packageName
               appInfo.appName=resolveInfo.activityInfo.name
               appInfo.appName=resolveInfo.loadLabel(packageManager).toString()
               appInfo.appIcon = resolveInfo.loadIcon(packageManager)
               shareAppInfos.add(appInfo)
           }
       }


       return shareAppInfos
   }

    fun getShareApps(context: Context): List<ResolveInfo> {
        val intent = Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.type = "*/*";
        val pManager = context.packageManager;
        return pManager.queryIntentActivities(intent,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }

    fun shareText(context: Context,extraText: String) {
        var intent = Intent(Intent.ACTION_SEND);
        intent.type = "text/plain";
        intent.putExtra(Intent.EXTRA_TEXT, extraText)
        context.startActivity(
            Intent.createChooser(intent, "发送到..."))
    }
}