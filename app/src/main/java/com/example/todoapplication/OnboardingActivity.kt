package com.example.todoapplication

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun OnboardingActivity(navController: NavHostController) {

    /*val context=LocalContext.current
    val isFirstOpenFlow = getPreference(context, IS_FIRST_OPEN)
    val isFirstOpen by isFirstOpenFlow.collectAsState(initial = true)
    Text("I am in OnBoardActivity:$isFirstOpen")
    Log.d("StatusTAG", "OnBoardActivity: $isFirstOpen")
    if(!isFirstOpen) {
        navController.navigate(Routes.DashBoardActivity)
    }
    else{
        var showOnboarding by rememberSaveable { mutableStateOf(true) }
        if(showOnboarding)
            OnboardingScreen(onFinishOnboarding = { showOnboarding = false })
        else
            navController.navigate(Routes.DashBoardActivity)
    }

     */


    var showOnboarding by rememberSaveable { mutableStateOf(true) }
    if(showOnboarding)
        OnboardingScreen(onFinishOnboarding = { showOnboarding = false })
    else
        navController.navigate(Routes.DashBoardActivity)

}


data class OnboardingPage(val title: String, val description: String, val id:Int)

@Composable
fun OnboardingScreen(onFinishOnboarding: () -> Unit) {
    val onboardingPages = listOf(
        OnboardingPage("Welcome", "Explore the features of our app",1),
        OnboardingPage("Stay Connected", "Communicate with your friends easily",2),
        OnboardingPage("Get Started", "Let’s get started!",3)
    )

    var currentPage by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Show the current page's image
       /* Image(
            painter = painterResource(id = onboardingPages[currentPage].imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        */

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = onboardingPages[currentPage].title,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = onboardingPages[currentPage].description,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (currentPage < onboardingPages.size - 1) {
                    currentPage++
                } else {
                    onFinishOnboarding()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = if (currentPage == onboardingPages.size - 1) "Finish" else "Next")
        }
    }
}