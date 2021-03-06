package com.sues.noteapp

import android.Manifest
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
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.sues.noteapp.component.*
import com.sues.noteapp.entity.Note
import com.sues.noteapp.ui.theme.*
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>()
    lateinit var registerForActivityResult: ActivityResultLauncher<Void>
    lateinit var registerPermission: ActivityResultLauncher<String>

    companion object {
        // ????????????
        const val REQUEST_CODE_STORAGE_PERMISSION = 1
        const val REQUEST_CODE_SELECT_IMAGE = 2
    }

    @OptIn(
        ExperimentalFoundationApi::class,
        ExperimentalAnimationApi::class,
        ExperimentalMaterialApi::class
    )
    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // installSplashScreen()
        // TODO: 2021/9/23 ???????????????????????????????????????
        registerForActivityResult =
            registerForActivityResult(object : ActivityResultContract<Void, Uri>() {
                override fun createIntent(context: Context, input: Void?): Intent {
                    return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                }

                override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                    Log.i("parseResult", resultCode.toString())
                    return intent!!.data
                }
            }) {
                if (it != null) {
                    noteViewModel.imageUri.value = it
                }
            }
        // TODO: 2021/9/23 ?????????????????????????????????
        registerPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionResult ->
                if (permissionResult) {
                    // ????????????
                    selectImage()
                } else {
                    Toast.makeText(this@MainActivity, "??????????????????", Toast.LENGTH_SHORT).show()
                }
            }

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

    fun getPermission() {
        // ????????????
        registerPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun selectImage() {
        registerForActivityResult.launch(null)
    }

}

fun getPathFromUri(imageUri: Uri?, contentResolver: ContentResolver): String? {
    if (imageUri == null) {
        // ??????????????????
        return null
    }
    // fixme: filepath??????????????????
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

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NavGraph(
    noteViewModel: NoteViewModel,
    context: Context,
    activity: MainActivity,
    imagePathUri: Uri?,
    contentResolver: ContentResolver
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {
        composable(route = "search") {
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
        composable(route = "editNote") { entry ->
//            val noteString = entry.arguments!!.getString("note")
//            val jsonObject = JSONObject(noteString!!)
//
//            val title = jsonObject.getString("title")
//            val noteText = jsonObject.getString("noteText")
//            val imagePath = jsonObject.getString("imagePath")
//            val color = jsonObject.getString("color")
//            val id = jsonObject.getString("id")
            val note = Note(
                id = 1,
                title = "title1",
                noteText = "??????hi???????????????dsada",
                dateTime = "2021 9-13"
            )
            EditNote(
                note = note,
                navController = navController,
                noteViewModel = noteViewModel,
                context = context,
                activity = activity,
                contentResolver = contentResolver
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun EditNote(
    note: Note,
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    context: Context,
    activity: MainActivity,
    contentResolver: ContentResolver
) {
    val (title, changeTitle) = remember {
        mutableStateOf(note.title)
    }
    val (noteContent, changeContent) = remember {
        mutableStateOf(note.noteText)
    }
    val dateTime by remember {
        val time = SimpleDateFormat("yyyy MMMM dd HH:MM a,EEEE", Locale.getDefault())
            .format(Date())
        mutableStateOf(time)
    }

    // ????????????
    val selectedColor = remember {
        mutableStateOf(note.color)
    }
    val imagePath by remember {
        mutableStateOf(note.imagePath)
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            SheetContent(
                scope = scope,
                scaffoldState = scaffoldState,
                selectedColor = selectedColor,
                context = context,
                activity = activity
            )
        },
        sheetPeekHeight = 60.dp,
        sheetBackgroundColor = colorMiscellaneousBackground,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 5.dp,
        scaffoldState = scaffoldState,
        snackbarHost = {// ?????????
            SnackbarHost(it) { data ->
                // custom snackbar with the custom border
                Snackbar(
                    modifier = Modifier.border(2.dp, MaterialTheme.colors.secondary),
                    snackbarData = data,
                    backgroundColor = colorWhite
                )
            }
        },
        topBar = {
            AddNoteTopBar(
                navController = navController,
            ) {
                note.color = selectedColor.value
                noteViewModel.updateNote(note)
            }
        }) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp)
        ) {

            item {
                //AddNoteTitle(title = title, onValueChange = changeTitle)
                AddNoteTitle(
                    title = title!!,
                    selectedColor = selectedColor.value,
                    onValueChange = changeTitle
                )
                // Fixme:????????????????????????(??????????????????)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.96f)
                        .padding(top = 5.dp, bottom = 5.dp)
                ) {
                    Text(text = dateTime, color = colorIcons)
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                // Fixme: ??????????????????
                if (imagePath != null || noteViewModel.imageUri.value != null) {
                    SetImage(
                        imagePath = (if (imagePath != null) imagePath else
                            noteViewModel.imageUri.value.let {
                                note.imagePath = getPathFromUri(it, contentResolver)
                                getPathFromUri(it, contentResolver)
                            }) as String,
                        noteViewModel = noteViewModel
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                // ???????????????
                AddNoteContent(noteContent = noteContent, onValueChange = changeContent)
            }
        }
    }
}


@Composable
fun SetImage(imagePath: String, noteViewModel: NoteViewModel) {

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
                onClick = {
                    // Fixme:?????????????????????
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

fun main() {
    val jsonObject = JSONObject()
    jsonObject.put("name", "zyt")
    println(jsonObject)
    println("dsad")
}

@Preview(showSystemUi = true, group = "Test")
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

