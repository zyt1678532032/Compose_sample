package com.sues.noteapp.component

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.sues.noteapp.R // app图片等资源
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
fun AddNoteTopBar(
    noteContent: String,
    title: String,
    subTitle: String,
    dateTime: String,
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    selectedColor: SelectedColor,
    contentResolver: ContentResolver,
    imagePathUri: Uri?
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
            AddNoteDoneButton {
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
                            dateTime = dateTime,
                            color = selectedColor,
                            // Fixme: 添加imageURi
                            imagePath = getPathFromUri(
                                imageUri = imagePathUri!!,
                                contentResolver = contentResolver
                            )
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
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    selectedColor: SelectedColor,
    imagePathUri: Uri?,
    contentResolver: ContentResolver
) {
    // 颜色数据下沉到最后一个Composable函数中
    val backgroundColor =
        when (selectedColor) { // 枚举类
            SelectedColor.Color1 -> colorPrimaryDark
            SelectedColor.Color2 -> colorNoteColor2
            SelectedColor.Color3 -> colorNoteColor3
            SelectedColor.Color4 -> colorNoteColor4
            SelectedColor.Color5 -> colorNoteColor5
        }
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .alpha(1f)
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Card(
            backgroundColor = backgroundColor,
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
                // Fixme: 设置搜索界面图片
                Log.i("imagePathUri",imagePathUri.toString())
                if (note.imagePath != null) {
                    val inputStream = contentResolver.openInputStream(imagePathUri!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    Image(
                        bitmap = BitmapFactory.decodeFile(note.imagePath).asImageBitmap(),
                        contentDescription = null,
                    )
                }
                Text(text = note.subTitle ?: "", fontWeight = FontWeight.Bold, color = colorIcons)
                Text(text = note.noteText, maxLines = 3)
            }
        }
    }
}

@Composable
fun AddNoteSubTitle(
    subTitle: String,
    selectedColor: SelectedColor,
    onValueChange: (String) -> Unit
) {
    val backgroundColor =
        when (selectedColor) { // 枚举类
            SelectedColor.Color1 -> colorPrimaryDark
            SelectedColor.Color2 -> colorNoteColor2
            SelectedColor.Color3 -> colorNoteColor3
            SelectedColor.Color4 -> colorNoteColor4
            SelectedColor.Color5 -> colorNoteColor5
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
                .background(backgroundColor)
        )
        Spacer(
            modifier = Modifier
                .width(13.dp)
        )
        OutlinedTextField(
            value = subTitle,
            onValueChange = onValueChange,
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

@Composable
fun AddNoteTitle(title: String, onValueChange: (String) -> Unit) {
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

