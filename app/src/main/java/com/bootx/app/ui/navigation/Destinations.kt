package com.bootx.app.ui.navigation

sealed class Destinations(val route: String) {
    //首页
    data object MainFrame : Destinations("MainFrame")
    //首页
    data object HomeFrame : Destinations("HomeFrame")
    //首页
    data object AppFrame : Destinations("AppFrame")
    //首页
    data object MineFrame : Destinations("MineFrame")
    //AppDetailFrame
    data object AppDetailFrame : Destinations("AppDetailFrame")
    //AppDetailFrame
    data object DownloadFrame : Destinations("DownloadFrame")
    //AppDetailFrame
    data object WebViewFrame : Destinations("WebViewFrame")
    data object LoginFrame : Destinations("LoginFrame")
    data object RegisterFrame : Destinations("RegisterFrame")
    data object SearchFrame : Destinations("SearchFrame")
    data object AboutFrame : Destinations("AboutFrame")
    data object TouGaoAppInfoListFrame : Destinations("TouGaoAppInfoListFrame")
    //应用投稿
    data object TouGaoFrame : Destinations("TouGaoFrame")
    //应用投稿

    // 设置
    data object SettingFrame : Destinations("SettingFrame")
    // webview打开的页面
    data object PageFrame : Destinations("PageFrame")



}
