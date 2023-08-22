package com.example.firebasenotificationcompose.destinations

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.firebasenotificationcompose.screens.DetailScreen
import com.example.firebasenotificationcompose.screens.HomeScreen


sealed class Destination(var route: String) {
    object HomeScreen : Destination("home")
    object DetailScreen : Destination("detail?title={title}&message={message}"){
        fun passOptionalTitleAndMessage(title: String = "", message: String = ""): String  // 0 and "" are default values
        {
            return "detail?title=$title&message=$message"
        }
    }
}


@Composable
fun NavigationAppHost(navController: NavHostController) {


    // startDestination = "home"
    NavHost(navController = navController, startDestination = Destination.HomeScreen.route) {
        composable(Destination.HomeScreen.route) { HomeScreen(navController) }
        composable(
           route =  Destination.DetailScreen.route ,
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                },
                navArgument("message") {
                    type = NavType.StringType
                },
            )
        ) {
            val title = it.arguments?.getString("title").toString()
            val message = it.arguments?.getString("message").toString()
            DetailScreen(navController , title , message) }
    }

}


