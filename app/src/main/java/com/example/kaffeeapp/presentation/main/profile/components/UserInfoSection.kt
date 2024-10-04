package com.example.kaffeeapp.presentation.main.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.util.model.Resource

@Composable
fun UserInfoSection(
    userInfo: User,
    imageUrl: String,
    uploadingImageState: Resource<String>?,
    onChangeImageClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
    ) {
        //image
        UserImage(
            imageUrl = imageUrl,
            uploadingImageState = uploadingImageState,
            onChangeImageClick = { onChangeImageClick.invoke() }
        )
        Column(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.profile_image_size)),
            verticalArrangement = Arrangement.Center,
        ) {
            CustomizedText(
                text = userInfo.name,
                fontSize = dimensionResource(id = R.dimen.text_size_large),
                color = MaterialTheme.colorScheme.onTertiary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_x_small)))
            CustomizedText(
                text = userInfo.email,
                fontSize = dimensionResource(id = R.dimen.text_size_small),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}