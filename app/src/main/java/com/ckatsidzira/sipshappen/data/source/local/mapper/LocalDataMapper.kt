package com.ckatsidzira.sipshappen.data.source.local.mapper

import com.ckatsidzira.sipshappen.data.source.local.model.AmountEntity
import com.ckatsidzira.sipshappen.data.source.local.model.BeerEntity
import com.ckatsidzira.sipshappen.data.source.local.model.HopEntity
import com.ckatsidzira.sipshappen.data.source.local.model.IngredientsEntity
import com.ckatsidzira.sipshappen.data.source.local.model.MaltEntity
import com.ckatsidzira.sipshappen.domain.model.Amount
import com.ckatsidzira.sipshappen.domain.model.Beer
import com.ckatsidzira.sipshappen.domain.model.Hop
import com.ckatsidzira.sipshappen.domain.model.Ingredients
import com.ckatsidzira.sipshappen.domain.model.Malt

fun BeerEntity.toDomain() = Beer(
    id = id,
    name = name,
    description = description,
    image = image,
    tagline = tagline,
    abv = abv,
    foodPairing = foodPairing,
    ingredients = ingredients.toDomain(),
)

fun IngredientsEntity.toDomain() = Ingredients(
    malt = malt.map { it.toDomain() },
    hops = hops.map { it.toDomain() },
    yeast = yeast,
)

fun MaltEntity.toDomain() = Malt(
    name = name,
    amount = amount.toDomain(),
)

fun HopEntity.toDomain() = Hop(
    name = name,
    amount = amount.toDomain(),
    add = add,
    attribute = attribute,
)

fun AmountEntity.toDomain() = Amount(
    value = value,
    unit = unit,
)