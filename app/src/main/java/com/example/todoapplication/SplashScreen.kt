package com.example.todoapplication

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.todoapplication.PreferenceKeys.IS_FIRST_OPEN
import com.example.todoapplication.PreferenceKeys.IS_LOGGED_IN
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController:NavController){
    SplashScreenLayout(navController)
}

@Composable
private fun SplashScreenLayout(navController: NavController) {


    val context= LocalContext.current
    val isLoggedInFlow = getPreference(context, IS_LOGGED_IN)
    val isLoggedIn by isLoggedInFlow.collectAsState(initial = true)


    Column(modifier = Modifier.fillMaxSize().background(color = Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text("Todo Application",
            fontWeight = FontWeight.W700,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = colorResource(R.color.black),
            modifier = Modifier.fillMaxWidth())
        AnimatedPreloader()
    }

    LaunchedEffect(Unit) {
        delay(0*1000)

        if(!isLoggedIn) {
            navController.navigate(Routes.LoginActivity)
        }
        else{
            navController.navigate(Routes.DashBoardActivity)
        }



    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier.fillMaxWidth().background(color = Color.White)) {

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.todo_animation
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier
    )
}

