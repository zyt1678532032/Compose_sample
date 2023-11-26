package com.sues.noteapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sues.noteapp.component.NavGraph
import com.sues.noteapp.data.NoteRepositoryImpl
import com.sues.noteapp.ui.theme.NoteAPPTheme
import com.sues.noteapp.viewModel.NoteViewModel

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(
                        noteRepository = NoteRepositoryImpl(
                            localDatasource = application.noteDatabase.noteDao(),
                            contentResolver = contentResolver
                        )
                    ) as T
                }
            }
        }
    )

    private lateinit var resultCallback: (path: String?) -> Unit

    private val imagePickerLauncher: ActivityResultLauncher<Void?> =
        registerForActivityResult(object : ActivityResultContract<Void?, Uri?>() {
            override fun createIntent(context: Context, input: Void?): Intent {
                return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                return intent?.data
            }
        }) {
            if (it != null) {
                val imagePath = ImageUtils.getPathFromUri(it, contentResolver)
                resultCallback(imagePath)
                Log.i("MainActivity", "imagePath: $imagePath")
            }
        }

    private val permissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionResult ->
            if (permissionResult) {
                // 授权通过
                Toast.makeText(this@MainActivity, "授权通过", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this@MainActivity, "申请权限失败", Toast.LENGTH_SHORT).show()
            }
        }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAPPTheme {
                NavGraph(
                    noteViewModel = noteViewModel,
                )
            }
        }
    }

    fun getPermission() {
        // 申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    fun selectImage(resultCallback: (path: String?) -> Unit) {
        this.resultCallback = resultCallback
        imagePickerLauncher.launch(null)
    }

}


