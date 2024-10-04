package com.example.kaffeeapp.presentation.main.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ImageLoaderWithUrl
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.util.model.Resource

@Composable
fun UserImage(
    imageUrl: String,
    uploadingImageState: Resource<String>?,
    onChangeImageClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.profile_image_size))
            .clickable { onChangeImageClick.invoke() }
    ) {
        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.profile_image_size)),
            shape = CircleShape,
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.Transparent
            )
        ) {
            ImageLoaderWithUrl(
                modifier = Modifier,
                imageUrl = imageUrl,
                placeholder = R.drawable.profile_picture
            )
        }
        if (uploadingImageState is Resource.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(color = Color.White.copy(alpha = 0.35f))
            ) {
                ProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.circle_size_32))
                    .padding(dimensionResource(id = R.dimen.padding_x_small))
            )
        }
    }
}