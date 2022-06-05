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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import com.example.mememania.data.network.Meme
import com.example.mememania.ui.theme.MemeManiaTheme
import com.example.mememania.ui.theme.navigation.BottomNavItem

class MainActivity : ComponentActivity() {
    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.initViewModel(application = application)
        setContent {
            MemeManiaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(mainViewModel = mainViewModel)
                }
            }
        }
    }
}


@Composable
fun MemeList(memeList: List<Meme>, navController: NavHostController) {
    // LazyColumn is to show the list of the ui. It's kind of recycler view in compose.
    LazyColumn {
        itemsIndexed(items = memeList) { index, item ->
            MemeItem(meme = item, index, navController) { i, meme ->
                navController.currentBackStackEntry?.savedStateHandle?.set("meme", meme)

                navController.navigate("details")
            }
        }
    }

}

@Composable
fun MemeItem(meme: Meme, index: Int, navController: NavController, onClick: (Int, Meme) -> Unit) {

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
        Box {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
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


@Composable
fun AppBottomNavigation(navController: NavController) {

    val navItems = listOf(BottomNavItem.Home, BottomNavItem.Favourite)

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_700),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination


        navItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title, fontSize = 9.sp) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.screen_route
                } == true,
                onClick = {
                    navController.navigate(item.screen_route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomNavigation(navController = navController) },
        drawerContent = { Text(text = "Drawer") }
    ) {
        NavHost(navController, startDestination = "home") {
            composable(route = "home") {
                MemeList(memeList = mainViewModel.movieListResponse, navController)
                if (mainViewModel.movieListResponse.isEmpty()) {
                    mainViewModel.getMemeList()
                }
            }
            composable("my_favourite") {
                FavouriteScreen()
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
                        boxCount = 0,
                        isLiked = false
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
fun FavouriteScreen() {
    Surface(color = MaterialTheme.colors.secondary) {
        Box {
            Text(text = "FavouriteScreen")
        }
    }
}