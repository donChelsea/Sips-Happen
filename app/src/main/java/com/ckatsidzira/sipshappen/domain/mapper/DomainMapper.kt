package com.ckatsidzira.sipshappen.domain.mapper

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

fun Beer.toUiModel() = BeerUiModel(
    id = id,
    name = name,
    description = description,
    image = image,
    tagline = tagline,
    abv = abv,
    foodPairing = foodPairing,
    ingredients = ingredients.toUiModel(),
)

fun Ingredients.toUiModel() = IngredientsUiModel(
    malt = malt.map { it.toUiModel() },
    hops = hops.map { it.toUiModel() },
    yeast = yeast,
)

fun Malt.toUiModel() = MaltUiModel(
    name = name,
    amount = amount.toUiModel(),
)

fun Hop.toUiModel() = HopUiModel(
    name = name,
    amount = amount.toUiModel(),
    add = add,
    attribute = attribute,
)

fun Amount.toUiModel() = AmountUiModel(
    value = value,
    unit = unit,
)