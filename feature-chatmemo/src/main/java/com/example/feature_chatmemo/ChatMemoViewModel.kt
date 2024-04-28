package com.example.feature_chatmemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.core_datastore.PreferenceStorage
import com.ssong_develop.core_model.ChatMemo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatMemoViewModel @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : ViewModel() {

    val chatMemoFlow = preferenceStorage.chatMemoFlow

    fun addChatMemo(memo: String) {
        viewModelScope.launch {
            preferenceStorage.addChatMemo(
                ChatMemo(memo = memo)
            )
        }
    }

    fun updateChatMemo(chatMemo: ChatMemo) {
        viewModelScope.launch {
            preferenceStorage.updateChatMemo(
                chatMemo
            )
        }
    }
}