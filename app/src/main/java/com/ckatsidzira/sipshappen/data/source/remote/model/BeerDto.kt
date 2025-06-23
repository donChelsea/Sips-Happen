package com.ckatsidzira.sipshappen.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class BeerDto(
    val id: Int,
    val name: String,
    val description: String,
    val tagline: String,
    val image: String,
    val abv: Double,
    @SerializedName("food_pairing")
    val foodPairing: List<String>,
    val ingredients: IngredientsDto,
)

data class IngredientsDto(
    val malt: List<MaltDto>,
    val hops: List<HopDto>,
    val yeast: String,
)

data class HopDto(
    val name: String,
    val amount: AmountDto,
    val add: String,
    val attribute: String,
)

data class MaltDto(
    val name: String,
    val amount: AmountDto,
)

data class AmountDto(
    val value: Double,
    val unit: String,
)