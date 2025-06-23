package com.ckatsidzira.sipshappen.data.source.mapper

import com.ckatsidzira.sipshappen.data.source.local.model.AmountEntity
import com.ckatsidzira.sipshappen.data.source.local.model.BeerEntity
import com.ckatsidzira.sipshappen.data.source.local.model.HopEntity
import com.ckatsidzira.sipshappen.data.source.local.model.IngredientsEntity
import com.ckatsidzira.sipshappen.data.source.local.model.MaltEntity
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

fun BeerDto.toEntity() = BeerEntity(
    id = id,
    name = name,
    description = description,
    image = image,
    tagline = tagline,
    abv = abv,
    foodPairing = foodPairing,
    ingredients = ingredients.toEntity(),
)

fun IngredientsDto.toEntity() = IngredientsEntity(
    malt = malt.map { it.toEntity() },
    hops = hops.map { it.toEntity() },
    yeast = yeast,
)

fun MaltDto.toEntity() = MaltEntity(
    name = name,
    amount = amount.toEntity(),
)

fun HopDto.toEntity() = HopEntity(
    name = name,
    amount = amount.toEntity(),
    add = add,
    attribute = attribute,
)

fun AmountDto.toEntity() = AmountEntity(
    value = value,
    unit = unit,
)

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