package com.ckatsidzira.sipshappen.data.source.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache")
data class BeerEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val tagline: String,
    val image: String,
    val abv: Double,
    val foodPairing: List<String>,
    @Embedded
    val ingredients: IngredientsEntity,
)


data class IngredientsEntity(
    val malt: List<MaltEntity>,
    val hops: List<HopEntity>,
    val yeast: String,
)

data class HopEntity(
    val name: String,
    val amount: AmountEntity,
    val add: String,
    val attribute: String,
)

data class MaltEntity(
    val name: String,
    val amount: AmountEntity,
)

data class AmountEntity(
    val value: Double,
    val unit: String,
)