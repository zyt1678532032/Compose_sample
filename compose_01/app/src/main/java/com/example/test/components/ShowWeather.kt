package com.example.test.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test.R

@Preview(showSystemUi = true)
@Composable
fun test() {
    Card {
        Image(
            painter = painterResource(id = R.drawable.bg_partly_cloudy_day),
            contentDescription = null,
            modifier = Modifier
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "北京市",
                color = Color.White,
                style = TextStyle(
                    fontSize = 25.sp
                ),
                modifier = Modifier.padding(top = 120.dp)
            )
            Text(
                text = "10     C",
                color = Color.White,
                style = TextStyle(
                    fontSize = 55.sp
                ),
                modifier = Modifier.padding(top = 50.dp)
            )
            Text(
                text = "晴 | 空气质量指数26",
                color = Color.White,
                style = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}
