package com.sues.noteapp.component

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.sues.noteapp.SetImage
import com.sues.noteapp.data.local.Note
import com.sues.noteapp.ui.theme.colorIcons
import com.sues.noteapp.ui.theme.colorMiscellaneousBackground
import com.sues.noteapp.ui.theme.colorWhite
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalMaterialApi
@Composable
fun EditNote(
    note: Note,
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    context: Context
) {
    val (title, changeTitle) = remember { mutableStateOf(note.title) }
    val (noteContent, changeContent) = remember { mutableStateOf(note.noteText) }
    val dateTime by remember {
        val time = SimpleDateFormat("yyyy MMMM dd HH:MM a,EEEE", Locale.getDefault())
            .format(Date())
        mutableStateOf(time)
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    // 选中颜色
    val selectedColor = remember { mutableStateOf(note.selectedColor) }
    val imagePathState = remember { mutableStateOf<String?>(note.imagePath) }

    BottomSheetScaffold(
        sheetContent = {
            SheetContent(
                scope = scope,
                scaffoldState = scaffoldState,
                context = context,
                selectedColor = selectedColor
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
                note.selectedColor = selectedColor.value
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
                AddNoteTitle(
                    title = title!!,
                    selectedColor = selectedColor.value,
                    onValueChange = changeTitle
                )
                // Fixme:这里好像也有问题(时间信息显示)
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.96f)
                        .padding(top = 5.dp, bottom = 5.dp),
                    text = dateTime,
                    color = colorIcons
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Fixme: 添加图片布局
                SetImage(imagePath = imagePathState)
                Spacer(modifier = Modifier.height(10.dp))
                // 笔记内容体
                AddNoteContent(noteContent = noteContent, onValueChange = changeContent)
            }
        }
    }
}
