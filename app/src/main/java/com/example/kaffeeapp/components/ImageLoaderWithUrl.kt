package com.example.kaffeeapp.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ImageLoaderWithUrl(
    modifier: Modifier,
    imageUrl: String
) {
    var imageLoadingState by rememberSaveable { mutableStateOf(true) }
    var imageErrorState by rememberSaveable { mutableStateOf(false) }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .listener(
                onStart = {
                    imageLoadingState = true
                    imageErrorState = false
                },
                onSuccess = { _, _ ->
                    imageLoadingState = false
                    imageErrorState = false
                },
                onError = { _, _ ->
                    imageLoadingState = false
                    imageErrorState = true
                }
            )
            .build(),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
    if (imageLoadingState) {
        ProgressBar()
    }
    if (imageErrorState) {
        ErrorImage()
    }

}