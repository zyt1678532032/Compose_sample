package com.example.test

import android.graphics.Bitmap
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun androidPicksElement(modifier: Modifier) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            ) {
                cicleImage(modifier = Modifier)
            }
            Text(
                text = "Cupcake",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "A tag line",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        }
    }
}

@Composable
fun header() {

}

/**
 *  圆形图片
 */
@Composable
fun cicleImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.placeholder),
        contentDescription = null,
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun test() {
    Surface(
        modifier = Modifier.width(100.dp)
    ) {
        Column {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.animateContentSize { initialValue, targetValue ->

                }
            ) {
                Text(
                    text = "button"
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
            ) {
                Text(
                    text = "button2"
                )
            }
        }

    }
}

@Preview
@Composable
fun testAnimator() {
}





