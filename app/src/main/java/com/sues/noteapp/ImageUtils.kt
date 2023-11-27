package com.sues.noteapp

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
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

    fun centerCrop(source: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val sourceWidth = source.width
        val sourceHeight = source.height

        // 计算缩放比例
        val scale = if (sourceWidth * newHeight > newWidth * sourceHeight) {
            newHeight.toFloat() / sourceHeight.toFloat()
        } else {
            newWidth.toFloat() / sourceWidth.toFloat()
        }

        // 计算裁剪后的宽高
        val scaledWidth = Math.round(sourceWidth * scale)
        val scaledHeight = Math.round(sourceHeight * scale)

        // 计算左上角坐标
        val left = (newWidth - scaledWidth) / 2
        val top = (newHeight - scaledHeight) / 2

        // 创建目标 Bitmap
        val result = Bitmap.createBitmap(newWidth, newHeight, source.config)

        // 创建 Canvas，并绘制裁剪后的 Bitmap
        val canvas = Canvas(result)
        val srcRect = Rect(0, 0, sourceWidth, sourceHeight)
        val destRect = Rect(left, top, left + scaledWidth, top + scaledHeight)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.drawBitmap(source, srcRect, destRect, paint)

        return result
    }
}