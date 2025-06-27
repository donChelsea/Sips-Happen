package com.ckatsidzira.sipshappen.presentation.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class NavScreen(val route: String) {
    data object Home: NavScreen("Home")
    data object Details: NavScreen("Details")
    data object Favorites: NavScreen("Favorites")

    object DetailArgs {
        const val ID = "id"
    }

    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}