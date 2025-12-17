package com.trishala.cvs.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.trishala.cvs.presentation.features.details.DetailsRoute
import com.trishala.cvs.presentation.features.search.SearchRoute


object Routes {
    const val SEARCH = "search"
    const val DETAIL = "detail"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(Routes.SEARCH) {
            SearchRoute(
                onCharacterClick = { id ->
                    navController.navigate("${Routes.DETAIL}/$id")
                }
            )
        }

        composable(
            route = "${Routes.DETAIL}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable

            DetailsRoute(
                id = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
