package com.sues.noteapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sues.noteapp.component.addNoteContent
import com.sues.noteapp.component.bottomAppBar
import com.sues.noteapp.component.topAppbar
import com.sues.noteapp.entity.Note
import com.sues.noteapp.ui.theme.*
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>()
    private var dataBase: NoteDataBase? = null

    @OptIn(ExperimentalFoundationApi::class)
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // dataBase = NoteDataBase.getDataBase(applicationContext)

        setContent {
            NoteAPPTheme {
                NavGraph(noteViewModel = noteViewModel)
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NavGraph(noteViewModel: NoteViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            Search(navController = navController, noteViewModel = noteViewModel)
        }
        composable("addNote") {
            AddNote(navController = navController, noteViewModel = noteViewModel)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Search(navController: NavHostController, noteViewModel: NoteViewModel) {

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
            bottomAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(
                    route = "addNote",
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(route = "addNote", inclusive = true).build()
                )
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
            // 笔记内容
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(0.96f),
                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 5.dp)
            ) {
                // Note 列表元素
                items(noteViewModel.noteList.value!!.size) { index ->
                    //
                    noteItem(noteViewModel.noteList.value!![index])
                }
            }
        }
    }
}

@Composable
fun AddNote(navController: NavHostController, noteViewModel: NoteViewModel) {
    var title by remember {
        mutableStateOf("")
    }
    var subTitle by remember {
        mutableStateOf("")
    }
    var noteContent by remember {
        mutableStateOf("")
    }
    val dateTime by remember {
        val time = SimpleDateFormat("yyyy MMMM dd HH:MM a,EEEE", Locale.getDefault())
            .format(Date())
        mutableStateOf(time)
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.navigate(
                            route = "search",
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(route = "search", inclusive = true)
                                .build()
                        )
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 13.dp)
                        )
                    }
                    Spacer(modifier = Modifier.fillMaxWidth(0.85f))
                    IconButton(
                        onClick = {
                            if (noteContent.isEmpty()) {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "笔记内容不能为空!"
                                    )
                                }
                            } else {
                                noteViewModel.addNote(
                                    Note(
                                        id = 1,
                                        title = title,
                                        subTitle = subTitle,
                                        noteText = noteContent,
                                        dateTime = dateTime
                                    )
                                )
                                navController.navigate(
                                    route = "search",
                                    navOptions = NavOptions.Builder()
                                        .setPopUpTo(route = "search", inclusive = true)
                                        .build()
                                )
                                Log.i("addNote", noteViewModel.noteList.value!!.size.toString())

                            }

                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = null,
                            modifier = Modifier
                                .width(25.dp)
                                .height(24.dp)
                                .border(
                                    width = 2.dp,
                                    color = colorIcons,
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .padding(5.dp)
                        )
                    }
                }
            }

        }) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = { Text(text = "Note Title") },
                modifier = Modifier.fillMaxWidth(0.96f),
                maxLines = 1
            )
            // 时间
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .padding(top = 5.dp, bottom = 5.dp)
            ) {
                Text(text = dateTime)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(5.dp)
                        .height(50.dp)
                        .background(colorNoteColor2)
                )
                Spacer(
                    modifier = Modifier
                        .width(13.dp)
                )
                OutlinedTextField(
                    value = subTitle,
                    onValueChange = {
                        subTitle = it
                    },
                    label = { Text(text = "Note subTitle") },
                    modifier = Modifier
                        .fillMaxWidth(0.96f),
                    maxLines = 1
                )
                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            // 笔记内容体
            OutlinedTextField(
                value = noteContent,
                onValueChange = {
                    noteContent = it
                },
                placeholder = { Text(text = "Note content") },
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(150.dp),
                shape = RoundedCornerShape(10.dp),
            )
        }
    }
}

@Composable
fun noteItem(note: Note) {
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .alpha(1f)
            .padding(horizontal = 4.dp)
    ) {
        Card(
            backgroundColor = colorPrimaryDark,
            contentColor = colorWhite,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(all = 15.dp)
            ) {
                Text(text = note.title ?: "")
                Text(text = note.subTitle ?: "")
                Text(text = note.noteText, maxLines = 3)
            }
        }
    }
}