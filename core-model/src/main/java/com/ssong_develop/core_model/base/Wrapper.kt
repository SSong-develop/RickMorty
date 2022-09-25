package com.ssong_develop.core_model.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wrapper<T, V>(
    @SerialName("info")
    val info: T,
    @SerialName("results")
    val results: List<V>
)
