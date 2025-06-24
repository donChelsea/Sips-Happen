package com.ckatsidzira.sipshappen.presentation.custom.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ckatsidzira.sipshappen.BuildConfig.IMAGES_BASE_URL
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel
import com.ckatsidzira.sipshappen.presentation.util.MOCK_BEER

@Composable
fun RandomBeerCard(
    beer: BeerUiModel,
    modifier: Modifier = Modifier,
    onItemClick: (BeerUiModel) -> Unit = {}
) {
    Card(
        onClick = { onItemClick(beer) },
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = IMAGES_BASE_URL + beer.image,
                contentDescription = beer.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
                    .fillMaxHeight()
                    .background(Color.Transparent),
            )
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
                Text(
                    text = beer.description.ifEmpty { beer.tagline },
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, end = 12.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxHeight()
                    .weight(.50f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBeerItem() {
    RandomBeerCard(beer = MOCK_BEER)
}