package com.bootx.app.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.Window
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date


object CommonUtils {

    fun toast(context: Context,msg: String) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
    }

    fun getDayInfo(time: Long): String{
        var now = Date().time
        var t1 = (now-time)/1000
        if(t1<60){
            return "一分钟内"
        }
        if(t1<60*60){
            return "一小时内"
        }
        if(t1<60*60*12){
            return "12小时内"
        }
        if(t1<60*60*24){
            return "一天内"
        }
        return "1天前"
    }
    fun drawable2Bitmap(context: Context,drawable: Drawable): Bitmap{
        return drawable.toBitmap()
    }
    fun res2Drawable(context: Context,resId: Int): Drawable{
        return context.resources.getDrawable(resId)
    }
    fun bitmap2Drawable(context: Context,bitmap: Bitmap): Drawable{
        return BitmapDrawable(context.resources,bitmap)
    }

    fun drawable2Base64(drawable: Drawable?): String{
        if(drawable==null){
            return "";
        }
        val bitMap = drawable.toBitmap()
        val baos = ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val bytes = baos.toByteArray()
        return Base64.getEncoder().encodeToString(bytes)
    }


    fun copy(context: Context,value: String){
        val manager: ClipboardManager = context.getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager
        val mClipData =
            ClipData.newPlainText("Label", value)
        manager.setPrimaryClip(mClipData)
        toast(context,"复制成功")
    }

    fun getToken(context: Context): String{
       return SharedPreferencesUtils(context).get("token")
    }

    fun formatDate(date: Date,format: String): String{
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.format(date)
    }

    @Composable
    fun HideStatusBar(window: Window){
        /*// 隐藏状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val systemUiController = rememberSystemUiController()
        systemUiController.isStatusBarVisible = true // 隐藏状态栏
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, // 设置状态栏透明
            darkIcons = false // 如果状态栏背景为深色，请设置为 true
        )*/
    }

    @Composable
    fun ShowStatus(window: Window){
       /* // 隐藏状态栏
        WindowCompat.setDecorFitsSystemWindows(window, true)
        val systemUiController = rememberSystemUiController()
        systemUiController.isStatusBarVisible = false*/
    }
}