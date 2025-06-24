package com.ckatsidzira.sipshappen.presentation.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ckatsidzira.sipshappen.ui.theme.SipsHappenTheme

@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    title: String,
    onClick: (() -> Unit)? = null,
    onClickText: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
            )
            if (onClick != null) {
                Text(
                    text = onClickText ?: "",
                    modifier = Modifier.clickable { onClick.invoke() },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(name = "Multiple Banner Sections", showBackground = true)
@Composable
fun PreviewMultipleBannerSections() {
    SipsHappenTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            BannerSection(
                title = "Today's choice",
                modifier = Modifier.padding(vertical = 8.dp)
            )
            BannerSection(
                title = "Top picks",
                onClickText = "View All",
                onClick = { },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}