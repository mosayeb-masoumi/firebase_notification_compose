package com.example.firebasenotificationcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.firebasenotificationcompose.destinations.Destination
import com.example.firebasenotificationcompose.destinations.NavigationAppHost
import com.example.firebasenotificationcompose.ui.theme.FirebaseNotificationComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseNotificationComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                   // https://firebase.google.com/docs/cloud-messaging/android/client

                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController)


                    // Handle notification intent
                    handleNotificationIntent(intent, navController)

                }
            }
        }
    }



    private fun handleNotificationIntent(intent: Intent, navController: NavHostController) {
        if (intent.action == "com.example.ACTION_OPEN_SCREEN") {
            val destinationRoute = intent.getStringExtra("notif_destination")
            destinationRoute?.let { route ->

                navController.navigate(route)


//                val destination = when (route) {
//                    Destination.HomeScreen.route -> Destination.HomeScreen
//                    Destination.DetailScreen.route -> Destination.DetailScreen
//                    else -> null
//                }
//
//                destination?.let {
//                    // Navigate to the desired Compose screen using your navigation mechanism
//                    // Make sure this is executed on the main thread
//                    lifecycleScope.launch {
//                        navController.navigate(route)
//                    }
//                }
            }
        }
    }

//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        intent?.let {
//            setIntent(it) // Save the new intent
//            handleNotificationIntent(it, rememberNavController()) // Handle the new intent
//        }
//    }

}
