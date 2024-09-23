package com.example.todoapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun OnboardingScreen(navController: NavHostController) {






    var showOnboarding by rememberSaveable { mutableStateOf(true) }
    if(showOnboarding)
        OnboardingScreenLayout(onFinishOnboarding = { showOnboarding = false })
    else
        navController.navigate(Routes.DashBoardActivity)

}


data class OnboardingPage(val title: String, val description: String, val id:Int)

@Composable
fun OnboardingScreenLayout(onFinishOnboarding: () -> Unit) {
    val onboardingPages = listOf(
        OnboardingPage("Welcome", "Explore the features of our app", 1),
        OnboardingPage("Stay Connected", "Communicate with your friends easily", 2),
        OnboardingPage("Get Started", "Let’s get started!", 3)
    )

    var currentPage by rememberSaveable { mutableStateOf(0) }



        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(30.dp)
                .fillMaxSize()
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = colorResource(R.color.white),
                    ), shape = RoundedCornerShape(10.dp),
                )) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = onboardingPages[currentPage].title,
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                color = colorResource(R.color.color_013684),
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = onboardingPages[currentPage].description,
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = colorResource(R.color.color_1d1e20),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    if (currentPage < onboardingPages.size - 1) {
                        currentPage++
                    } else {
                        onFinishOnboarding()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(text = if (currentPage == onboardingPages.size - 1) "Finish" else "Next")
            }
        }
    }
