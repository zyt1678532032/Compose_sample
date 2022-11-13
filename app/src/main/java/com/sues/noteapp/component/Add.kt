package com.sues.noteapp.component

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sues.noteapp.MainActivity
import com.sues.noteapp.R // app图片等资源
import com.sues.noteapp.SetImage
import com.sues.noteapp.entity.Note
import com.sues.noteapp.getPathFromUri
import com.sues.noteapp.ui.theme.*
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@Composable
fun AddNote(
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    context: Context,
    contentResolver: ContentResolver,
    imagePathUri: Uri?
) {
    val (title, changeTitle) = remember {
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
    // 选中颜色
    val selectedColor = remember {
        mutableStateOf(SelectedColor.Color1)
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            SheetContent(
                scope,
                scaffoldState,
                context,
                selectedColor
            )
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
                navController = navController,
            ) {
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
                            noteText = noteContent,
                            dateTime = dateTime,
                            selectedColor = selectedColor.value,
                            // Fixme: 添加imagePath
                            imagePath = getPathFromUri(
                                imageUri = imagePathUri,
                                contentResolver = contentResolver
                            )
                        )
                    )
                    // 添加完一个note将imageUri设置为null,这样在点击添加按钮时就不会出现上一次的图片情况发生了
                    noteViewModel.imageUri.value = null
                    navController.navigate(
                        route = "search",
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(route = "search", inclusive = true)
                            .build()
                    )
                    Log.i("addNote", noteViewModel.noteList.value?.size.toString())
                }
            }
        }) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp)
        ) {

            item {
                //AddNoteTitle(title = title, onValueChange = changeTitle)
                AddNoteTitle(
                    title = title,
                    selectedColor = selectedColor.value,
                    onValueChange = changeTitle
                )
                // Fixme:时间信息显示
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.96f)
                        .padding(top = 5.dp, bottom = 5.dp)
                ) {
                    Text(text = dateTime, color = colorIcons)
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                // Fixme: 添加图片布局
                if (imagePathUri != null) {
                    SetImage(
                        imagePath = getPathFromUri(
                            imageUri = imagePathUri,
                            contentResolver = contentResolver
                        )!!,
                        noteViewModel = noteViewModel
                    )
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

@ExperimentalMaterialApi
@Composable
fun SheetContent(
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    context: Context,
    selectedColor: MutableState<SelectedColor>
) {
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
            SelectedColorChangeByOnClick(
                selectedColor = selectedColor.value,
                currentColor = SelectedColor.Color1,
                onClick = {
                    if (selectedColor.value != SelectedColor.Color1) {
                        selectedColor.value = SelectedColor.Color1
                    }
                }
            )
            SelectedColorChangeByOnClick(
                selectedColor = selectedColor.value,
                currentColor = SelectedColor.Color2,
                onClick = {
                    if (selectedColor.value != SelectedColor.Color2) {
                        selectedColor.value = SelectedColor.Color2
                    }
                }
            )
            SelectedColorChangeByOnClick(
                selectedColor = selectedColor.value,
                currentColor = SelectedColor.Color3,
                onClick = {
                    if (selectedColor.value != SelectedColor.Color3) {
                        selectedColor.value = SelectedColor.Color3
                    }
                }
            )
            SelectedColorChangeByOnClick(
                selectedColor = selectedColor.value,
                currentColor = SelectedColor.Color4,
                onClick = {
                    if (selectedColor.value != SelectedColor.Color4) {
                        selectedColor.value = SelectedColor.Color4
                    }
                }
            )
            SelectedColorChangeByOnClick(
                selectedColor = selectedColor.value,
                currentColor = SelectedColor.Color5,
                onClick = {
                    if (selectedColor.value != SelectedColor.Color5) {
                        selectedColor.value = SelectedColor.Color5
                    }
                }
            )
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
                    // Fixme: 底部导航栏添加图片功能
                    scope.launch {
                        scaffoldState.bottomSheetState.collapse()
                    }
                    // Todo: 权限的获取需要注意一下
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        (context as? MainActivity)?.getPermission()
                    } else { // 已经授权
                        (context as? MainActivity)?.selectImage()
                    }
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = null,
                tint = colorWhite
            )
            Text(
                text = "添加图片",
                color = colorWhite
            )
        }
    }
}

@Composable
private fun SelectedColorChangeByOnClick(
    selectedColor: SelectedColor,
    currentColor: SelectedColor,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_done),
            contentDescription = null,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(35.dp)
                )
                .background(currentColor.color)
                .size(40.dp)
                .padding(5.dp),
            tint = if (selectedColor == currentColor) colorWhite else currentColor.color
        )
    }
}


@ExperimentalMaterialApi
@Composable
fun AddNoteTopBar(
    navController: NavHostController,
    onClick: () -> Unit
) {
    TopAppBar {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AddNoteBackButton {
                navController.navigate(
                    route = "search",
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(route = "search", inclusive = true)
                        .build()
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.85f))
            AddNoteDoneButton(onClick = onClick)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun NoteItem(
    note: Note,
    navController: NavHostController,
    deletedState: MutableState<Boolean>
) {
    // 颜色数据下沉到最后一个Composable函数中
    val backgroundColor = note.selectedColor.color
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .alpha(1f)
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .combinedClickable(
                onLongClick = {
                    // 显示删除选框
                    deletedState.value = true
                }
            ) {
                if (!deletedState.value) { // 非删除状态下才可以跳转
                    navController.navigate(route = "editNote")
                } else {
                    deletedState.value = false
                }
            }
    ) {
        Box {
            Card(
                backgroundColor = backgroundColor,
                contentColor = colorWhite,
                shape = RoundedCornerShape(10.dp),
            ) {
                if (deletedState.value) {
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .align(
                                alignment = Alignment.TopEnd
                            )
                            .border(
                                width = 1.dp,
                                color = colorNoteColor2,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .size(22.dp)
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(colorNoteColor2)
                        //.paint(painter = painterResource(id = R.drawable.ic_done))
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    // Fixme: 设置搜索界面图片
                    if (note.imagePath != null) {
                        Image(
                            bitmap = BitmapFactory.decodeFile(note.imagePath).asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (note.title != "") {
                        Text(
                            text = note.title!!,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            color = colorWhite,
                            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp)
                        )
                    }
                    Text(
                        text = note.noteText,
                        maxLines = 2,
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AddNoteTitle(
    title: String,
    selectedColor: SelectedColor,
    onValueChange: (String) -> Unit
) {
    val backgroundColor = selectedColor.color
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
                .background(backgroundColor)
        )
        Spacer(
            modifier = Modifier
                .width(13.dp)
        )
        OutlinedTextField(
            value = title,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = "Note Title",
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
}

@Composable
fun AddNoteContent(noteContent: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = noteContent,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Note content",
                color = colorIcons,
                fontWeight = FontWeight.Bold
            )
        },
        modifier = Modifier
            .fillMaxWidth(0.95f),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
    )
}

@ExperimentalMaterialApi
@Composable
fun AddNoteDoneButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
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

@Composable
fun AddNoteBackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 13.dp)
        )
    }
}

