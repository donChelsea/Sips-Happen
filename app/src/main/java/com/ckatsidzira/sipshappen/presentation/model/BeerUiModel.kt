package com.ckatsidzira.sipshappen.presentation.model

data class BeerUiModel(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val image: String = "",
    val tagline: String = "",
    val abv: Double = 0.0,
    val foodPairing: List<String> = emptyList(),
    val ingredients: IngredientsUiModel = IngredientsUiModel(),
)

data class IngredientsUiModel(
    val malt: List<MaltUiModel> = emptyList(),
    val hops: List<HopUiModel> = emptyList(),
    val yeast: String = "",
)

data class HopUiModel(
    val name: String = "",
    val amount: AmountUiModel = AmountUiModel(),
    val add: String = "",
    val attribute: String = "",
)

data class MaltUiModel(
    val name: String = "",
    val amount: AmountUiModel = AmountUiModel(),
)

data class AmountUiModel(
    val value: Double = 0.0,
    val unit: String = "",
)