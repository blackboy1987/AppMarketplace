package com.bootx.app.ui.components

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bootx.app.entity.SoftEntity
import com.bootx.app.ui.navigation.Destinations
import kotlinx.coroutines.launch

@Composable
fun RightIcon(onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() }
    ) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "")
    }
}

@Composable
fun LeftIcon(onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() }
    ) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "")
    }
}

@Composable
fun TopBarTitle(text: String) {
    Text(text = text, fontSize = MaterialTheme.typography.titleMedium.fontSize)
}

@Composable
fun SoftIcon(url: String) {
    AsyncImage(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(8.dp)),
        model = url,
        contentDescription = ""
    )
}

@Composable
fun SoftIcon12(url: String) {
    AsyncImage(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(10.dp)),
        model = url,
        contentDescription = ""
    )
}

@Composable
fun SoftIcon8(url: String) {
    AsyncImage(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape),
        model = url,
        contentDescription = ""
    )
}

@Composable
fun SoftIcon8_8(url: String) {
    AsyncImage(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(8.dp)),
        model = url,
        contentDescription = ""
    )
}

@Composable
fun SoftIcon6(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = Modifier
            .then(modifier)
            .size(60.dp)
            .clip(CircleShape),
        contentScale = ContentScale.FillBounds,
        model = url,
        contentDescription = ""
    )
}

@Composable
fun SoftIcon6_8(url: String) {
    AsyncImage(
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(8.dp)),
        model = url,
        contentDescription = ""
    )
}

@Composable
fun SoftIcon4(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .then(modifier),
        model = url,
        contentDescription = ""
    )
}


@Composable
fun MyInput(value: String, leadingIcon: ImageVector, onChange: (value: String) -> Unit, placeholder: @Composable (() -> Unit)) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        value = value,
        onValueChange = {
            onChange(it)
        },
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = "")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        placeholder = placeholder,
        singleLine = true,
    )
}

@Composable
fun MyPasswordInput(value: String, onChange: (value: String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        value = value,
        onValueChange = {
            onChange(it)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = "")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
    )
}

fun toast(context: Context, message: String) {
    val toast =
        Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
    toast.show()
}

@Composable
fun Loading302() {
    CircularProgressIndicator(
        modifier = Modifier.size(30.dp),
        strokeWidth = 2.dp,
    )
}

@Composable
fun Loading404() {
    CircularProgressIndicator(
        modifier = Modifier.size(40.dp),
        strokeWidth = 4.dp,
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MyTabRow(tabs: List<String>, onClick: (index: Int) -> Unit) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    SecondaryTabRow(
        divider = @Composable {

        },
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier
            .focusRestorer()
            .padding(horizontal = 8.dp, vertical = 0.dp),
        tabs = {
            tabs.forEachIndexed { index, item ->
                Tab(selected = selectedTabIndex == index, onClick = {
                    selectedTabIndex = index
                    onClick(index)
                }) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }
    )
}

@Composable
fun Tag(text: String) {
    Card(
        modifier = Modifier.padding(0.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
        ),
    ) {
        Text(
            modifier = Modifier
                .height(16.dp)
                .padding(horizontal = 12.dp, vertical = 0.dp),
            text = text, fontSize = 10.sp, fontStyle = FontStyle.Italic,
            lineHeight = 16.sp,
        )
    }

}

@Composable
fun MyInput1(
    placeholder:
    String, value:
    String,
    onValueChange: (value: String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
){
    var text by remember { mutableStateOf(value) }
    var isPlaceholderVisible by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.then(modifier)
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp)) // 设置圆角
            .background(Color(0xFFF0F0F0)), // 设置背景色
        contentAlignment = Alignment.CenterStart
    ) {
        if (text.isEmpty() && isPlaceholderVisible) {
            Text(
                text = placeholder,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                isPlaceholderVisible = it.isEmpty()
                onValueChange(it)
            },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
        )
    }
}

@Composable
fun SoftItem(item: SoftEntity,onClick: (id: Int) -> Unit){
    AnimatedVisibility(
        visible = true,
        enter = scaleIn(
            initialScale = 0.1f, // 从0.8的尺寸开始
            animationSpec = tween(30000) // 动画持续时间300毫秒
        )
    ) {
        ListItem(
            headlineContent = {
                Text(text = "${item.name}")
            },
            supportingContent = {
                Text(text = "${item.versionName} ${item.downloads}")
            },
            leadingContent = {
                SoftIcon6_8("${item.logo}")
            },
            trailingContent = {
                Button(onClick = {
                    onClick(item.id)
                }) {
                    Text(text = "查看")
                }
            }

        )
    }
}