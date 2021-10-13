package com.example.management.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 添加图片工具类
 */
public class ReadImgToBinary2 {

    // 将图片转为Base64
    public static String imgToBase64(String imgPath) {
        Bitmap bitmap = null;
        if (imgPath != null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath);
        }
        if (bitmap == null) {
            //bitmap not found!!
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }

    }

    public static String getPathFromUri(Uri imageUri, ContentResolver contentResolver) {
        if (imageUri == null) {
            // 没有选择图片
            return null;
        }
        // fixme: filepath可以这样定义
        String filePath;
        Cursor cursor = contentResolver.query(imageUri, null, null, null, null);
        if (cursor == null) {
            filePath = imageUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

}