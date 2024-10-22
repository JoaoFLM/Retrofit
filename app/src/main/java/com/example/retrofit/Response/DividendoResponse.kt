package com.example.retrofit.Response

data class DividendoResponse(
        val amount: Double,
        val adjAmount: Double,
        val approvalDate: String,
        val cvmCode: String,
        val exDate: String,
        val notes: String?,
        val payableDate: String,
        val recordDate: String,
        val ticker: String,
        val type: String
    )
