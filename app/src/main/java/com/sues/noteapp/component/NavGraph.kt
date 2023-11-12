package com.sues.noteapp.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.sues.noteapp.data.local.Note
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
    imagePathState: MutableState<String?>
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
                context = context,
                imagePathState = imagePathState
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