package com.example.kaffeeapp.presentation.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import coil.compose.AsyncImage
import com.example.kaffeeapp.R
import com.example.kaffeeapp.ui.theme.lightRed

@Composable
fun OfferBannerSection(
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium))),
            painter = painterResource(id = R.drawable.img_offer_banner),
            contentDescription = stringResource(id = R.string.offer_img_desc),
            contentScale = ContentScale.Crop
//                .height(maxHeight * 0.29f)
        )
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_small)))
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.promo),
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.lightRed)
                        .padding(dimensionResource(id = R.dimen.padding_x_small)),
                )
            }
            CustomizedText(
                text = stringResource(id = R.string.buy_one_get_one),
                style = MaterialTheme.typography.headlineMedium,
                fontSize = dimensionResource(id = R.dimen.text_size_x_large),
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}