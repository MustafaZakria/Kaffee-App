package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.ErrorImage
import com.example.kaffeeapp.components.ProgressBar
import com.example.kaffeeapp.data.entities.BranchDetails
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText
import com.example.kaffeeapp.util.Constants.NETWORK_ERROR
import com.example.kaffeeapp.util.model.Resource

@Composable
fun BottomSheetContent(
    branchesResult: BranchesResult,
    onSelectBranch: (BranchDetails) -> Unit
) {
    var selectedBranch by rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxHeight(0.8f),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))

        ) {
            item {
                CustomizedText(
                    text = stringResource(id = R.string.branches),
                    fontSize = dimensionResource(id = R.dimen.text_size_16),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_x_small)))
            }

            if (branchesResult is Resource.Success) {
                val branches = branchesResult.data ?: listOf()
                items(branches) { branch ->
                    BranchItem(
                        branch = branch,
                        selectedBranch = selectedBranch,
                        onSelectBranch = {
                            selectedBranch = branch.name
                            onSelectBranch.invoke(branch)
                        }
                    )
                }
            }
        }
        if (branchesResult is Resource.Loading) {
            ProgressBar(modifier = Modifier.fillMaxSize())
        } else if (branchesResult is Resource.Failure) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorImage()
                CustomizedText(
                    text = NETWORK_ERROR,
                    fontSize = dimensionResource(id = R.dimen.text_size_small),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}