package com.thk.todo_clone.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.thk.todo_clone.R

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    onCloseListener: () -> Unit,
    onYesClicked: () -> Unit
) {
   if (openDialog) {
       AlertDialog(
           title = {
               Text(
                   text = title,
                   fontSize = MaterialTheme.typography.h5.fontSize,
                   fontWeight = FontWeight.Bold
               )
           },
           text = {
              Text(
                  text = message,
                  fontSize = MaterialTheme.typography.subtitle1.fontSize,
                  fontWeight = FontWeight.Normal
              )
           },
           confirmButton = {
               TextButton(
                       onClick = {
                       onYesClicked()
                       onCloseListener()
                   }
               ) {
                    Text(text = stringResource(id = R.string.yes))
               }
           },
           dismissButton = {
               TextButton(
                   onClick = {
                       onCloseListener()
                   }
               ) {
                   Text(text = stringResource(id = R.string.no))
               }
           },
           onDismissRequest = { onCloseListener() }
       )
   }
}