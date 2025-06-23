package com.ckatsidzira.sipshappen.presentation.mapper

import com.ckatsidzira.sipshappen.domain.model.Amount
import com.ckatsidzira.sipshappen.domain.model.Beer
import com.ckatsidzira.sipshappen.domain.model.Hop
import com.ckatsidzira.sipshappen.domain.model.Ingredients
import com.ckatsidzira.sipshappen.domain.model.Malt
import com.ckatsidzira.sipshappen.presentation.model.AmountUiModel
import com.ckatsidzira.sipshappen.presentation.model.BeerUiModel
import com.ckatsidzira.sipshappen.presentation.model.HopUiModel
import com.ckatsidzira.sipshappen.presentation.model.IngredientsUiModel
import com.ckatsidzira.sipshappen.presentation.model.MaltUiModel

fun BeerUiModel.toDomain() = Beer(
    id = id,
    name = name,
    description = description,
    image = image,
    tagline = tagline,
    abv = abv,
    foodPairing = foodPairing,
    ingredients = ingredients.toDomain(),
)

fun IngredientsUiModel.toDomain() = Ingredients(
    malt = malt.map { it.toDomain() },
    hops = hops.map { it.toDomain() },
    yeast = yeast,
)

fun MaltUiModel.toDomain() = Malt(
    name = name,
    amount = amount.toDomain(),
)

fun HopUiModel.toDomain() = Hop(
    name = name,
    amount = amount.toDomain(),
    add = add,
    attribute = attribute,
)

fun AmountUiModel.toDomain() = Amount(
    value = value,
    unit = unit,
)