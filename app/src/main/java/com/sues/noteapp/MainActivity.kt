package com.sues.noteapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.sues.noteapp.component.NavGraph
import com.sues.noteapp.ui.theme.NoteAPPTheme
import com.sues.noteapp.viewModel.NoteViewModel

class MainActivity : ComponentActivity() {

    private val noteViewModel by viewModels<NoteViewModel>()
    private val imagePathState: MutableState<String?> = mutableStateOf(null)

    private val activityResultLauncher: ActivityResultLauncher<Void> =
        registerForActivityResult(object : ActivityResultContract<Void, Uri?>() {
            override fun createIntent(context: Context, input: Void): Intent {
                return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                return intent?.data
            }
        }) {
            if (it != null) {
                imagePathState.value = getPathFromUri(it, contentResolver)
            }
        }

    private val registerPermission: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionResult ->
            if (permissionResult) {
                // 授权通过
                selectImage()
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
                    context = this,
                    imagePathState = imagePathState
                )
            }
        }
    }

    fun getPermission() {
        // 申请权限
        registerPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun selectImage() {
        activityResultLauncher.launch(null)
    }

}


