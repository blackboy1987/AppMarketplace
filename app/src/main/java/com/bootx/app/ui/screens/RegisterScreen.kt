package com.bootx.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.ui.components.MyInput1
import com.bootx.app.ui.navigation.Destinations
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.app.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel = viewModel(),
) {
    val context = LocalContext.current
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

    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(text = "欢迎来到",color= Color(0xFFAAAAAA))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "爱尚应用",color= Color(0xFF111111), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "账号注册",color= Color(0xFF111111), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
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
            Text(text = "或者", modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate(Destinations.LoginFrame.route)
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "登录")
            }
        }
    }
}

