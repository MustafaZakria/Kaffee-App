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
    val userInfo = viewModel.user

    val uploadingImageState = viewModel.uploadingImageState

    val isSystemOnDarkMode = viewModel.isSystemOnDarkMode

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setUserImage(uri)
    }

    ProfileScreenContent(
        userInfo = userInfo,
        uploadingImageState = uploadingImageState,
        isSystemOnDarkMode = isSystemOnDarkMode.value,
        onSystemModeChange = { viewModel.changeSystemMode() },
        onChangeImageClick = {
            galleryLauncher.launch("image/*")
        },
        onMyOrderClick = { navigateToMyOrdersScreen.invoke() }
    )
}

@Composable
fun ProfileScreenContent(
    userInfo: User,
    uploadingImageState: Resource<String>?,
    isSystemOnDarkMode: Boolean,
    onSystemModeChange: () -> Unit,
    onChangeImageClick: () -> Unit,
    onMyOrderClick: () -> Unit
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBarProfileScreen(
                isDarkMode = isSystemOnDarkMode,
                onModeChange = { onSystemModeChange.invoke() }
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
                    imageUrl = userInfo.imageUrl,
                    uploadingImageState = uploadingImageState,
                    onChangeImageClick = { onChangeImageClick.invoke() }
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
                //orders count and points
                UserStatisticalSection(
                    points = userInfo.points,
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
    KaffeeAppTheme(
        darkTheme = true
    ) {
        ProfileScreenContent(
            userInfo = User(
                name = "Mustafa Zakaria",
                email = "Mustafa.zakria21@gmail.com",
                imageUrl = ""
            ),
            null,
            false,
            {},
            {},
            {}
        )
    }
}
