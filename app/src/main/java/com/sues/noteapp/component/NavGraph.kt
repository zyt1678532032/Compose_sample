package com.sues.noteapp.component

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sues.noteapp.MainActivity
import com.sues.noteapp.entity.Note
import com.sues.noteapp.viewModel.NoteViewModel

enum class Screen {
    MainScreen,
    AddNoteScreen,
    EditNoteScreen
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NavGraph(
    noteViewModel: NoteViewModel,
    context: Context,
    imagePathUri: Uri?,
    contentResolver: ContentResolver
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
                imagePathUri = imagePathUri,
                contentResolver = contentResolver
            )
        }
        composable(route = Screen.EditNoteScreen.name) { entry ->
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
                context = context,
                contentResolver = contentResolver
            )
        }
    }
}