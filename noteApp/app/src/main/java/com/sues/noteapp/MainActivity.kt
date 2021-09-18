package com.sues.noteapp

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sues.noteapp.component.*
import com.sues.noteapp.ui.theme.*
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>()
    private var dataBase: NoteDataBase? = null

    companion object {
        // 获取权限
        const val REQUEST_CODE_STORAGE_PERMISSION = 1
        const val REQUEST_CODE_SELECT_IMAGE = 2
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen()
        // dataBase = NoteDataBase.getDataBase(applicationContext)

        setContent {
            val imageUriState: Uri? by noteViewModel.imageUri.observeAsState()

            NoteAPPTheme {
                NavGraph(
                    noteViewModel = noteViewModel,
                    context = applicationContext,
                    activity = this,
                    contentResolver = contentResolver,
                    imagePathUri = imageUriState
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage()
            } else {
                Toast.makeText(this, "申请权限失败!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        if (intent.resolveActivity(packageManager) != null) {
//             startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE)
//            //registerForActivityResult()
//        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            // && requestCode == RESULT_OK
            if (data != null) {
                val imageUri = data.data
                Log.i("Uri", imageUri.toString())
                if (imageUri != null) {
                    noteViewModel.imageUri.value = imageUri
                }
            }
        }
    }
}

fun getPathFromUri(imageUri: Uri?, contentResolver: ContentResolver): String? {
    // fixme: filepath可以这样定义
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

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun NavGraph(
    noteViewModel: NoteViewModel,
    context: Context,
    activity: MainActivity,
    imagePathUri: Uri?,
    contentResolver: ContentResolver
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = "search") {
        composable("search") {
            Search(
                navController = navController,
                noteViewModel = noteViewModel,
            )
        }
        composable(
            route = "addNote",
        ) {
            AddNote(
                navController = navController,
                noteViewModel = noteViewModel,
                context = context,
                activity = activity,
                imagePathUri = imagePathUri,
                contentResolver = contentResolver
            )
        }
    }
}


@Composable
fun SetImage(imagePathUri: Uri, contentResolver: ContentResolver, noteViewModel: NoteViewModel) {
    val inputStream = contentResolver.openInputStream(imagePathUri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    Box {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(15.dp))
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            IconButton(
                onClick = {
                    // 删除选择的图片
                    noteViewModel.imageUri.value = null
                },
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

@Preview(showSystemUi = true)
@Composable
fun test() {
    Box {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Cyan)
        )
        Box(
            Modifier
                .matchParentSize()
                .padding(top = 20.dp, bottom = 20.dp)
                .background(Color.Yellow)
        )
        Box(
            Modifier
                .matchParentSize()
                .padding(40.dp)
                .background(Color.Magenta)
        )
        Box(
            Modifier
                .align(Alignment.Center)
                .size(300.dp, 300.dp)
                .background(Color.Green)
        )
        Box(
            Modifier
                .align(Alignment.TopStart)
                .size(150.dp, 150.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .paint(painterResource(id = R.drawable.ic_delete))
        )
    }
}

