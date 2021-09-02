package com.example.test.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
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
@ExperimentalMaterialApi
@Preview(showSystemUi = true)
@Composable
fun ShowWeatherInfo() {
    val scrollState = rememberScrollState()
    // 下拉刷新
    val state = rememberSwipeableState(1f)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
//            .swipeable(
//                orientation = Orientation.Vertical,
//                state = state,
//                anchors = mapOf
//            )
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
                    .height(500.dp),
                contentScale = ContentScale.FillBounds // 填充高度
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
        Spacer(modifier = Modifier.height(8.dp))
        LifeIndex()
        Spacer(modifier = Modifier.height(8.dp))
    }
}

/**
 *  预报元素
 */
@Composable
fun PredictItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f),
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
            repeat(4) {
                WeatherInfo()
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

/**
 * 详细天气
 */
@Composable
fun WeatherInfo() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
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
}

/**
 * 生活指数
 */
@Preview
@Composable
fun LifeIndex() {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(5.dp),
        elevation = 18.dp
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "生活指数", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                LifeIndexItem()
                LifeIndexItem()
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                LifeIndexItem()
                LifeIndexItem()
            }
        }
    }
}

@Preview
@Composable
fun LifeIndexItem() {
    Row(
        Modifier
            .background(Color.White)
            .padding(start = 15.dp, end = 15.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_ultraviolet),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
            tint = Color.Blue
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier.padding(start = 5.dp)
        ) {
            Text(
                text = "感冒",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp
                )
            )
            Text(
                text = "及易发",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        }
    }
}