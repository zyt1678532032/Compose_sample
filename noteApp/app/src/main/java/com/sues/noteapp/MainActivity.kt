package com.sues.noteapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.sues.noteapp.component.*
import com.sues.noteapp.entity.Note
import com.sues.noteapp.ui.theme.*
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import java.lang.Math.ceil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import androidx.compose.runtime.livedata.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>()
    private var dataBase: NoteDataBase? = null

    @ExperimentalAnimationApi
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

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun NavGraph(noteViewModel: NoteViewModel) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = "search") {
        composable("search") {
            Search(navController = navController, noteViewModel = noteViewModel)
        }
        composable("addNote") {
            AddNote(navController = navController, noteViewModel = noteViewModel)
        }
    }
}

@Composable
fun Search(navController: NavHostController, noteViewModel: NoteViewModel) {

    var searchText by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar() {
                Text(
                    text = "My Note",
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        },
        bottomBar = {
            bottomAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        route = "addNote",
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(route = "addNote", inclusive = true).build()
                    )
                },
                backgroundColor = colorNoteColor2,
            ) {
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
                        contentDescription = null,
                        tint = colorSearchIcon
                    )
                },
                label = {
                    Text(
                        text = "Search notes",
                        color = colorTextHint,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.96f),
                colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
            )
            StaggeredVerticalGrid(
                maxColumnWidth = 200.dp,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
            ) {
                repeat(noteViewModel.noteList.value!!.size) {
                    noteItem(noteViewModel.noteList.value!![it])
                }
            }
        }
    }
}

@ExperimentalMaterialApi
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
    // 颜色选择器
    // TODO: 2021/9/13 需要修改
    var colorState1 by remember {
        mutableStateOf(true)
    }
    var colorState2 by remember {
        mutableStateOf(false)
    }
    var colorState3 by remember {
        mutableStateOf(false)
    }
    var colorState4 by remember {
        mutableStateOf(false)
    }
    var colorState5 by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        scope.launch { scaffoldState.bottomSheetState.collapse() }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Swipe up to expand sheet", color = colorWhite, fontWeight = FontWeight.Bold)
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        if (!colorState1) { // false
                            // 其他的变为false
                            colorState1 = false
                            colorState2 = false
                            colorState3 = false
                            colorState4 = false
                            colorState5 = false
                            colorState1 = true
                        }
                    }) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(35.dp)
                                )
                                .background(colorPrimaryDark)
                                .size(40.dp)
                                .padding(5.dp),
                            tint = if (colorState1) colorWhite else colorPrimaryDark
                        )
                    }
                    IconButton(onClick = {
                        if (!colorState2) { // false
                            // 其他的变为false
                            colorState1 = false
                            colorState2 = false
                            colorState3 = false
                            colorState4 = false
                            colorState5 = false
                            colorState2 = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(35.dp)
                                )
                                .background(colorNoteColor2)
                                .size(40.dp)
                                .padding(5.dp),
                            tint = if (colorState2) colorWhite else colorNoteColor2
                        )
                    }
                    IconButton(onClick = {
                        if (!colorState3) { // false
                            // 其他的变为false
                            colorState1 = false
                            colorState2 = false
                            colorState3 = false
                            colorState4 = false
                            colorState5 = false
                            colorState3 = true

                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(35.dp)
                                )
                                .background(colorNoteColor3)
                                .size(40.dp)
                                .padding(5.dp),
                            tint = if (colorState3) colorWhite else colorNoteColor3
                        )
                    }
                    IconButton(onClick = {
                        if (!colorState4) { // false
                            // 其他的变为false
                            colorState1 = false
                            colorState2 = false
                            colorState3 = false
                            colorState4 = false
                            colorState5 = false
                            colorState4 = true

                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(35.dp)
                                )
                                .background(colorNoteColor4)
                                .size(40.dp)
                                .padding(5.dp),
                            tint = if (colorState4) colorWhite else colorNoteColor4
                        )
                    }
                    IconButton(onClick = {
                        if (!colorState5) { // false
                            // 其他的变为false
                            colorState1 = false
                            colorState2 = false
                            colorState3 = false
                            colorState4 = false
                            colorState5 = false
                            colorState5 = true

                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(35.dp)
                                )
                                .background(colorNoteColor5)
                                .size(40.dp)
                                .padding(5.dp),
                            tint = if (colorState5) colorWhite else colorNoteColor5
                        )
                    }
                    Text(
                        text = "选择颜色",
                        fontWeight = FontWeight.Bold,
                        color = colorWhite
                    )
                }
            }
        },
        sheetPeekHeight = 60.dp,
        sheetBackgroundColor = colorMiscellaneousBackground,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 5.dp,
        scaffoldState = scaffoldState,
        snackbarHost = {// 提示框
            SnackbarHost(it) { data ->
                // custom snackbar with the custom border
                Snackbar(
                    modifier = Modifier.border(2.dp, MaterialTheme.colors.secondary),
                    snackbarData = data,
                    backgroundColor = colorWhite
                )
            }
        },
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
                label = {
                    Text(
                        text = "Note Title",
                        color = colorIcons,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.fillMaxWidth(0.96f),
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
            )
            // 时间
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .padding(top = 5.dp, bottom = 5.dp)
            ) {
                Text(text = dateTime, color = colorIcons)
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
                    label = {
                        Text(
                            text = "Note subTitle",
                            color = colorIcons,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.96f),
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
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
                placeholder = {
                    Text(
                        text = "Note content",
                        color = colorIcons,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(150.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
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
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Card(
            backgroundColor = colorPrimaryDark,
            contentColor = colorWhite,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(all = 15.dp)
            ) {
                Text(
                    text = note.title ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = colorWhite
                )
                Text(text = note.subTitle ?: "", fontWeight = FontWeight.Bold, color = colorIcons)
                Text(text = note.noteText, maxLines = 3)
            }
        }
    }
}

