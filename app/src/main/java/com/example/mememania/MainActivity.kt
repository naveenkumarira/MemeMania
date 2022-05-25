package com.example.mememania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.mememania.network.Meme
import com.example.mememania.ui.theme.MemeManiaTheme

class MainActivity : ComponentActivity() {
    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemeManiaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MemeList(memeList = mainViewModel.movieListResponse)
                    mainViewModel.getMemeList()
                }
            }
        }
    }
}


@Composable
fun MemeList(memeList: List<Meme>) {
    var selectedIndex by remember { mutableStateOf(-1) }
    LazyColumn {
        itemsIndexed(items = memeList) { index, item ->
            MemeItem(meme = item, index, selectedIndex) { i ->
                selectedIndex = i
            }
        }
    }

}

@Composable
fun MemeItem(meme: Meme, index: Int, selectedIndex: Int, onClick: (Int) -> Unit) {

    val backgroundColor =
        if (index == selectedIndex) MaterialTheme.colors.primary else MaterialTheme.colors.background
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .clickable { onClick(index) }
            .height(250.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Surface(color = backgroundColor) {

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 50f
                            )
                        )
                )

                Image(
                    contentScale = ContentScale.Crop,
                    painter = rememberImagePainter(
                        data = meme.url,
                        builder = {
                            placeholder(R.drawable.placeholder)
                        }
                    ),
                    contentDescription = meme.name,
                    //                         .weight(0.2f) in modifier will takes the weight like in linear layout
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().background(color = Color.Black).padding(16.dp),
                        textAlign = TextAlign.Start,
                        text = meme.name ?: "",
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                        fontWeight = FontWeight.Bold,
                    )
                }

            }
        }
    }

}