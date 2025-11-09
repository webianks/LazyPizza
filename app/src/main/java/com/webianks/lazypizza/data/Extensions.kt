package com.webianks.lazypizza.data

import com.webianks.lazypizza.data.dto.MenuItemDto
import kotlin.collections.map
import kotlin.collections.orEmpty

fun MenuItemDto.toDomain(): MenuItem = when (type.lowercase()) {
    "configurable" -> MenuItem.Configurable(
        id = id,
        name = name,
        imageUrl = imageUrl,
        category = category,
        basePrice = Money(price),
        description = description,
        groups = groups.orEmpty().map { g ->
            ModifierGroup(
                id = g.id,
                title = g.title,
                min = g.min,
                max = g.max,
                options = g.options.map { o ->
                    ModifierOption(
                        o.id,
                        o.name,
                        Money(o.price), o.imageUrl!!
                    )
                }
            )
        }
    )

    else -> MenuItem.Simple(
        id = id,
        name = name,
        imageUrl = imageUrl,
        category = category,
        basePrice = Money(price)
    )
}