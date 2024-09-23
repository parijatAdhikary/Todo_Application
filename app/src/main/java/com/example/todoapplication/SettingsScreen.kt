package com.example.todoapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    ToggleSwitch()
}

@Composable
fun ToggleSwitch() {
    val isChecked = remember { mutableStateOf(false) }

    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {

        Text(text = if (isChecked.value) "Dark mode is ON" else "Dark mode is OFF", modifier = Modifier.padding(0.dp,30.dp,100.dp,0.dp)
        ,fontWeight = FontWeight.W700,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = colorResource(R.color.black),)
        Switch(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },modifier = Modifier.padding(0.dp,14.dp,0.dp,0.dp)
        )
    }
}