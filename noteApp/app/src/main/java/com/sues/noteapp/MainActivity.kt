package com.sues.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sues.noteapp.component.addNoteContent
import com.sues.noteapp.component.bottomAppBar
import com.sues.noteapp.component.topAppbar
import com.sues.noteapp.ui.theme.NoteAPPTheme
import com.sues.noteapp.ui.theme.colorIcons
import com.sues.noteapp.ui.theme.colorNoteColor2

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAPPTheme {
                NavGraph()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            Search(navController = navController)
        }
        composable("addNote") {
            AddNote(navController = navController)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Search(navController: NavHostController) {

    var searchText by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar() {
                Text(
                    text = "笔记",
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(start = 10.dp, end = 10.dp)) {
                bottomAppBar()
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addNote")
            }, backgroundColor = colorNoteColor2) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                )
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                label = { Text(text = "Search notes") },
                modifier = Modifier
                    .fillMaxWidth(0.96f),
            )

        }
    }
}

@Composable
fun AddNote(navController: NavHostController) {
    val title = ""
    val subTitle = ""
    val noteContent = ""

    Scaffold(
        topBar = {
            TopAppBar {
                topAppbar(navController = navController)
            }

        }) {
        addNoteContent(
            listOf(
                title,
                subTitle,
                noteContent
            )
        )
    }
}