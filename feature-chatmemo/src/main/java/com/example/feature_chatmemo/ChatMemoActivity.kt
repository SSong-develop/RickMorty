package com.example.feature_chatmemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class ChatMemoActivity : ComponentActivity() {

    private val chatMemoViewModel by viewModels<ChatMemoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatMemoApp(chatMemoViewModel)
        }
    }
}
