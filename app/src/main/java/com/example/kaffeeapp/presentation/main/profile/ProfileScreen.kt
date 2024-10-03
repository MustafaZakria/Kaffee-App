package com.example.kaffeeapp.presentation.main.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ImageLoaderWithUrl
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.presentation.main.profile.components.ProfileRowItem
import com.example.kaffeeapp.presentation.main.profile.components.StatisticItem
import com.example.kaffeeapp.presentation.main.profile.components.TopBarProfileScreen
import com.example.kaffeeapp.ui.theme.KaffeeAppTheme
import com.example.kaffeeapp.util.model.Resource

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToMyOrdersScreen: () -> Unit
) {
    val userInfo = viewModel.user.collectAsState(initial = User())
    val points = viewModel.points.toString()
    val uploadingImageState = viewModel.uploadingImageState
    var userImageUrl by rememberSaveable { mutableStateOf(userInfo.value.imageUrl) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setUserImage(uri)
    }

    LaunchedEffect(key1 = uploadingImageState) {
        if (uploadingImageState is Resource.Success) {
            userImageUrl = uploadingImageState.data.toString()
        }
    }

    ProfileScreenContent(
        userInfo = userInfo.value,
        imageUrl = userImageUrl,
        uploadingImageState = uploadingImageState,
        points = points,
        onChangeImageClick = {
            galleryLauncher.launch("image/*")
        },
        onMyOrderClick = { navigateToMyOrdersScreen.invoke() }
    )
}

@Composable
fun ProfileScreenContent(
    userInfo: User,
    imageUrl: String,
    uploadingImageState: Resource<String>?,
    points: String,
    onChangeImageClick: () -> Unit,
    onMyOrderClick: () -> Unit
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBarProfileScreen(
                isDarkMode = false,
                onModeChange = {}
            )
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = dimensionResource(id = R.dimen.padding_bottom_navigation),
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                //profile Info
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                ) {
                    //image
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
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                //orders count and points
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    StatisticItem(
                        modifier = Modifier.weight(1f),
                        value = points,
                        text = stringResource(id = R.string.points)
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.height_divider_50))
                            .align(Alignment.CenterVertically)
                    )
                    StatisticItem(
                        modifier = Modifier.weight(1f),
                        value = userInfo.orders.size.toString(),
                        text = stringResource(id = R.string.orders)
                    )
                }
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                ProfileRowItem(
                    text = stringResource(id = R.string.my_orders),
                    onClick = { onMyOrderClick.invoke() }
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_10)))
                ProfileRowItem(
                    text = stringResource(id = R.string.about_us),
                    onClick = {}
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_10)))
                ProfileRowItem(
                    text = stringResource(id = R.string.privacy_policy),
                    onClick = {}
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_10)))
                ProfileRowItem(
                    text = stringResource(id = R.string.contact_us),
                    onClick = {}
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ProfilePreview() {
    KaffeeAppTheme {
        ProfileScreenContent(
            userInfo = User(
                name = "Mustafa Zakaria",
                email = "Mustafa.zakria21@gmail.com",
                imageUrl = ""
            ),
            "0",
            null,
            "",
            {},
            {}
        )
    }
}
