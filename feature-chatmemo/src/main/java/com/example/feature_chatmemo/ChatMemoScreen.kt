package com.example.feature_chatmemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChatMemoScreen(
    viewModel: ChatMemoViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = viewModel.testValue,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
