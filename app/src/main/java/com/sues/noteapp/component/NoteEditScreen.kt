package com.sues.noteapp.component

import android.Manifest
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.sues.noteapp.ImageUtils
import com.sues.noteapp.PermissionUtils
import com.sues.noteapp.R
import com.sues.noteapp.data.local.Note
import com.sues.noteapp.ui.theme.SelectedColor
import com.sues.noteapp.ui.theme.colorIcons
import com.sues.noteapp.ui.theme.colorMiscellaneousBackground
import com.sues.noteapp.ui.theme.colorWhite
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalMaterialApi
@Composable
fun EditNoteScreen(
    navController: NavHostController,
    noteViewModel: NoteViewModel,
) {
    val state = noteViewModel.uiState

    Log.i("MainActivity", "当前的note: ${state.clickedNote}")
    val (title, changeTitle) = remember {
        val title = state.clickedNote?.title ?: ""
        mutableStateOf(title)
    }
    val (noteContent, changeContent) = remember {
        val noteContent = state.clickedNote?.noteText ?: ""
        mutableStateOf(noteContent)
    }
    var imagePath by remember {
        mutableStateOf(state.clickedNote?.imagePath)
    }
    // 选中颜色
    val selectedColor = remember {
        val color = state.clickedNote?.selectedColor ?: SelectedColor.Color1
        mutableStateOf(color)
    }

    val dateTime by remember {
        val time = SimpleDateFormat("yyyy MMMM dd HH:MM, EEEE", Locale.getDefault()).format(Date())
        mutableStateOf(time)
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = noteViewModel::onPhotoPickerSelect
    )

    BottomSheetScaffold(
        sheetContent = {
            SheetContent(
                scope,
                scaffoldState,
                selectedColor
            ) {
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        },
        sheetPeekHeight = 60.dp,
        sheetBackgroundColor = colorMiscellaneousBackground,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 5.dp,
        scaffoldState = scaffoldState,
        snackbarHost = {// 提示框
            SnackbarHost(it) { data ->
                Snackbar(
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colors.secondary
                    ),
                    snackbarData = data,
                    backgroundColor = colorWhite
                )
            }
        },
        topBar = {
            AddNoteTopBar(
                onBack = {
                    navController.navigate(
                        route = Screen.MainScreen.name,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(route = Screen.MainScreen.name, inclusive = true)
                            .build()
                    )
                },
                onDone = {
                    if (noteContent.isEmpty()) {
                        scope.launch {
                            scaffoldState.snackbarHostState
                                .showSnackbar(message = "笔记内容不能为空!")
                        }
                    } else {
                        if (state.clickedNote != null) {
                            noteViewModel.updateNote(
                                state.clickedNote.copy(
                                    title = title,
                                    noteText = noteContent,
                                    dateTime = dateTime,
                                    selectedColor = selectedColor.value,
                                    imagePath = imagePath
                                )
                            )
                        } else {
                            noteViewModel.insertNote(
                                Note(
                                    title = title,
                                    noteText = noteContent,
                                    dateTime = dateTime,
                                    selectedColor = selectedColor.value,
                                    imagePath = imagePath
                                )
                            )
                        }
                        // 添加完一个note将imageUri设置为null,这样在点击添加按钮时就不会出现上一次的图片情况发生了
                        navController.navigate(
                            route = Screen.MainScreen.name,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(route = Screen.MainScreen.name, inclusive = true)
                                .build()
                        )
                    }
                }
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
                AddNoteTitle(
                    title = title,
                    selectedColor = selectedColor.value,
                    onValueChange = changeTitle
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.96f)
                        .padding(top = 5.dp, bottom = 5.dp)
                ) {
                    Text(text = dateTime, color = colorIcons)
                }
                Spacer(modifier = Modifier.height(10.dp))
                imagePath?.let {
                    ImageCard(
                        imagePath = it,
                        onIconClick = {
                            imagePath = null
                        },
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
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
    selectedColor: MutableState<SelectedColor>,
    onImageAdd: () -> Unit
) {
    Box(
        modifier = Modifier
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
            SelectedColorCard(
                selectedColor = selectedColor,
                backgroundColor = SelectedColor.Color1,
            )
            SelectedColorCard(
                selectedColor = selectedColor,
                backgroundColor = SelectedColor.Color2,
            )
            SelectedColorCard(
                selectedColor = selectedColor,
                backgroundColor = SelectedColor.Color3,
            )
            SelectedColorCard(
                selectedColor = selectedColor,
                backgroundColor = SelectedColor.Color4,
            )
            SelectedColorCard(
                selectedColor = selectedColor,
                backgroundColor = SelectedColor.Color5,
            )
            Text(
                text = "选择颜色",
                fontWeight = FontWeight.Bold,
                color = colorWhite,
            )
        }
        // 添加图片
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clickable {
                    scope.launch {
                        scaffoldState.bottomSheetState.collapse()
                    }
                },
        ) {
            TextButton(onClick = onImageAdd) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_image),
                    contentDescription = null,
                    tint = colorWhite
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "添加图片", color = colorWhite)
            }
        }
    }
}

@Composable
private fun SelectedColorCard(
    selectedColor: MutableState<SelectedColor>,
    backgroundColor: SelectedColor,
) {
    IconButton(onClick = {
        selectedColor.value = backgroundColor
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_done),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(35.dp))
                .background(backgroundColor.color)
                .size(40.dp)
                .padding(5.dp),
            tint = isShowIconDone(selectedColor, backgroundColor)
        )
    }
}

private fun isShowIconDone(
    selectedColor: MutableState<SelectedColor>,
    backgroundColor: SelectedColor
): Color {
    return if (selectedColor.value == backgroundColor) {
        colorWhite
    } else {
        backgroundColor.color
    }
}

@Composable
fun AddNoteTopBar(
    onBack: () -> Unit,
    onDone: () -> Unit
) {
    TopAppBar {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 13.dp)
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.85f))
            IconButton(onClick = onDone) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_done),
                    contentDescription = null,
                    modifier = Modifier
                        .width(25.dp)
                        .height(24.dp)
                        .border(width = 2.dp, color = colorIcons, shape = RoundedCornerShape(30.dp))
                        .padding(5.dp)
                )
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
            modifier = Modifier.width(10.dp)
        )
        Spacer(
            modifier = Modifier
                .padding(top = 10.dp)
                .width(5.dp)
                .height(50.dp)
                .background(backgroundColor)
        )
        Spacer(
            modifier = Modifier.width(13.dp)
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
            modifier = Modifier.fillMaxWidth(0.96f),
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
        )
        Spacer(
            modifier = Modifier.width(5.dp)
        )
    }
}

@Composable
fun AddNoteContent(noteContent: String?, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = noteContent ?: "",
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Note content",
                color = colorIcons,
                fontWeight = FontWeight.Bold
            )
        },
        modifier = Modifier.fillMaxWidth(0.95f),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
    )
}

@Composable
fun ImageCard(imagePath: String, onIconClick: () -> Unit) {
    Box {
        Image(
            bitmap = BitmapFactory.decodeFile(imagePath).asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(15.dp))
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            IconButton(
                onClick = onIconClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}

