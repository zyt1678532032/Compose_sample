package com.sues.noteapp.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sues.noteapp.viewModel.NoteViewModel

// 导航枚举类
enum class Screen {
    MainScreen,
    AddNoteScreen,
    EditNoteScreen
}

@ExperimentalMaterialApi
@Composable
fun NavGraph(
    noteViewModel: NoteViewModel,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.name) {
        composable(route = Screen.MainScreen.name) {
            MainScreen(
                navController = navController,
                noteViewModel = noteViewModel,
            )
        }
        composable(route = Screen.AddNoteScreen.name) {
            EditNoteScreen(
                navController = navController,
                noteViewModel = noteViewModel,
            )
        }
        composable(
            route = Screen.EditNoteScreen.name,
        ) {
            EditNoteScreen(
                navController = navController,
                noteViewModel = noteViewModel,
            )
        }
    }
}