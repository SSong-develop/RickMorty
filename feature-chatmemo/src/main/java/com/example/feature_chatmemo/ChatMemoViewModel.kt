package com.example.feature_chatmemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatMemoViewModel @Inject constructor(

): ViewModel() {

    val testValue by mutableStateOf("helloWorld!")
}