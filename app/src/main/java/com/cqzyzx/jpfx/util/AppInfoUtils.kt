package com.cqzyzx.jpfx.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.usage.StorageStats
import android.app.usage.StorageStatsManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import com.cqzyzx.jpfx.entity.AppInfo
import com.kwai.video.player.KsMediaPlayerInitConfig
import java.io.IOException
import java.util.UUID


object AppInfoUtils {

    /**
     * 获取所有安装的App
     */
    fun getAppList(context: Context): List<AppInfo> {
        val appInfos = mutableListOf<AppInfo>()
        val packageManager = context.packageManager
        val installedApplications =
            packageManager.getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES)

        installedApplications.forEach { info ->
            run {
                appInfos.add(getAppInfo(context, info.packageName))
            }
        }
        return appInfos
    }

    /**
     * 获取所有安装的App
     */
    fun getAppList2(context: Context): List<AppInfo> {
        val appInfos = mutableListOf<AppInfo>()
        val packageManager = context.packageManager

        val installedApplications =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        installedApplications.forEach { info ->
            run {
                appInfos.add(getAppInfo(context, info.packageName))
            }
        }
        return appInfos
    }

    /**
     * 判断app是否安装
     */
    fun isInstall(context: Context, packageName: String): Boolean {
        if (packageName.isEmpty()) {
            return false
        }
        val packageManager = context.packageManager
        try {
            val packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return packageInfo != null
        } catch (ignore: Exception) {
        }

        return false
    }

    /**
     * 通过包名获取PackageInfo
     */
    fun getPackInfo(context: Context, packageName: String): PackageInfo? {
        if (packageName.isEmpty()) {
            return null
        }
        val packageManager = context.packageManager
        try {
            return packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        } catch (ignore: Exception) {
        }

        return null
    }

    /**
     * 通过包名获取app信息
     */
    fun getAppInfo(context: Context, packageName: String): AppInfo {
        val appInfo = AppInfo(
            appIcon = null,
            appName = "",
            versionName = "",
            packageName = packageName,
            versionCode = "",
        )
        if (packageName.isEmpty()) {
            return appInfo
        }
        val packageManager = context.packageManager
        try {
            val packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            appInfo.minSdkVersion = packageInfo.applicationInfo.minSdkVersion
            appInfo.targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion
            appInfo.versionName = packageInfo.versionName
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                appInfo.versionCode = packageInfo.longVersionCode.toString()
            } else {
                appInfo.versionCode = packageInfo.versionCode.toString()
            }
            appInfo.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(packageManager)

            // 判断应用是支持32位还是64位
            try {
                val applicationInfo: ApplicationInfo =
                    packageManager.getApplicationInfo(KsMediaPlayerInitConfig.packageName, 0)
                // 检查支持的ABI
                if (applicationInfo.nativeLibraryDir.contains("arm64")) {
                    // 应用支持64位
                    appInfo.app32 = "64位"
                } else if (applicationInfo.nativeLibraryDir.contains("armeabi") || applicationInfo.nativeLibraryDir.contains(
                        "x86"
                    )
                ) {
                    // 应用支持32位
                    appInfo.app32 = "32位"
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }


            val storageStatsManager =
                context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
            val storageManager =
                context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val storageVolumes: List<StorageVolume> = storageManager.getStorageVolumes()

            for (item in storageVolumes) {
                val uuidStr = item.uuid
                var uuid: UUID?
                uuid = if (uuidStr == null) {
                    StorageManager.UUID_DEFAULT
                } else {
                    UUID.fromString(uuidStr)
                }
                val uid: Int = getUid(context, packageName)
                //通过包名获取uid
                var storageStats: StorageStats? = null
                try {
                    storageStats = storageStatsManager.queryStatsForUid(uuid, uid)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                //缓存大小
                storageStats!!.cacheBytes
                //数据大小
                storageStats!!.dataBytes
                //应用大小
                appInfo.appBytes = storageStats!!.appBytes
                //应用的总大小
                appInfo.appBytes =
                    storageStats!!.cacheBytes + storageStats!!.dataBytes + storageStats!!.appBytes
            }


        } catch (ignore: Exception) {
        }

        return appInfo
    }

    private fun getUid(context: Context, packageName: String): Int {
        try {
            return context.packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            ).uid
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return -1
    }


    @SuppressLint("HardwareIds")
    fun getDeviceInfo(activity: Activity): SystemInfo {
        // 获取设备的操作系统版本
        var osVersion = ""
        try {
            osVersion = Build.VERSION.SDK_INT.toString()
        }catch (e: Exception){}

        // 获取手机型号和制造商信息
        var model = ""
        try {
            model = Build.MODEL
        }catch (e: Exception){}

        var manufacturer = ""
        try {
            manufacturer = Build.MANUFACTURER
        }catch (e: Exception){}

        // 获取设备的硬件信息
        var deviceId=""
        try {
            deviceId = (activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
        }catch (e: Exception){}

        var simSerialNumber = ""
        try {
            simSerialNumber = (activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).simSerialNumber
        }catch (e: Exception){}

        return SystemInfo(
            os = osVersion.toString(),
            model = model,
            manufacturer = manufacturer,
            deviceId = deviceId,
            simSerialNumber = simSerialNumber,
        )
    }


    data class SystemInfo(
        val os: String,
        val model: String,
        val manufacturer: String,
        val deviceId: String,
        val simSerialNumber: String,
    )
}