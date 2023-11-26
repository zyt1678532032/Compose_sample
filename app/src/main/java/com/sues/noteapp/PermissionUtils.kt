package com.sues.noteapp

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionUtils {

    fun checkSelfPermission(
        context: Context,
        permission: String,
        imagePathResult: (path: String?) -> Unit
    ) {
        if (!hasPermission(context, permission)) {
            // if (ActivityCompat.shouldShowRequestPermissionRationale(
            //         context as Activity,
            //         permission
            //     )
            // ) {
            //     Toast.makeText(context, "该权限已被用户选择了不再询问！", Toast.LENGTH_SHORT).show()
            // } else {
            //     Toast.makeText(context, "权限未被授予！", Toast.LENGTH_SHORT).show()
            // }
            // FIXME:
            (context as? MainActivity)?.getPermission()
            (context as? MainActivity)?.selectImage(imagePathResult)
        } else { // 已经授权
            // FIXME:
            (context as? MainActivity)?.selectImage(imagePathResult)
        }
    }

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}