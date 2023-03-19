package com.example.onlinestore.feature_online_store.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.onlinestore.R
import coil.request.ImageRequest
import com.example.onlinestore.feature_online_store.domain.model.Product

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(product.image)
                        .crossfade(true).build(),
                    placeholder = painterResource(id = R.drawable.no_image),
                    contentDescription = product.title,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(190.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.Center)
                )

                Text(
                    text = "${product.rating.rate}/10",
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.5f
                            )
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = product.title,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = "${product.price} $")
        }

    }
}