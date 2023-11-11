package com.sues.noteapp.component

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sues.noteapp.data.local.Note
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
        composable(route = Screen.EditNoteScreen.name) {
            // FIXME: 编辑功能
            val note = Note(
                id = 1,
                title = "title1",
                noteText = "的撒hi大使馆蒂萨dsada",
                dateTime = "2021 9-13"
            )
            EditNote(
                note = note,
                navController = navController,
                noteViewModel = noteViewModel,
                context = context
            )
        }
    }
}