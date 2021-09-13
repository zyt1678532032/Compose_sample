package com.sues.noteapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.sues.noteapp.ui.theme.colorIcons
import com.sues.noteapp.R // app图片等资源
import com.sues.noteapp.ui.theme.colorNoteColor2
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun topAppbar(navController: NavHostController) {

}

@Composable
fun addNoteContent(
    inputs: List<String>
) {
    var title by remember {
        mutableStateOf(inputs[0])
    }
    var subTitle by remember {
        mutableStateOf(inputs[1])
    }
    var contentTitle by remember {
        mutableStateOf(inputs[2])
    }


}