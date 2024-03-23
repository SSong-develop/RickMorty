package com.example.feature_chatmemo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.feature_chatmemo.ui.theme.RickMortyTheme

@Composable
fun ChatMemoApp(
    chatMemoViewModel: ChatMemoViewModel
) {
    RickMortyTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ChatMemoScreen(
                viewModel = chatMemoViewModel,
                modifier = Modifier
            )
        }
    }
}