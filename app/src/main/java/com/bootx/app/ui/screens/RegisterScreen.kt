package com.bootx.app.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bootx.app.ui.components.MyInput1
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.ui.theme.fontSize12
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.app.viewmodel.LoginViewModel
import com.bootx.app.viewmodel.RegisterViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel = viewModel(),
) {
    val context = LocalContext.current
    CommonUtils.HideStatusBar((context as Activity).window)
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId) / 2
        } else {
            0
        }
    }
    val statusBarHeight = getStatusBarHeight(context)
    val coroutineScope = rememberCoroutineScope()
    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var code by remember {
        mutableStateOf("")
    }
    var spreadMemberUsername by remember {
        mutableStateOf("")
    }
    fun isEmail(email: String): Boolean {
        val str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        val p = Pattern.compile(str);
        val m = p.matcher(email)
        return m.matches()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xffe0aec9),
                        Color(0xffe0aec9)
                    )
                )
            )
            .padding(top = statusBarHeight.dp)
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (text1, text2, logo) = createRefs()
            Box(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .constrainAs(text1) {
                    top.linkTo(parent.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                }) {

            }
            Card(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors().copy(
                    containerColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(text2) {
                        top.linkTo(text1.bottom)
                    }) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        shape = RoundedCornerShape(0.dp),
                        colors = CardDefaults.cardColors().copy(
                            containerColor = Color.White,
                        ),
                    ) {
                        Spacer(modifier = Modifier.height(48.dp))
                        Text(text = "欢迎使用", color = Color(0xFF767676), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                        MyInput1(placeholder="请输入用户名",value = username,onValueChange={
                            username=it
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                        MyInput1(placeholder="请输入密码",value = password,onValueChange={
                            password=it
                        }, keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),visualTransformation = PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        MyInput1(placeholder="填写邀请码注册可获得100积分",value = spreadMemberUsername,onValueChange={
                            spreadMemberUsername=it
                        })
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(enabled = !registerViewModel.loading , onClick = {
                            if(username.isBlank()){
                                CommonUtils.toast(context,"请输入用户名")
                                return@Button
                            }
                            if(password.isBlank()){
                                CommonUtils.toast(context,"请输入密码")
                                return@Button
                            }
                            coroutineScope.launch {
                                registerViewModel.register(context,username,email,code,password,spreadMemberUsername)
                                val sharedPreferencesUtils = SharedPreferencesUtils(context)
                                sharedPreferencesUtils.set("token", registerViewModel.data.token)
                                navController.navigate(Destinations.MainFrame.route+"/0")
                            }
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "注册")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "已有账号，去登录", modifier = Modifier.clickable {
                                navController.navigate(Destinations.LoginFrame.route)
                            }, color = Color(0xff7e7e7e), fontSize= fontSize12)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .constrainAs(logo) {
                        top.linkTo(text1.bottom, margin = (-40).dp)
                        start.linkTo(parent.start, margin = screenWidth / 2 - (40.dp))
                    },
                model = "https://bootxyysc.oss-cn-hangzhou.aliyuncs.com/logo.png",
                contentDescription = ""
            )
        }
    }
}

