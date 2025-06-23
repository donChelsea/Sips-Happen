package com.ckatsidzira.sipshappen.domain.model

data class Beer(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val tagline: String,
    val abv: Double,
    val foodPairing: List<String>,
    val ingredients: Ingredients,
)

data class Ingredients(
    val malt: List<Malt>,
    val hops: List<Hop>,
    val yeast: String,
)

data class Hop(
    val name: String,
    val amount: Amount,
    val add: String,
    val attribute: String,
)

data class Malt(
    val name: String,
    val amount: Amount,
)

data class Amount(
    val value: Double,
    val unit: String,
)