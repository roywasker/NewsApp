package com.roy.newsapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.roy.newsapp.domain.model.Article
import com.roy.newsapp.utils.Constants

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {

            Image(
                modifier = Modifier
                    .size(110.dp)
                    .padding(end = 12.dp)
                    .clip(RoundedCornerShape(8.dp)),
                painter = rememberAsyncImagePainter(article.urlToImage?: Constants.Image.articleImage),
                contentDescription = "Article Image",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f),
            ) {

                Text(
                    text = article.title?: "Title currently unavailable",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.description?: "Description currently unavailable",
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = article.publishedAt?: "Date currently unavailable",
                    fontSize = 12.sp,
                )
            }
        }
    }
}