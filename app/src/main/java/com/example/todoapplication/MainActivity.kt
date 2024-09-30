package com.example.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.debduttapanda.j3lib.MyScreen
import com.debduttapanda.j3lib.arguments
import com.debduttapanda.j3lib.models.Route
import com.debduttapanda.j3lib.wvm
import com.example.todoapplication.Routes.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
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

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.SplashScreen.full
        ) {
            MyScreen(
                navController,
                Routes.SplashScreen,
                wirelessViewModel = { wvm<SplashScreenViewModel>() }
            ) {
                SplashScreen()
            }
            MyScreen(
                navController,
                Routes.DashBoardActivity,
                wirelessViewModel = { wvm<DashBoardViewModel>() }
            ) {
                DashBoardScreenLayout()
            }
            MyScreen(
                navController,
                Routes.LoginActivity,
                wirelessViewModel = { wvm<LoginScreenViewModel>() }
            ) {
                LoginScreen()
            }
            MyScreen(
                navController,
                Routes.OnboardingActivity,
                wirelessViewModel = { wvm<OnboardingScreenViewModel>() }
            ) {
                OnboardingScreen()
            }
            MyScreen(
                navController,
                Routes.SettingsScreen,
                wirelessViewModel = { wvm<SettingsScreenViewModel>() }
            ) {
                SettingsScreen()
            }

        }
    }
}

