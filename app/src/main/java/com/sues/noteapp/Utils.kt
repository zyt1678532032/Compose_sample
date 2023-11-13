package com.sues.noteapp

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

fun getPathFromUri(imageUri: Uri?, contentResolver: ContentResolver): String? {
    if (imageUri == null) {
        // 没有选择图片
        return null
    }
    val filePath: String
    val cursor = contentResolver.query(imageUri, null, null, null, null)
    if (cursor == null) {
        filePath = imageUri.path!!
    } else {
        cursor.moveToFirst()
        val index = cursor.getColumnIndex("_data")
        filePath = cursor.getString(index)
        cursor.close()
    }
    return filePath
}


@Composable
fun SetImage(imagePath: String, onIconClick: () -> Unit) {
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
