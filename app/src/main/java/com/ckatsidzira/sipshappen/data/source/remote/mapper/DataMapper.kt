package com.ckatsidzira.sipshappen.data.source.remote.mapper

import com.ckatsidzira.sipshappen.data.source.remote.model.AmountDto
import com.ckatsidzira.sipshappen.data.source.remote.model.BeerDto
import com.ckatsidzira.sipshappen.data.source.remote.model.HopDto
import com.ckatsidzira.sipshappen.data.source.remote.model.IngredientsDto
import com.ckatsidzira.sipshappen.data.source.remote.model.MaltDto
import com.ckatsidzira.sipshappen.domain.model.Amount
import com.ckatsidzira.sipshappen.domain.model.Beer
import com.ckatsidzira.sipshappen.domain.model.Hop
import com.ckatsidzira.sipshappen.domain.model.Ingredients
import com.ckatsidzira.sipshappen.domain.model.Malt

fun BeerDto.toDomain() = Beer(
    id = id,
    name = name,
    description = description,
    image = image,
    tagline = tagline,
    abv = abv,
    foodPairing = foodPairing,
    ingredients = ingredients.toDomain(),
)

fun IngredientsDto.toDomain() = Ingredients(
    malt = malt.map { it.toDomain() },
    hops = hops.map { it.toDomain() },
    yeast = yeast,
)

fun MaltDto.toDomain() = Malt(
    name = name,
    amount = amount.toDomain(),
)

fun HopDto.toDomain() = Hop(
    name = name,
    amount = amount.toDomain(),
    add = add,
    attribute = attribute,
)

fun AmountDto.toDomain() = Amount(
    value = value,
    unit = unit,
)