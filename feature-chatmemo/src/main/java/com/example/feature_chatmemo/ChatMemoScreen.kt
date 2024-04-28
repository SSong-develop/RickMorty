package com.example.feature_chatmemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ChatMemoScreen(
    viewModel: ChatMemoViewModel,
    modifier: Modifier
) {

    var memo by remember { mutableStateOf("") }

    val list by viewModel.chatMemoFlow.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
    ) {
        TextField(
            value = memo,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onValueChange = {
                memo = it
            }
        )

        Button(
            onClick = {
                viewModel.addChatMemo(memo)
            }
        ) {
            Text(
                text = "save",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }

        Text(
            text = list.joinToString { it.memo }
        )
    }
}
