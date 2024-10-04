package com.example.kaffeeapp.presentation.main.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.User
import com.example.kaffeeapp.presentation.main.profile.components.SettingItems
import com.example.kaffeeapp.presentation.main.profile.components.TopBarProfileScreen
import com.example.kaffeeapp.presentation.main.profile.components.UserInfoSection
import com.example.kaffeeapp.presentation.main.profile.components.UserStatisticalSection
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
    var userImageUrl by rememberSaveable { mutableStateOf("") }

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

    LaunchedEffect(key1 = userInfo) {
        userImageUrl = userInfo.value.imageUrl
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
                UserInfoSection(
                    userInfo = userInfo,
                    imageUrl = imageUrl,
                    uploadingImageState = uploadingImageState,
                    onChangeImageClick = { onChangeImageClick.invoke() }
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                //orders count and points
                UserStatisticalSection(
                    points = points,
                    userInfo = userInfo
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                SettingItems(
                    onMyOrderClick = { onMyOrderClick.invoke() }
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
