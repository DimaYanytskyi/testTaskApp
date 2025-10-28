package com.example.testtaskapp.domain.model

data class Record(
    val type: String,
    val text: String? = null,
    val picture: String? = null,
    val url: String? = null
)