package com.example.todoapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun DashBoardScreen(navController: NavController){
    DashBoardScreenLayout(navController)
}
@Composable
fun DashBoardScreenLayout(navController: NavController) {


   // LaunchedEffect (Unit){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text("this is dashboard")

        }
   // }


}
