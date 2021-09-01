package com.example.test.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test.R

/**
 *  显示天气信息
 */
@Preview(showSystemUi = true)
@Composable
fun ShowWeatherInfo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.background(Color.Black),
            contentAlignment = Alignment.TopCenter // 内容位置
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_partly_cloudy_day),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.85f), // todo: 9/1 这里换为fillMaxWidth()会出现问题
                contentScale = ContentScale.FillHeight // 填充高度
            )
            Column(
                verticalArrangement = Arrangement.Center, // 内容垂直位置
                horizontalAlignment = Alignment.CenterHorizontally // 内容水平位置
            ) {
                Text(
                    text = "北京市",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 25.sp
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = "10   ℃",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 55.sp
                    ),
                    modifier = Modifier.padding(top = 140.dp)
                )
                Text(
                    text = "晴 | 空气质量指数26",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        PredictItem()
    }
}

/**
 *  预报元素
 */
@Preview
@Composable
fun PredictItem() {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        elevation = 18.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "预报",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontSize = 25.sp
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "2019-10-25")
                Icon(
                    painter = painterResource(id = R.drawable.ic_cloudy),
                    contentDescription = null
                )
                Text(text = "多云")
                Text(text = "4--13℃")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
