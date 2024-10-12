package com.example.kaffeeapp.presentation.main.cart.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kaffeeapp.R
import com.example.kaffeeapp.presentation.main.home.components.CustomizedText

@Composable
fun NoteDialog(
    noteValue: String,
    onNoteValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSaveNote: () -> Unit
) {

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.tertiary,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_rounded_corner_medium)),
        title = {
            CustomizedText(
                text = stringResource(id = R.string.note),
                fontSize = dimensionResource(id = R.dimen.text_size_16),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        },
        text = {
            TextField(
                value = noteValue,
                onValueChange = { value -> onNoteValueChange.invoke(value) },
                placeholder = {
                    CustomizedText(
                        text = stringResource(id = R.string.leave_note),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = dimensionResource(id = R.dimen.text_size_medium)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onTertiary
                ),
                minLines = 2
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss.invoke()
                }
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.dismiss),
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        onDismissRequest = { onDismiss.invoke() },
        confirmButton = {
            TextButton(
                onClick = {
                    onSaveNote.invoke()
                }
            ) {
                CustomizedText(
                    text = stringResource(id = R.string.save),
                    fontSize = dimensionResource(id = R.dimen.text_size_medium),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}
