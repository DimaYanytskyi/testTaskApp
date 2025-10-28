package com.example.testtaskapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RecordDto(
    val type: String,
    val text: String? = null,
    val picture: String? = null,
    val url: String? = null
)