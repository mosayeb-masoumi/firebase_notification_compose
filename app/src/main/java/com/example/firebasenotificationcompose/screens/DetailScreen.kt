package com.example.firebasenotificationcompose.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DetailScreen(navController: NavHostController, title: String, message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Detail Screen")
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = title, fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = message, fontSize = 20.sp, color = Color.Black)
        }

    }
}