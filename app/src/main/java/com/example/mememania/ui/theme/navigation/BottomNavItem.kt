package com.example.mememania.ui.theme.navigation

import com.example.mememania.R

sealed class BottomNavItem(val title:String, val icon:Int, val screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object Favourite: BottomNavItem("Favourite",R.drawable.ic_favourite,"my_favourite")
}