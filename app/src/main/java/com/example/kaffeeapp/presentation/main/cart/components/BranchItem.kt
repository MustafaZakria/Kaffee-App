package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.components.TextWithLeadingIcon
import com.example.kaffeeapp.data.entities.BranchDetails
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun BranchItem(
    branch: BranchDetails,
    selectedBranch: String,
    onSelectBranch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectBranch.invoke() }
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            CustomizedText(
                text = branch.name,
                fontSize = dimensionResource(id = R.dimen.text_size_16),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.headlineSmall
            )
            TextWithLeadingIcon(
                text = branch.address,
                iconId = R.drawable.ic_location,
                textSize = dimensionResource(id = R.dimen.text_size_medium),
                fontWeight = FontWeight.Medium
            )
            TextWithLeadingIcon(
                text = branch.workHours,
                iconId = R.drawable.ic_time,
                textSize = dimensionResource(id = R.dimen.text_size_medium),
                fontWeight = FontWeight.Medium
            )
        }
        if (selectedBranch == branch.name) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_size))
                    .align(Alignment.CenterVertically)
            )
        }
    }
}