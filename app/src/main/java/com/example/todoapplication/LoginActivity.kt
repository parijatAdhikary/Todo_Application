package com.example.todoapplication

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@Composable
fun LoginActivity(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context=LocalContext.current
    val activity = LocalContext.current as? Activity
    BackHandler {
        activity?.finish()
    }
    val isLoggedInFlow = getPreference(context,PreferenceKeys.IS_LOGGED_IN)
    val isLoggedIn by isLoggedInFlow.collectAsState(initial = false)
    Text("I am in LoginActivity:$isLoggedIn")
    Button(onClick = {
        coroutineScope.launch {
            savePreference(true, context,PreferenceKeys.IS_LOGGED_IN)
        }

    }) {
        Text("Click")
    }
    Log.d("StatusTAG", "LoginActivity: $isLoggedIn")
    if(isLoggedIn) {
       navController.navigate(Routes.OnboardingActivity)
    }
    PartialBottomSheet()
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_information")

object PreferenceKeys {
    val IS_FIRST_OPEN = booleanPreferencesKey("is_first_open")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
}
suspend fun savePreference(input: Boolean, context: Context, key: Preferences.Key<Boolean>) {
    context.dataStore.edit { preferences ->
        preferences[key] = input
    }
}
fun getPreference(context: Context, key: Preferences.Key<Boolean>): Flow<Boolean> {
    return context.dataStore.data
        .map { preferences ->
            preferences[key] ?: false
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { showBottomSheet = true }
        ) {
            Text("Display partial bottom sheet")
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                Text(
                    "Swipe up to open sheet. Swipe down to dismiss.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}