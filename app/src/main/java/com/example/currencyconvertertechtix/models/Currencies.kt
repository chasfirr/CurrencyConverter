package com.example.currencyconvertertechtix

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Currency")
data class Currency(
    @PrimaryKey
    val Symbol: String,
    val value: String
)

/////////////////////////////////////////////////

data class CurrencyJson(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("currencies")
    val symbols: Map<String, String>
)

data class ConvertJson(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val query: Query,
    val result: String,
    val success: Boolean
)

data class Query(
    val amount: Int,
    val from: String,
    val to: String,
)

data class Info(
    val Quote: Double,
    val timestamp: Long
)

///////////////////////////////////////////////

data class ConvertedCurrency(
    val symbol: String,
    val result: String
)

data class ConvertQuery(
    val amount: String,
    val from: String,
    val to: String,
)