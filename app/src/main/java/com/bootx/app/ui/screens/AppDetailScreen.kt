package com.bootx.app.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.SoftIcon6
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.util.ShareUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.app.viewmodel.SoftViewModel
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class, ExperimentalLayoutApi::class
)
@Composable
fun AppDetailScreen(
    navController: NavHostController,
    id: String,
    softViewModel: SoftViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var loading by remember {
        mutableStateOf(false)
    }
    var point by remember {
        mutableIntStateOf(1)
    }
    var memo by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        softViewModel.detail(context, SharedPreferencesUtils(context).get("token"), id)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { TopBarTitle(text = softViewModel.softDetail.name) },
            navigationIcon = {
                LeftIcon {
                    navController.popBackStack()
                }
            },
        )
    }, bottomBar = {
        BottomAppBar {
            TextButton(onClick = { /*TODO*/ }) {
                Column(modifier = Modifier.clickable {
                    coroutineScope.launch {
                        state.show()
                    }
                }) {
                    Icon(imageVector = Icons.Filled.CurrencyBitcoin, contentDescription = "")
                    Text(text = "投币")
                }
            }
            Button(modifier = Modifier.weight(1.0f), onClick = { /*TODO*/ }) {
                Text(text = "下载")
            }
            TextButton(onClick = {
                val shareAppList = ShareUtils.getShareAppList(context)
                Log.e("shareAppList", "AppDetailScreen: ${shareAppList.toString()}")
            }) {
                Column(modifier = Modifier.clickable {
                    ShareUtils.shareText(context, "abc")
                }) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                    Text(text = "分享")
                }
            }
        }
    }) {

        Box(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                item {
                    ListItem(headlineContent = {
                        Text(
                            text = softViewModel.softDetail.name,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.primary,
                            overflow = TextOverflow.Ellipsis
                        )
                    }, supportingContent = {
                        Text(
                            text = softViewModel.softDetail.fullName ?: "",
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.secondary,
                            overflow = TextOverflow.Ellipsis
                        )
                    }, leadingContent = {
                        SoftIcon6(url = softViewModel.softDetail.logo)
                    })
                }
            }
        }
    }
}