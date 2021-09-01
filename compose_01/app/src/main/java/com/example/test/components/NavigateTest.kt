package com.example.test.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Popup
import androidx.lifecycle.withResumed
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.test.R

/**
 *  Compose中导航组件的使用
 */
@Composable
fun NavControllerTest() {
    val navController = rememberNavController()
    navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController, startDestination = "profile"
    ) {
        composable("profile") {
            Profile(navController = navController)
        }
        composable("friends") {
            Friends()
        }
    }

}

@Composable
fun Profile(navController: NavController) {
    Button(onClick = {
    }) {
        Text(text = "跳转到下一个布局")
    }
}

@Composable
fun Friends() {
    Text(
        text = "friends",
    )
}

@Composable
fun Home() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            BottomNavigation {
                items.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(screen.route) },
                    )
                }
            }

        }
    ) {
        // 每个 NavController 都必须与一个 NavHost 可组合项相关联。
        NavHost(
            navController = navController,
            startDestination = Screen.Profile.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.Profile.route) {
                Profile(navController = navController)
            }
            composable(Screen.Friends.route) {
                Friends()
            }
        }
    }
}

// 密封类:就是它的子类需要和它在同一个包下
sealed class Screen(val route: String) {
    object Profile : Screen("profile")
    object Friends : Screen("friends")
}

val items = listOf<Screen>(
    Screen.Profile,
    Screen.Friends
)


