package com.sues.noteapp.component

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.CoroutineScope

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
    scope: CoroutineScope,
    context: Context,
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
            AddNote(
                navController = navController,
                noteViewModel = noteViewModel,
            )
        }
        composable(
            route = Screen.EditNoteScreen.name,
        ) {

            // EditNote(
            //     note = note,
            //     navController = navController,
            //     noteViewModel = noteViewModel,
            //     context = context
            // )
        }
    }
}