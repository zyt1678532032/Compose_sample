package com.sues.noteapp.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sues.noteapp.R
import com.sues.noteapp.entity.Note
import com.sues.noteapp.ui.theme.colorPrimaryDark
import com.sues.noteapp.ui.theme.colorSearchIcon
import com.sues.noteapp.ui.theme.colorTextHint
import com.sues.noteapp.ui.theme.colorWhite
import kotlin.math.ceil
import kotlin.math.max


@Composable
fun searchBottomAppBar() {
    BottomAppBar(contentPadding = PaddingValues(start = 10.dp, end = 10.dp)) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_outline),
                contentDescription = "添加",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = "添加图片",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_web_link),
                contentDescription = "添加浏览地址",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }

}

@Composable
fun searchContent(
    searchText: String,
    notes: List<Note>,
    onValueChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
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
            label = {
                Text(
                    text = "Search notes",
                    color = colorTextHint,
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.96f),
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = colorWhite)
        )
        StaggeredVerticalGrid(
            maxColumnWidth = 200.dp,
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .verticalScroll(ScrollState(0))
        ) {
            repeat(notes.size) {
                // Fixme:这里需要将backgroundColor变为动态选择
                noteItem(
                    note = notes[it],
                    backgroundColor = colorPrimaryDark
                )
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
fun test() {
    StaggeredVerticalGrid(
        maxColumnWidth = 220.dp,
    ){
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
