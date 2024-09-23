package com.example.todoapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun DashBoardActivity(navController: NavController){
    DashBoardActivityLayout(navController)
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DashBoardActivityLayout(navController: NavController) {

    val activity = LocalContext.current as? Activity
    BackHandler {
        activity?.finish()
    }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("this is dashboard")

    }

}
