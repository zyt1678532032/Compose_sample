package com.sues.noteapp.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp


data class Item(
    val name: String,
    val startTime: String,
    val station: String,
    val endTime: String,
    val project: String,
    val result: String
)

@Preview
@Composable
fun Test() {
    LazyColumn {
        items(
            count = 7, itemContent = { index ->
                ResultItem(
                    item = items[index]
                )
            }
        )
    }
}

@Composable
fun ResultItem(
    item: Item
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            RowItem(element1 = "姓名：", element2 = item.name)
            RowItem(element1 = "采样时间：", element2 = item.startTime)
            RowItem(element1 = "检查机构：", element2 = item.station)
            RowItem(element1 = "检测时间：", element2 = item.endTime)
            RowItem(element1 = "检测项目：", element2 = item.project)
            RowItem(element1 = "检测结果：", element2 = item.result)
        }
    }
}


@OptIn(ExperimentalUnitApi::class)
@Composable
fun RowItem(
    element1: String,
    element2: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
    ) {
        Text(
            text = element1,
            fontSize = TextUnit(16F, TextUnitType.Sp),
            modifier = Modifier.padding(start = 5.dp)
        )
        Text(
            text = element2,
            fontSize = TextUnit(16F, TextUnitType.Sp),
            fontWeight = FontWeight(500),
            color = if (element2 == "【阴性】") Color(0xFF4CAF50) else Color.Black,
            modifier = Modifier.padding(end = if (element2 == "【阴性】") 0.dp else 10.dp)
        )
    }
}


val items = listOf<Item>(
    Item(
        name = "**通",
        startTime = "2022-11-12 17:51:33",
        station = "上海伯豪医学检验所 (方舱)",
        endTime = "2022-11-12 20:14:48",
        project = "核酸",
        result = "【阴性】",
    ),
    Item(
        name = "**通",
        startTime = "2022-11-11 14:50:12",
        station = "上海伯豪医学检验所 (方舱)",
        endTime = "2022-11-11 19:14:28",
        project = "核酸",
        result = "【阴性】",
    ),
    Item(
        name = "**通",
        startTime = "2022-11-10 12:44:12",
        station = "上海伯豪医学检验所 (方舱)",
        endTime = "2022-11-10 21:37:25",
        project = "核酸",
        result = "【阴性】",
    ),
    Item(
        name = "**通",
        startTime = "2022-11-09 16:50:46",
        station = "上海伯豪医学检验所 (方舱)",
        endTime = "2022-11-09 22:02:55",
        project = "核酸",
        result = "【阴性】",
    ),
    Item(
        name = "**通",
        startTime = "2022-11-08 15:05:12",
        station = "上海伯豪医学检验所 (方舱)",
        endTime = "2022-11-08 20:21:01",
        project = "核酸",
        result = "【阴性】",
    ),
    Item(
        name = "**通",
        startTime = "2022-11-07 15:50:12",
        station = "上海千麦博米乐医学检验所",
        endTime = "2022-11-07 21:04:37",
        project = "核酸",
        result = "【阴性】",
    ),
    Item(
        name = "**通",
        startTime = "2022-11-06 14:33:09",
        station = "上海思路迪医学检验所 (闵行)",
        endTime = "2022-11-06 20:12:28",
        project = "核酸",
        result = "【阴性】",
    )
)