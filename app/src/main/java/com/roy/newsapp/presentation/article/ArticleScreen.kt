package com.roy.newsapp.presentation.article

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.roy.newsapp.domain.model.Article
import com.roy.newsapp.presentation.components.ErrorCard
import com.roy.newsapp.ui.theme.LinkColor
import com.roy.newsapp.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    navController: NavController,
){

    val context = LocalContext.current
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val article = savedStateHandle?.get<Article>("article")
    val configuration = LocalConfiguration.current
    val isPortrait =  configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = Constants.Text.ArticleTopBarTitle,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            modifier = Modifier.size(size = 26.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Arrow icon",
                        )
                    }
                }
            )
        },

    ){ padding ->

        if (article == null){

            ErrorCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 12.dp)
                    .offset(y =(-30).dp),
                message = Constants.Text.ArticleUnavailable,
                buttonText = Constants.Text.GoBackButton,
                onClick = {
                    navController.navigateUp()
                }
            )

        }else {

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(bottom = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                val imageModifier = if (isPortrait){
                    Modifier.aspectRatio(16f / 9f)
                }else {
                    Modifier.height(200.dp).fillMaxWidth(1f)
                }

                val horizontalPadding = if (isPortrait) 18.dp else 24.dp

                Image(
                    modifier = imageModifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = horizontalPadding)
                        .clip(RoundedCornerShape(size = 8.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp),
                        ),
                    painter = rememberAsyncImagePainter(
                        article.urlToImage ?: Constants.Image.articleImage
                    ),
                    contentDescription = "Article Image",
                    contentScale = if (isPortrait) ContentScale.Crop else ContentScale.FillBounds
                )

                val authorPublished = (article.author ?: "Author currently unavailable") +
                        " | " +
                        (article.publishedAt ?: "Date currently unavailable")

                Text(
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    text = authorPublished,
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.8f)
                )

                Text(
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    text = article.title ?: "Title currently unavailable",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )

                Text(
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    text = article.description ?: "Description currently unavailable",
                    fontSize = 18.sp
                )

                Text(
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                    text = article.content ?: "Content currently unavailable",
                    fontSize = 16.sp,
                )

                TextButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, article.url?.toUri())
                        context.startActivity(intent)
                    }
                ) {
                    Text(
                        text = Constants.Text.ArticleButton,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = LinkColor,
                    )
                }
            }
        }
    }
}