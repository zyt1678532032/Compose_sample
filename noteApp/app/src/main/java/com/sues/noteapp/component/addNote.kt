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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sues.noteapp.ui.theme.colorIcons
import com.sues.noteapp.R // app图片等资源
import com.sues.noteapp.ui.theme.colorNoteColor2

@Composable
fun topAppbar(navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigate("search") }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 13.dp)
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth(0.85f))
        IconButton(
            onClick = { /*TODO*/ },
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
            value = contentTitle,
            onValueChange = {
                contentTitle = it
            },
            placeholder = { Text(text = "Note subTitle") },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(150.dp),
            shape = RoundedCornerShape(10.dp)
        )
    }

}