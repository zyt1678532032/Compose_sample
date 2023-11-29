package com.sues.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import com.sues.noteapp.component.NavGraph
import com.sues.noteapp.ui.theme.NoteAPPTheme
import com.sues.noteapp.viewModel.NoteViewModel

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel> {
        NoteViewModel.Factory
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAPPTheme {
                NavGraph(
                    noteViewModel = noteViewModel,
                )
            }
        }
    }

}

