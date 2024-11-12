package com.moberganalytics.tutorials

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                ArticleCard(modifier = Modifier.padding(innerPadding))
//                TaskComplete(modifier = Modifier.padding(innerPadding))
//                Quadrant(modifier = Modifier.padding(innerPadding))
                BusinessCard(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun BusinessCard(
    modifier: Modifier = Modifier,
) {
    val appIcons = Icons.Rounded

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .background(Color(0xFFFFA000))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(2f)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.android_logo),
                    contentDescription = null,
                    modifier = Modifier.width(256.dp)
                )
                Text(
                    text = stringResource(R.string.bc_name),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = stringResource(R.string.bc_title),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.weight(1f),
        ) {
            Column() {
                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(appIcons.Call, contentDescription = null)
                    Text(text = stringResource(R.string.bc_phone))
                }
                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(appIcons.Share, contentDescription = null, modifier = Modifier.padding(end = 10.dp))
                    Text(text = stringResource(R.string.bc_twitter))
                }
                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(appIcons.Email, contentDescription = null, modifier = Modifier.padding(end = 10.dp))
                    Text(text = stringResource(R.string.bc_email))
                }
            }
        }
    }
}

@Composable
fun QuadrantCard(
    title: String,
    description: String,
    background: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun Quadrant(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.weight(1f)) {
            QuadrantCard(
                title = stringResource(R.string.quadrant_1),
                description = stringResource(R.string.quadrant_1c),
                background = Color(0xFFEADDFF),
                modifier = Modifier.weight(1f)
            )
            QuadrantCard(
                title = stringResource(R.string.quadrant_2),
                description = stringResource(R.string.quadrant_2c),
                background = Color(0xFFD0BCFF),
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            QuadrantCard(
                title = stringResource(R.string.quadrant_1),
                description = stringResource(R.string.quadrant_1c),
                background = Color(0xFFB69DF8),
                modifier = Modifier.weight(1f)
            )
            QuadrantCard(
                title = stringResource(R.string.quadrant_2),
                description = stringResource(R.string.quadrant_2c),
                background = Color(0xFFF6EDFF),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TaskComplete(
    modifier: Modifier = Modifier,
    completeStr: String = stringResource(R.string.task_complete),
    message: String = stringResource(R.string.task_message),
    completeIcon: Painter = painterResource(R.drawable.task_completed)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = completeIcon,
            contentDescription = null,
        )
        Text(
            text = completeStr,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = message,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.article_title),
    shortDesc: String = stringResource(R.string.article_short_desc),
    longDesc: String = stringResource(R.string.article_long_desc),
    banner: Painter = painterResource(R.drawable.article_bg),
) {
    Column(modifier = modifier) {
        Image(
            painter = banner,
            contentDescription = "article banner",
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = title,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = shortDesc,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        Text(
            text= longDesc,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    BusinessCard()
}