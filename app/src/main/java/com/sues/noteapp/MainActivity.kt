package com.sues.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sues.noteapp.component.NavGraph
import com.sues.noteapp.data.NoteRepositoryImpl
import com.sues.noteapp.ui.theme.NoteAPPTheme
import com.sues.noteapp.viewModel.NoteViewModel

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(
                        application = application,
                        noteRepository = NoteRepositoryImpl(
                            localDatasource = application.noteDatabase.noteDao(),
                        )
                    ) as T
                }
            }
        }
    )

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


