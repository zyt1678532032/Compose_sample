package com.sues.noteapp

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sues.noteapp.component.*
import com.sues.noteapp.ui.theme.*
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>()
    private var dataBase: NoteDataBase? = null

    companion object {
        // 获取权限
        const val REQUEST_CODE_STORAGE_PERMISSION = 1
        const val REQUEST_CODE_SELECT_IMAGE = 2
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen()
        // dataBase = NoteDataBase.getDataBase(applicationContext)

        setContent {
            val imageBitmapState by noteViewModel.imageBitmap.observeAsState()
            val imageUriState by noteViewModel.imageUri.observeAsState()

            NoteAPPTheme {
                NavGraph(
                    noteViewModel = noteViewModel,
                    context = applicationContext,
                    activity = this,
                    imageBitmap = imageBitmapState,
                    contentResolver = contentResolver,
                    imagePathUri = imageUriState
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage()
            } else {
                Toast.makeText(this, "申请权限失败!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        if (intent.resolveActivity(packageManager) != null) {
//             startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE)
//            //registerForActivityResult()
//        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            // && requestCode == RESULT_OK
            if (data != null) {
                val imageUri = data.data
                Log.i("Uri",imageUri.toString())
                if (imageUri != null) {
                    try {
                        val inputStream = contentResolver.openInputStream(imageUri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        noteViewModel.imageBitmap.value = bitmap
                        noteViewModel.imageUri.value = imageUri
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

fun getPathFromUri(imageUri: Uri,contentResolver: ContentResolver): String {
    var filePath: String = ""
    val cursor = contentResolver.query(imageUri, null, null, null, null)
    if (cursor == null) {
        filePath = imageUri.path!!
    }else{
        cursor.moveToFirst()
        val index = cursor.getColumnIndex("_data")
        filePath = cursor.getString(index)
        cursor.close()
    }
    return filePath
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun NavGraph(
    noteViewModel: NoteViewModel,
    context: Context,
    activity: MainActivity,
    imageBitmap: Bitmap?,
    imagePathUri: Uri?,
    contentResolver: ContentResolver
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = "search") {
        composable("search") {
            Search(
                navController = navController,
                noteViewModel = noteViewModel,
                imagePathUri = imagePathUri,
                contentResolver = contentResolver
            )
        }
        composable(
            route = "addNote",
        ) {
            AddNote(
                navController = navController,
                noteViewModel = noteViewModel,
                context = context,
                activity = activity,
                imageBitmap = imageBitmap,
                imagePathUri = imagePathUri,
                contentResolver = contentResolver
            )
        }
    }
}

@Composable
fun Search(
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    imagePathUri: Uri?,
    contentResolver: ContentResolver
) {
    val (searchText, changeSearchText) = remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            SearchTopBar()
        },
        bottomBar = {
            SearchBottomAppBar()
        },
        floatingActionButton = {
            FloatingButton(navController = navController)
        }
    ) {
        SearchContent(
            searchText = searchText,
            notes = noteViewModel.noteList.value!!,
            onValueChange = changeSearchText,
            imagePathUri = imagePathUri,
            contentResolver = contentResolver
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun AddNote(
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    context: Context,
    activity: MainActivity,
    imageBitmap: Bitmap?,
    contentResolver: ContentResolver,
    imagePathUri: Uri?
) {
    val (title, changeTitle) = remember {
        mutableStateOf("")
    }
    val (subTitle, changeSubTitle) = remember {
        mutableStateOf("")
    }
    val (noteContent, changeContent) = remember {
        mutableStateOf("")
    }
    val dateTime by remember {
        val time = SimpleDateFormat("yyyy MMMM dd HH:MM a,EEEE", Locale.getDefault())
            .format(Date())
        mutableStateOf(time)
    }
    // 颜色选择器
    // Fixme: 2021/9/13 颜色选择需要修改
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
        mutableStateOf(SelectedColor.Color1)
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
                horizontalAlignment = Alignment.CenterHorizontally,
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
                            selectedColor = SelectedColor.Color1

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
                            selectedColor = SelectedColor.Color2
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
                            selectedColor = SelectedColor.Color3

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
                            selectedColor = SelectedColor.Color4
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
                            selectedColor = SelectedColor.Color5
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
                // 添加图片
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clickable {
                            // Fixme: 添加图片
                            // 关闭底部导航栏
                            scope.launch {
                                scaffoldState.bottomSheetState.collapse()
                            }
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                ActivityCompat.requestPermissions(
                                    activity,
                                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                                    MainActivity.REQUEST_CODE_STORAGE_PERMISSION
                                )
                            } else {
                                activity.selectImage()
                            }
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_image),
                        contentDescription = null
                    )
                    Text(
                        text = "添加图片",
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
            AddNoteTopBar(
                noteContent = noteContent,
                title = title,
                subTitle = subTitle,
                dateTime = dateTime,
                navController = navController,
                noteViewModel = noteViewModel,
                scope = scope,
                scaffoldState = scaffoldState,
                selectedColor = selectedColor,
                contentResolver = contentResolver,
                imagePathUri = imagePathUri
            )
        }) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp)
        ) {

            item {
                AddNoteTitle(title = title, onValueChange = changeTitle)
                // Fixme:这里好像也有问题(时间信息显示)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.96f)
                        .padding(top = 5.dp, bottom = 5.dp)
                ) {
                    Text(text = dateTime, color = colorIcons)
                }
                AddNoteSubTitle(
                    subTitle = subTitle,
                    selectedColor = selectedColor,
                    onValueChange = changeSubTitle
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                // Fixme: 添加图片布局
                println(imageBitmap ?: "bitmap为空")
                if (imageBitmap != null) {
                    setImage(bitmap = imageBitmap)
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                // 笔记内容体
                AddNoteContent(noteContent = noteContent, onValueChange = changeContent)
            }

        }
    }
}

@Composable
fun setImage(bitmap: Bitmap) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier.clip(RoundedCornerShape(15.dp))
    )
}



