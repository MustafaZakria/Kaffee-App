package com.example.kaffeeapp.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.presentation.main.profile.ProfileScreenContent
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme

@Composable
fun ImageLoaderWithUrl(
    modifier: Modifier,
    imageUrl: String,
    placeholder: Int? =  null
) {
    var imageLoadingState by rememberSaveable { mutableStateOf(true) }
    var imageErrorState by rememberSaveable { mutableStateOf(false) }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(if(imageUrl == "") placeholder else imageUrl)
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
@Composable
@Preview(showBackground = true)
fun ProfilePreview() {
    KaffeeAppTheme {
        ImageLoaderWithUrl(modifier = Modifier, imageUrl = "")
    }
}
