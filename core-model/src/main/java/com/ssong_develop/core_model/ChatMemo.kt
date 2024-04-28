package com.ssong_develop.core_model

import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.UUID

@Serializable
data class ChatMemo(
    val id: String = UUID.randomUUID().toString(),
    val date: String = Calendar.getInstance().time.toString(),
    val memo: String
)