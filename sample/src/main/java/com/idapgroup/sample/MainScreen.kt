package com.idapgroup.sample

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.errorEvents

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val context = LocalContext.current
    viewModel.HandleErrors()
    LaunchedEffect(Unit) {
        viewModel.successEvent.collect { data ->
            context.toast(data)
        }
    }

    var tagValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { viewModel.startSuccessfulTask() }) {
            Text(text = stringResource(R.string.start_success))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.startFailingTask() }) {
            Text(text = stringResource(R.string.start_failure))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.padding(horizontal = 50.dp),
            value = tagValue,
            label = { Text(stringResource(R.string.tag_hint)) },
            onValueChange = { tagValue = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.tag = tagValue
            }
        ) {
            Text(text = stringResource(R.string.set_tag_button))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                context.toast(viewModel.tag)
            }
        ) {
            Text(text = stringResource(R.string.get_tag_button))
        }
    }
}

@Composable
fun ViewModel.HandleErrors() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        errorEvents.collect { err ->
            context.toast(err.message)
        }
    }
}

fun Context.toast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}