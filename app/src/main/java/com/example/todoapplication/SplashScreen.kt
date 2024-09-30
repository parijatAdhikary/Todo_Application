package com.example.todoapplication

import android.os.Bundle
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.todoapplication.PreferenceKeys.IS_LOGGED_IN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun SplashScreen(){
    val myViewModel: WirelessViewModel = viewModel()
    SplashScreenLayout(myViewModel)
}

@Composable
private fun SplashScreenLayout(myViewModel: WirelessViewModel) {


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
        delay(5*1000)

        if(!isLoggedIn) {
            myViewModel.navigation {
                navigate(Routes.LoginActivity.full)
            }
        }
        else{
            myViewModel.navigation {
                navigate(Routes.DashBoardActivity.full)
            }
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



@HiltViewModel
class SplashScreenViewModel @Inject constructor(): WirelessViewModel(){
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
}
