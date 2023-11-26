package com.sues.noteapp

import android.content.ContentResolver
import android.net.Uri

object ImageUtils {
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
}