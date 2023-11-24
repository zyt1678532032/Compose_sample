package com.sues.noteapp.component

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.sues.noteapp.R
import com.sues.noteapp.data.local.Note
import com.sues.noteapp.ui.theme.SelectedColor
import com.sues.noteapp.ui.theme.colorSearchIcon
import com.sues.noteapp.ui.theme.colorTextHint
import com.sues.noteapp.ui.theme.colorWhite
import com.sues.noteapp.viewModel.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.max

@Composable
fun MainScreen(
    navController: NavHostController,
    noteViewModel: NoteViewModel,
) {
    LaunchedEffect(Unit) {
        noteViewModel.loadNotes()
    }
    val (searchText, searchTextChanged) = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val state = noteViewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    text = "My Note",
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { SearchBottomAppBar(scope, snackbarHostState) },
        floatingActionButton = {
            Row {
                FloatingActionButton(
                    onClick = {
                        noteViewModel.setCurrentNote(null)
                        navController.navigate(
                            route = Screen.AddNoteScreen.name,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(route = Screen.AddNoteScreen.name, inclusive = true)
                                .build()
                        )
                    },
                    backgroundColor = SelectedColor.Color2.color,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                    )
                }
            }
        }
    ) {
        SearchContent(
            padding = it,
            searchText = searchText,
            notes = state.notes,
            navController = navController,
            onValueChange = searchTextChanged,
            noteViewModel = noteViewModel
        )
    }
}

@Composable
fun SearchBottomAppBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    BottomAppBar(contentPadding = PaddingValues(start = 10.dp, end = 10.dp)) {
        IconButton(onClick = {
            scope.launch {
                snackbarHostState.showSnackbar("function not support")
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_outline),
                contentDescription = "添加",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        IconButton(onClick = {
            scope.launch {
                snackbarHostState.showSnackbar("function not support")
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = "添加图片",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        IconButton(onClick = {
            scope.launch {
                snackbarHostState.showSnackbar("function not support")
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_web_link),
                contentDescription = "添加浏览地址",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun SearchContent(
    padding: PaddingValues,
    searchText: String,
    notes: List<Note>?,
    navController: NavHostController,
    onValueChange: (String) -> Unit,
    noteViewModel: NoteViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = padding)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = colorSearchIcon
                )
            },
            label = { Text(text = "Search notes", color = colorTextHint) },
            modifier = Modifier.fillMaxWidth(0.96f),
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
        )
        StaggeredVerticalGrid(
            maxColumnWidth = 200.dp,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 70.dp)
                .verticalScroll(ScrollState(0))
        ) {
            notes?.forEach { note ->
                NoteItem(
                    note = note,
                    navController = navController,
                    noteViewModel = noteViewModel
                )
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    navController: NavHostController,
    noteViewModel: NoteViewModel,
) {
    // 颜色数据下沉到最后一个Composable函数中
    val backgroundColor = note.selectedColor.color
    // 控制右上角的删除框是否显示
    var isDeleted by remember {
        mutableStateOf(false)
    }
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .alpha(1f)
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        // 显示删除选框
                        isDeleted = true
                    },
                    onTap = {
                        if (!isDeleted) { // 非删除状态下才可以跳转
                            noteViewModel.setCurrentNote(note = note)
                            navController.navigate(route = Screen.EditNoteScreen.name)
                        } else {
                            isDeleted = false
                        }
                    }
                )
            }
    ) {
        Box {
            Card(
                backgroundColor = backgroundColor,
                contentColor = colorWhite,
                shape = RoundedCornerShape(10.dp),
            ) {
                if (isDeleted) {
                    IconButton(
                        onClick = { isDeleted = false },
                        modifier = Modifier
                            .align(alignment = Alignment.TopEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(35.dp))
                                .background(Color.White)
                                .border(width = 2.dp, color = Color.White)
                                .size(20.dp),
                            tint = if (isDeleted) Color.Black else note.selectedColor.color
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    note.imagePath?.let {
                        Image(
                            bitmap = BitmapFactory.decodeFile(it).asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Text(
                        text = note.title ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = colorWhite,
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp)
                    )
                    Text(
                        text = note.noteText ?: "",
                        maxLines = 2,
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}


// 交错网格布局
@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns

        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

// 网格组件
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        // Keep track of the width of each row
        val rowWidths = IntArray(rows) { index ->
            println("index:$index")
            0
        }

        // Keep track of the max height of each row
        val rowHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->
            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {// TODO: 2021/8/17 一个语法糖:等价于1.until(rows)
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        // Set the size of the parent layout
        layout(width, height) {
            // x co-ord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
@Preview
fun TestStaggeredVerticalGrid() {
    StaggeredVerticalGrid(
        maxColumnWidth = 220.dp,
    ) {
        Text(
            text = "dsadsa"
        )
        Text(
            text = "dsadsa"
        )
        Text(
            text = "dsadsa"
        )
        Text(
            text = "dsadsa"
        )
        Text(
            text = "dsadsa"
        )
        Text(
            text = "dsadsa"
        )
        Text(
            text = "dsadsa"
        )
    }
}
