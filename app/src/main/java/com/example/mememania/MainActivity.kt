package com.example.mememania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                    HomeView()
                }
            }
        }
    }

    @Composable
    fun HomeView() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "feed") {
            composable(route = "feed") {
                MemeList(memeList = mainViewModel.movieListResponse, navController)
                if (mainViewModel.movieListResponse.isEmpty()) {
                    mainViewModel.getMemeList()
                }
            }
            composable(
                route = "details",
                arguments = listOf(navArgument("meme") {
                    type = NavType.ParcelableType(Meme::class.java)
                    defaultValue = Meme(
                        name = "DefaultMeme",
                        url = "https://i.imgflip.com/30b1gx.jpg",
                        height = null,
                        id = "",
                        width = 0,
                        boxCount = 0
                    )
                })
            ) {
                navController.previousBackStackEntry?.savedStateHandle?.get<Meme>("meme")
                    ?.let { meme ->
                        MemeDetails(meme)
                    }
            }
        }
    }
}


@Composable
fun MemeList(memeList: List<Meme>, navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(-1) } //State of the lazy column. Based on it the recomposition will occur
    // LazyColumn is to show the list of the ui. It's kind of recycler view in compose.
    LazyColumn {
        itemsIndexed(items = memeList) { index, item ->
            MemeItem(meme = item, index, selectedIndex) { i, meme ->
                selectedIndex =
                    i  //When we are updating the selectedIndex then re-composition will happen for updating the UI.
//                navController.currentBackStackEntry?.arguments?.putAll(Bundle().apply {
//                    putParcelable("meme", meme)
//                })
                navController.currentBackStackEntry?.savedStateHandle?.set("meme", meme)

                navController.navigate("details")
            }
        }
    }

}

@Composable
fun MemeItem(meme: Meme, index: Int, selectedIndex: Int, onClick: (Int, Meme) -> Unit) {

    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .clickable { onClick(index, meme) }
            .height(250.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Surface(color = MaterialTheme.colors.primary) {
            //Box layout is equals to Frame layout in traditional UI.
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
                        data = meme.url ?: "https://i.imgflip.com/30b1gx.jpg",
                        builder = {
                            placeholder(R.drawable.placeholder)
                        }
                    ),
                    contentDescription = meme.name ?: "Drake Hotline Bling",
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Black)
                            .padding(16.dp),
                        textAlign = TextAlign.Start,
                        text = meme.name ?: "Drake Hotline Bling",
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                        fontWeight = FontWeight.Bold,
                    )
                }

            }
        }
    }

}

@Composable
        /**
         * Simple detail page
         * @param meme contains the meme info
         */
fun MemeDetails(meme: Meme) {
    Surface(color = MaterialTheme.colors.secondary) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                painter = rememberImagePainter(
                    data = meme.url,
                    builder = {
                        placeholder(R.drawable.placeholder)
                    }
                ),
                contentDescription = meme.name,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .padding(16.dp),
                textAlign = TextAlign.Start,
                text = meme.name ?: "",
                style = TextStyle(color = Color.White, fontSize = 16.sp),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}