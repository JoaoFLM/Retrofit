package com.example.retrofit.Response

data class CotacaoResponse(
    val adjClose: Double,
    val close: Double,
    val date: String,
    val max: Double,
    val min: Double,
    val open: Double,
    val volume: Long
)
