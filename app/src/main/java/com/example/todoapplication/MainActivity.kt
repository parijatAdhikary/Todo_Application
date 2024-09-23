package com.example.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapplication.Routes.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setupNavController()
        }
    }

    @Composable
    private fun setupNavController() {
        var navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.SplashScreen, builder = {
            composable(Routes.SplashScreen){
                SplashScreen(navController)
            }
            composable(Routes.DashBoardActivity){
                DashBoardActivity(navController)
            }
            composable(Routes.LoginActivity){
                LoginActivity(navController)
            }
            composable(Routes.OnboardingActivity){
                OnboardingActivity(navController)
            }
        })
    }
}
