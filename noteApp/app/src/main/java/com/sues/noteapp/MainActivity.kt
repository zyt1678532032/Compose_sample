package com.sues.noteapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>()
    private var dataBase: NoteDataBase? = null

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen()
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
        composable(
            route = "addNote",
        ) {
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
            searchBottomAppBar()
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
        searchContent(searchText, noteViewModel.noteList.value!!) {
            searchText = it
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
    // 选中颜色
    var selectedColor by remember {
        mutableStateOf(Color.Black)
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
                            selectedColor = colorPrimaryDark

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
                            selectedColor = colorNoteColor2
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
                            selectedColor = colorNoteColor2

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
                            selectedColor = colorNoteColor4
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
                            selectedColor = colorNoteColor5
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
                        color = colorWhite,
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
            addNoteTopBar(
                noteContent,
                title,
                subTitle,
                dateTime,
                navController,
                noteViewModel,
                scope,
                scaffoldState,
            )
        }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            addNoteTitle(title) {
                title = it
            }
            // 时间
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .padding(top = 5.dp, bottom = 5.dp)
            ) {
                Text(text = dateTime, color = colorIcons)
            }
            addNoteSubTitle(subTitle) {
                subTitle = it
            }
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            // 笔记内容体
            addNoteContent(noteContent) {
                noteContent = it
            }
        }
    }
}



