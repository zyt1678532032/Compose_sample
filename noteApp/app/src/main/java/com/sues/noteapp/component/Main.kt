package com.sues.noteapp.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sues.noteapp.R


@Composable
fun bottomAppBar() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_outline),
            contentDescription = "添加",
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_image),
            contentDescription = "添加图片",
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_web_link),
            contentDescription = "添加浏览地址",
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}