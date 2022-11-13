package com.sues.noteapp.component

import android.content.ContentResolver
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sues.noteapp.MainActivity
import com.sues.noteapp.SetImage
import com.sues.noteapp.entity.Note
import com.sues.noteapp.getPathFromUri
import com.sues.noteapp.ui.theme.colorIcons
import com.sues.noteapp.ui.theme.colorMiscellaneousBackground
import com.sues.noteapp.ui.theme.colorWhite
import com.sues.noteapp.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@Composable
fun EditNote(
    note: Note,
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    context: Context,
    activity: MainActivity,
    contentResolver: ContentResolver
) {
    val (title, changeTitle) = remember {
        mutableStateOf(note.title)
    }
    val (noteContent, changeContent) = remember {
        mutableStateOf(note.noteText)
    }
    val dateTime by remember {
        val time = SimpleDateFormat("yyyy MMMM dd HH:MM a,EEEE", Locale.getDefault())
            .format(Date())
        mutableStateOf(time)
    }

    // 选中颜色
    val selectedColor = remember {
        mutableStateOf(note.color)
    }
    val imagePath by remember {
        mutableStateOf(note.imagePath)
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            SheetContent(
                scope = scope,
                scaffoldState = scaffoldState,
                selectedColor = selectedColor,
                context = context,
                activity = activity
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
                note.color = selectedColor.value
                noteViewModel.updateNote(note)
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
                    title = title!!,
                    selectedColor = selectedColor.value,
                    onValueChange = changeTitle
                )
                // Fixme:这里好像也有问题(时间信息显示)
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
                if (imagePath != null || noteViewModel.imageUri.value != null) {
                    SetImage(
                        imagePath = (if (imagePath != null) imagePath else
                            noteViewModel.imageUri.value.let {
                                note.imagePath = getPathFromUri(it, contentResolver)
                                getPathFromUri(it, contentResolver)
                            }) as String,
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
