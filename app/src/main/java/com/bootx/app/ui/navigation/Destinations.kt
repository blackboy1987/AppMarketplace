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



}
