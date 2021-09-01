package com.example.test.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test.R
import com.example.test.components.theme.WeatherTypography

@Composable
fun SearchWeatherByAddress() {
    // 搜索框输入的地址
    var address by remember {
        mutableStateOf("")
    }

    Scaffold {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                },
                label = {
                    Text(text = "输入地址")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.bg_place),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(4) {
                        searchItem()
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun searchItem() {
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 10.dp,
                top = 20.dp,
                bottom = 20.dp,
                end = 50.dp
            )
        ) {
            Text(
                text = "北京市",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                ),
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "中国北京市",
                style = TextStyle(
                    fontSize = 12.sp
                ),
            )
        }
    }
}