package com.bootx.myapplication

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun SearchScreen() {
    val context = LocalContext.current
    var keywords by remember {
        mutableStateOf<String>("")
    }
    var showDialog by remember {
        mutableStateOf<Boolean>(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }, title = {
                TextField(singleLine = true, value = keywords, onValueChange = {
                    keywords = it
                }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ), keyboardActions = KeyboardActions(
                    onSearch = {
                        Log.e("SearchScreen", "SearchScreen: $keywords")
                    },
                ), trailingIcon = {
                    if (keywords.isNotBlank()) {
                        Icon(modifier = Modifier.clickable {
                            keywords = ""
                        }, imageVector = Icons.Default.Close, contentDescription = "")
                    }
                })
            }, actions = {
                IconButton(onClick = {
                    showDialog = true
                }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "")
                }
            })
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            val filters = listOf(
                "Washer/Dryer", "Ramp access", "Garden", "Cats OK", "Dogs OK", "Smoke-free"
            )
            FlowRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { title ->
                    Card(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .combinedClickable(
                                onClick = {
                                    keywords=title
                                },
                                onLongClick = {
                                   keywords=title+title+title
                                },
                            ),
                        shape = RoundedCornerShape(4.dp)

                    ) {
                        Text(text = title, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            }, modifier = Modifier
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "说明",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "1.对当前搜索有结果有操作之后，才会加入历史记录",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
                Text(
                    text = "2.当输入框有文字时，返回键会清空文字，再次返回才会返回到上一界面",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
                Text(
                    text = "3.长按可删除某一搜索历史",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    showDialog = false
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "关闭")
                }
            }
        }
    }

}
