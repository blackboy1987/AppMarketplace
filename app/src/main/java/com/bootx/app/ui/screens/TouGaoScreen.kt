package com.bootx.app.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bootx.app.entity.CategoryEntity
import com.bootx.app.extension.onScroll
import com.bootx.app.ui.components.LeftIcon
import com.bootx.app.ui.components.TopBarTitle
import com.bootx.app.ui.components.touGao.TouGaoModalBottomSheet
import com.bootx.app.ui.theme.fontSize14
import com.bootx.app.util.CoilImageEngine
import com.bootx.app.util.CommonUtils
import com.bootx.app.util.SharedPreferencesUtils
import com.bootx.app.viewmodel.TouGaoViewModel
import github.leavesczy.matisse.DefaultMediaFilter
import github.leavesczy.matisse.Matisse
import github.leavesczy.matisse.MatisseContract
import github.leavesczy.matisse.MediaResource
import github.leavesczy.matisse.MimeType
import github.leavesczy.matisse.NothingCaptureStrategy
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun TouGaoScreen(
    navController: NavHostController,
    packageName: String,
    touGaoViewModel: TouGaoViewModel = viewModel()
) {
    var imageCount = 9
    val context = LocalContext.current
    var showTopBar by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf("基本信息", "详细信息", "应用基因")
    val quDaoList = listOf("官方版", "国际版", "测试版本", "汉化版")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var category1 by remember {
        mutableStateOf(listOf<CategoryEntity>())
    }


    // 类别
    var categoryId0 by remember {
        mutableStateOf(0)
    }
    // 渠道
    var quDaoIndex by remember { mutableIntStateOf(0) }
    //分区
    var categoryId1 by remember {
        mutableStateOf(0)
    }

    // 应用标题
    var title by remember { mutableStateOf("") }
    // 投稿说明
    var memo by remember { mutableStateOf("") }
    // 应用介绍
    var introduce by remember { mutableStateOf("") }
    // 更新内容
    var updatedContent by remember { mutableStateOf("") }
    // 广告
    var adType0 by remember { mutableIntStateOf(0) }
    // 付费内容
    var adType1 by remember { mutableIntStateOf(0) }
    // 运营方式
    var adType2 by remember { mutableIntStateOf(0) }
    // 闪光点
    var adType3 by remember { mutableIntStateOf(0) }
    // 应用logo
    var appLogo by remember {
        mutableStateOf("")
    }
    // 网盘地址
    var downloadUrl by remember {
        mutableStateOf("")
    }
    // 网盘密码
    var password by remember {
        mutableStateOf("")
    }

    var isOpen by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        touGaoViewModel.categoryList(SharedPreferencesUtils(context).get("token"))
        touGaoViewModel.getAppInfo(context, packageName)
        categoryId0 = touGaoViewModel.categories[0].id
        category1 = touGaoViewModel.categories[0].children
        categoryId1 = category1[0].id

        // 应用标题
        title = touGaoViewModel.appInfo.appName
        // 应用logo
        appLogo = CommonUtils.drawable2Base64(touGaoViewModel.appInfo.appIcon)

    }

    var imageType by remember { mutableIntStateOf(0) }
    val lazyListState = rememberLazyListState()
    lazyListState.onScroll(callback = { index ->
        showTopBar = index > 0
    })
    var list by remember {
        mutableStateOf(listOf<MediaResource>())
    }
    val mediaPickerLauncher =
        rememberLauncherForActivityResult(contract = MatisseContract()) { images: List<MediaResource>? ->
            if (!images.isNullOrEmpty()) {
                val mediaResource = images[0]
                val uri = mediaResource.uri
                val path = mediaResource.path
                val name = mediaResource.name
                val mimeType = mediaResource.mimeType
                list = images
            } else {
                list = listOf()
            }
        }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = {
                    if (showTopBar) {
                        TopBarTitle(text = "${touGaoViewModel.appInfo.appName}")
                    }
                },
                navigationIcon = {
                    LeftIcon {
                        navController.popBackStack()
                    }
                },
                actions = {
                    TextButton(onClick = {
                        isOpen = true
                    }) {
                        Text(text = "添加网盘地址")
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onClick = {
                        coroutineScope.launch {
                            touGaoViewModel.upload(
                                context,
                                title,
                                memo,
                                introduce,
                                updatedContent,
                                adType0,
                                adType1,
                                adType2,
                                adType3,
                                appLogo,
                                categoryId0,
                                categoryId1,
                                quDaoIndex,
                                list,
                                downloadUrl,
                                password,
                            )
                        }
                    }) {
                    Text(text = "投稿")
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            Column {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.padding(8.dp)
                ) {
                    item {
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = touGaoViewModel.appInfo.appName,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = touGaoViewModel.appInfo.packageName,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            leadingContent = {
                                AsyncImage(
                                    modifier = Modifier.size(80.dp),
                                    model = touGaoViewModel.appInfo.appIcon,
                                    contentDescription = ""
                                )
                            }
                        )
                    }
                    item {
                        SecondaryTabRow(
                            divider = @Composable {

                            },
                            selectedTabIndex = selectedTabIndex,
                            modifier = Modifier
                                .focusRestorer()
                                .padding(8.dp),
                            tabs = {
                                tabs.forEachIndexed { index, item ->
                                    Tab(selected = selectedTabIndex == index, onClick = {
                                        selectedTabIndex = index
                                        coroutineScope.launch {
                                            // lazyListState.animateScrollToItem(1)
                                        }
                                    }) {
                                        Text(
                                            text = item,
                                            fontSize = fontSize14,
                                            modifier = Modifier.padding(8.dp),
                                        )
                                    }
                                }
                            }
                        )
                    }
                    if (selectedTabIndex == 0) {
                        item {
                            Text(text = "类别")
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(Color(0xFFf4f4f4))
                                    .padding(8.dp)
                            ) {
                                touGaoViewModel.categories.forEach { item ->
                                    CategoryItem(
                                        text = item.name,
                                        selected = categoryId0 == item.id,
                                        onClick = {
                                            coroutineScope.launch {
                                                categoryId0 = item.id
                                                category1 = item.children
                                                categoryId1 = category1[0].id
                                            }
                                        })
                                }
                            }
                        }
                        item {
                            Text(text = "渠道")
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(Color(0xFFf4f4f4))
                                    .padding(8.dp)
                            ) {
                                quDaoList.forEachIndexed { index, s ->
                                    CategoryItem(
                                        text = s,
                                        selected = quDaoIndex == index,
                                        onClick = {
                                            coroutineScope.launch {
                                                quDaoIndex = index
                                            }
                                        })
                                }
                            }
                        }
                        item {
                            Text(text = "分区")
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(Color(0xFFf4f4f4))
                                    .padding(8.dp)
                            ) {
                                category1.forEach { item ->
                                    CategoryItem(
                                        text = item.name,
                                        selected = categoryId1 == item.id,
                                        onClick = {
                                            coroutineScope.launch {
                                                categoryId1 = item.id
                                            }
                                        })
                                }
                            }
                        }
                    } else if (selectedTabIndex == 1) {
                        item {
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(Color(0xFFf4f4f4))
                                    .padding(8.dp)
                            ) {
                                CategoryItem("竖屏", imageType == 0, onClick = {
                                    coroutineScope.launch {
                                        imageType = 0
                                    }
                                })
                                CategoryItem("横屏", imageType == 1, onClick = {
                                    coroutineScope.launch {
                                        imageType = 1
                                    }
                                })
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            LazyRow() {
                                items(list) { item ->
                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp, vertical = 16.dp)
                                            .clickable {
                                                val matisse = Matisse(
                                                    maxSelectable = 9,
                                                    mediaFilter = DefaultMediaFilter(
                                                        supportedMimeTypes = MimeType.ofImage(),
                                                        selectedResourceUri = list
                                                            .map { item -> item.uri }
                                                            .toSet(),
                                                    ),
                                                    imageEngine = CoilImageEngine(),
                                                    captureStrategy = NothingCaptureStrategy
                                                )
                                                mediaPickerLauncher.launch(matisse)
                                            }
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xfff4f4f4))
                                            .width(162.dp)
                                            .height(288.dp),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        AsyncImage(
                                            contentScale = ContentScale.FillBounds,
                                            model = item.uri,
                                            contentDescription = ""
                                        )
                                    }
                                }
                                if (list.size < imageCount) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .clickable {
                                                    val matisse = Matisse(
                                                        maxSelectable = 9,
                                                        mediaFilter = DefaultMediaFilter(
                                                            supportedMimeTypes = MimeType.ofImage(),
                                                            selectedResourceUri = list
                                                                .map { item -> item.uri }
                                                                .toSet(),
                                                        ),
                                                        imageEngine = CoilImageEngine(),
                                                        captureStrategy = NothingCaptureStrategy
                                                    )
                                                    mediaPickerLauncher.launch(matisse)
                                                }
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color(0xfff4f4f4))
                                                .width(162.dp)
                                                .height(288.dp)
                                                .padding(horizontal = 8.dp, vertical = 16.dp),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(40.dp),
                                                imageVector = Icons.Filled.Add,
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            Text(text = "应用标题")
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = title, onValueChange = { value ->
                                    title = value
                                }
                            )
                        }
                        item {
                            Text(text = "投稿说明")
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = memo,
                                onValueChange = { value ->
                                    memo = value
                                },
                                maxLines = 8,
                                minLines = 8,

                                )
                        }
                        item {
                            Text(text = "应用介绍")
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = introduce,
                                onValueChange = { value ->
                                    introduce = value
                                },
                                maxLines = 8,
                                minLines = 8,

                                )
                        }
                        item {
                            Text(text = "更新内容")
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = updatedContent,
                                onValueChange = { value ->
                                    updatedContent = value
                                },
                                maxLines = 8,
                                minLines = 8,

                                )
                        }
                    } else if (selectedTabIndex == 2) {
                        item {
                            Text(text = "该应用是否包含广告")
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                MyRadio(title="无广告",adType0==0, onClick = {adType0=0})
                                MyRadio(title="少量广告",adType0==1, onClick = {adType0=1})
                                MyRadio(title="超过广告",adType0==2, onClick = {adType0=2})
                            }

                        }
                        item {
                            Text(text = "该应用是否有付费内容")
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                MyRadio(title="完全免费",adType1==0, onClick = {adType1=0})
                                MyRadio(title="会员制",adType1==1, onClick = {adType1=1})
                                MyRadio(title="没钱不给用",adType1==2, onClick = {adType1=2})
                            }
                        }
                        item {
                            Text(text = "该应用的运营方式")
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                MyRadio(title="企业开发",adType2==0, onClick = {adType2=0})
                                MyRadio(title="独立开发",adType2==1, onClick = {adType2=1})
                            }
                        }
                        item {
                            Text(text = "该应用有什么闪光点")
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                MyRadio(title="白嫖",adType3==0, onClick = {adType3=0})
                                MyRadio(title="Material Design",adType3==1, onClick = {adType3=1})
                                MyRadio(title="神作",adType3==2, onClick = {adType3=2})
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(64.dp))
                    }
                }
            }
        }
    }

    if(isOpen){
        TouGaoModalBottomSheet(onClose = {
            isOpen = false
        }){
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp,end = 8.dp, top=16.dp, bottom = 8.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "添加安装包",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )
                Text(text = "分享地址")
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(), value = downloadUrl, onValueChange = { downloadUrl = it })
                Text(text = "密码")
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(), value = password, onValueChange = { password = it })
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        isOpen = false
                    }
                ) {
                    Text("保存")
                }
            }
        }
    }

}

@Composable
fun CategoryItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                start = 0.dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
            .clickable {
                onClick()
            },
        colors = if (selected) CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ) else CardDefaults.cardColors()
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp),
            text = text,
        )
    }
}

@Composable
fun MyRadio(title: String,selected: Boolean=false,onClick: () -> Unit){
    Row(
        Modifier
            .clickable {
                onClick()
            }
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = { onClick() })
        Text(text = title, fontSize = MaterialTheme.typography.titleSmall.fontSize)
    }
}