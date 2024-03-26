package com.bootx.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bootx.app.entity.SoftEntity
import com.bootx.app.ui.theme.fontSize12
import com.bootx.app.ui.theme.fontSize14
import com.bootx.app.ui.theme.padding8
import com.bootx.app.ui.theme.shape4

@Composable
fun Item3(
    item: SoftEntity,
    onClick: (id: Int) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable {
            onClick(item.id)
        },
        headlineContent = {
            Text(
                text = item.name,
                fontSize = MaterialTheme.typography.titleMedium.copy(fontSize = fontSize14).fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingContent = {
            SoftIcon6(url = item.logo)
        },
        trailingContent = {
            Button(onClick = {
                onClick(item.id)
            }) {
                Text(text = "查看")
            }
        },
        supportingContent = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "",
                        modifier = Modifier.size(12.dp),
                        tint = Color(0xFF2196f3),
                    )
                    Text(
                        text = item.score,
                        fontSize = MaterialTheme.typography.titleSmall.copy(fontSize = fontSize12).fontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2196f3),
                    )
                    Text(
                        text = item.versionName ?: "",
                        fontSize = MaterialTheme.typography.titleSmall.copy(fontSize = fontSize12).fontSize,
                        color = Color(0xFF8d9195),
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }
    )
}
