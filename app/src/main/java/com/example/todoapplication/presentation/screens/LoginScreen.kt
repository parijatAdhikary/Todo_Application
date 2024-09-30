package com.example.todoapplication.presentation.screens

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.example.todoapplication.R
import com.example.todoapplication.Routes
import com.example.todoapplication.presentation.screens.PreferenceKeys.IS_FIRST_OPEN
import com.example.todoapplication.presentation.screens.PreferenceKeys.IS_LOGGED_IN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity
    val myViewModel: WirelessViewModel = viewModel()
    BackHandler {
        activity?.finish()
    }

    var userEmail by remember { mutableStateOf(TextFieldValue("")) }
    var userPassword by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var isLoginPasswordVisible by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    val loginScrollState = rememberScrollState()

    val isFirstOpenFlow = getPreference(context, IS_FIRST_OPEN)
    val isFirstOpen by isFirstOpenFlow.collectAsState(initial = true)



    Column(
        Modifier
            .background(colorResource(R.color.white))
            .fillMaxSize()
            .padding(20.dp, 0.dp)
            .verticalScroll(loginScrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(100.dp))
        Image(
            painter = painterResource(R.drawable.login_image),
            contentDescription = "login page image",
            modifier = Modifier.size(250.dp, 200.dp)
        )
        Text(
            "Welcome Back!",
            fontWeight = FontWeight.W700,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = colorResource(R.color.black),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Log in to your existant account of Q Allure",
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(R.color.color_898989),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 40.dp,
                    shape = RoundedCornerShape(50),
                    spotColor = colorResource(R.color.color_CCCCCCCC)
                )
                .background(color = colorResource(R.color.white))
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = getColorWithDelay(
                            isEmailFocused, R.color.color_focus, R.color.white
                        ),
                    ), shape = RoundedCornerShape(50)
                )
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> isEmailFocused = focusState.isFocused },
            value = userEmail,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "personIcon",
                    tint = getColorWithDelay(
                        isEmailFocused, R.color.color_focus, R.color.color_non_focus
                    )
                )
            },
            /*colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
             */
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(if (isEmailFocused) R.color.color_focus else R.color.color_non_focus)
            ),
            placeholder = {
                Text(
                    "User ID",
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = colorResource(R.color.color_non_focus)
                )
            },
            onValueChange = {
                userEmail = it
            },
        )

        Spacer(Modifier.height(20.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 40.dp,
                    shape = RoundedCornerShape(50),
                    spotColor = colorResource(R.color.color_CCCCCCCC)
                )
                .background(color = colorResource(R.color.white))
                .border(
                    BorderStroke(
                        width = 1.dp, color = getColorWithDelay(
                            isPasswordFocused, R.color.color_focus, R.color.white
                        )
                    ), shape = RoundedCornerShape(50)
                )
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isPasswordFocused = focusState.isFocused
                },
            value = userPassword,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "personIcon",
                    tint = getColorWithDelay(
                        isPasswordFocused, R.color.color_focus, R.color.color_non_focus
                    )
                )
            },
            visualTransformation = if (isLoginPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (isLoginPasswordVisible) Icons.Filled.Check else Icons.Filled.Clear
                val description = if (isLoginPasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = {
                    isLoginPasswordVisible = !isLoginPasswordVisible
                }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            /*
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
             */
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(if (isPasswordFocused) R.color.color_focus else R.color.color_non_focus)
            ),
            placeholder = {
                Text(
                    "Password",
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = colorResource(R.color.color_non_focus)
                )
            },
            onValueChange = {
                userPassword = it
            },
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
        ) {
            Text(
                "Forgot Password?",
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Right,
                fontSize = 14.sp,
                color = colorResource(R.color.color_000b11),
                modifier = Modifier.clickable(onClick = {})
            )
        }


        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                Log.d("StatusTAG", "OnBoardActivity: $isFirstOpen")
                if (!isFirstOpen) {
                    coroutineScope.launch {
                        savePreference(true, context, IS_LOGGED_IN)
                        savePreference(true, context, IS_FIRST_OPEN)
                    }

                    myViewModel.navigation {
                        navigate(Routes.OnboardingActivity.full)
                    }
                } else {
                    myViewModel.navigation {
                        navigate(Routes.DashBoardActivity.full)
                    }
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.color_0148a4), contentColor = Color.White
            ), border = BorderStroke(
                width = 1.dp, color = colorResource(R.color.color_013684)
            ), modifier = Modifier
                .width(220.dp)
                .height(65.dp)
                .shadow(
                    elevation = 30.dp, shape = RoundedCornerShape(50), spotColor = colorResource(
                        R.color.color_803667A6
                    )
                )


        ) {
            Text(
                "LOG IN",
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Right,
                fontSize = 18.sp,
                color = colorResource(R.color.white),
            )
        }
        Spacer(Modifier.height(45.dp))


        Text(
            "Or connect using",
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(R.color.color_9f9f9f),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        Row() {
            Box(
                modifier = Modifier
                    .background(
                        colorResource(R.color.facebook_bg_color), shape = RoundedCornerShape(20)
                    )
                    .padding(25.dp, 12.dp)
                    .width(100.dp)
            ) {
                Row(
                    modifier = Modifier.align(alignment = Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(23.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = "Facebook",
                        color = Color.White,
                        fontWeight = FontWeight.W900,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .background(
                        colorResource(R.color.google_bg_color), shape = RoundedCornerShape(20)
                    )
                    .padding(32.dp, 9.dp)
                    .width(100.dp)
            ) {
                Row(
                    modifier = Modifier.align(alignment = Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(29.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = "Google",
                        color = Color.White,
                        fontWeight = FontWeight.W900,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
        Spacer(Modifier.height(60.dp))
        Row {
            Text(
                text = "Don't have an account?",
                fontSize = 16.sp,
                color = colorResource(R.color.color_1d1e20),
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = "Sign Up",
                fontSize = 16.sp,
                color = colorResource(R.color.color_2069d2),
                fontWeight = FontWeight.W800,
                modifier = Modifier.clickable() {
                    showBottomSheet = true

                },
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }) {
                SignUpActivity()
            }
        }
    }


}

@Composable
fun SignUpActivity() {

    var signupName by remember { mutableStateOf(TextFieldValue("")) }
    var signupEmail by remember { mutableStateOf(TextFieldValue("")) }
    var signupPhone by remember { mutableStateOf(TextFieldValue("")) }
    var signupPassword by remember { mutableStateOf(TextFieldValue("")) }
    var signupConfirmPassword by remember { mutableStateOf(TextFieldValue("")) }

    var isSignupNameFocused by remember { mutableStateOf(false) }
    var isSignupEmailFocused by remember { mutableStateOf(false) }
    var isSignupPhoneFocused by remember { mutableStateOf(false) }
    var isSignupPasswordFocused by remember { mutableStateOf(false) }
    var isSignupConfirmPasswordFocused by remember { mutableStateOf(false) }

    val signUpScrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }
    Column(
        Modifier
            .background(colorResource(R.color.white))
            .fillMaxSize()
            .padding(20.dp, 0.dp)
            .verticalScroll(signUpScrollState), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(40.dp))
        Text(
            "Let's Get Started!",
            fontWeight = FontWeight.W700,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = colorResource(R.color.black),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Create an account to Q Allure to get all features",
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(R.color.color_898989),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(50.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 40.dp,
                    shape = RoundedCornerShape(50),
                    spotColor = colorResource(R.color.color_CCCCCCCC)
                )
                .background(color = colorResource(R.color.white))
                .border(
                    BorderStroke(
                        width = 1.dp, color = getColorWithDelay(
                            isSignupNameFocused, R.color.color_focus, R.color.white
                        )
                    ), shape = RoundedCornerShape(50)
                )
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> isSignupNameFocused = focusState.isFocused },
            value = signupName,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "personIcon",
                    tint = getColorWithDelay(
                        isSignupNameFocused, R.color.color_focus, R.color.color_non_focus
                    )
                )
            },

            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.Bold, color = getColorWithDelay(
                    isSignupPhoneFocused, R.color.color_focus, R.color.color_non_focus
                )
            ),
            placeholder = {
                Text(
                    "Name",
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = colorResource(R.color.color_non_focus)
                )
            },
            onValueChange = {
                signupName = it
            },
        )

        Spacer(Modifier.height(30.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 40.dp,
                    shape = RoundedCornerShape(50),
                    spotColor = colorResource(R.color.color_CCCCCCCC)
                )
                .background(color = colorResource(R.color.white))
                .border(
                    BorderStroke(
                        width = 1.dp, color = getColorWithDelay(
                            isSignupEmailFocused, R.color.color_focus, R.color.white
                        )
                    ), shape = RoundedCornerShape(50)
                )
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> isSignupEmailFocused = focusState.isFocused },
            value = signupEmail,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "personIcon",
                    tint = getColorWithDelay(
                        isSignupEmailFocused, R.color.color_focus, R.color.color_non_focus
                    )
                )
            },

            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.Bold, color = getColorWithDelay(
                    isSignupPhoneFocused, R.color.color_focus, R.color.color_non_focus
                )
            ),
            placeholder = {
                Text(
                    "Email",
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = colorResource(R.color.color_non_focus)
                )
            },
            onValueChange = {
                signupEmail = it
            },
        )

        Spacer(Modifier.height(30.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 40.dp,
                    shape = RoundedCornerShape(50),
                    spotColor = colorResource(R.color.color_CCCCCCCC)
                )
                .background(color = colorResource(R.color.white))
                .border(
                    BorderStroke(
                        width = 1.dp, color = getColorWithDelay(
                            isSignupPhoneFocused, R.color.color_focus, R.color.white
                        )
                    ), shape = RoundedCornerShape(50)
                )
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> isSignupPhoneFocused = focusState.isFocused },
            value = signupPhone,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "personIcon",
                    tint = getColorWithDelay(
                        isSignupPhoneFocused, R.color.color_focus, R.color.color_non_focus
                    )
                )
            },

            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.Bold, color = getColorWithDelay(
                    isSignupPhoneFocused, R.color.color_focus, R.color.color_non_focus
                )
            ),
            placeholder = {
                Text(
                    "Phone",
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = colorResource(R.color.color_non_focus)
                )
            },
            onValueChange = {
                signupPhone = it
            },
        )

        Spacer(Modifier.height(30.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 40.dp,
                    shape = RoundedCornerShape(50),
                    spotColor = colorResource(R.color.color_CCCCCCCC)
                )
                .background(color = colorResource(R.color.white))
                .border(
                    BorderStroke(
                        width = 1.dp, color = getColorWithDelay(
                            isSignupPasswordFocused, R.color.color_focus, R.color.white
                        )
                    ), shape = RoundedCornerShape(50)
                )
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> isSignupPasswordFocused = focusState.isFocused },
            value = signupPassword,

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "personIcon",
                    tint = getColorWithDelay(
                        isSignupPasswordFocused, R.color.color_focus, R.color.color_non_focus
                    )
                )
            },
            visualTransformation = if (isSignupPasswordFocused) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (isSignupPasswordFocused) Icons.Filled.Check else Icons.Filled.Clear
                val description = if (isSignupPasswordFocused) "Hide password" else "Show password"

                IconButton(onClick = {
                    isSignupPasswordFocused = !isSignupPasswordFocused
                }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },

            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.Bold, color = getColorWithDelay(
                    isSignupPhoneFocused, R.color.color_focus, R.color.color_non_focus
                )
            ),
            placeholder = {
                Text(
                    "Password",
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = colorResource(R.color.color_non_focus)
                )
            },
            onValueChange = {
                signupPassword = it
            },
        )

        Spacer(Modifier.height(30.dp))


        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 40.dp,
                    shape = RoundedCornerShape(50),
                    spotColor = colorResource(R.color.color_CCCCCCCC)
                )
                .background(color = colorResource(R.color.white))
                .border(
                    BorderStroke(
                        width = 1.dp, color = getColorWithDelay(
                            isSignupConfirmPasswordFocused, R.color.color_focus, R.color.white
                        )
                    ), shape = RoundedCornerShape(50)
                )
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isSignupConfirmPasswordFocused = focusState.isFocused
                },
            value = signupConfirmPassword,
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "personIcon",
                    tint = getColorWithDelay(
                        isSignupConfirmPasswordFocused, R.color.color_focus, R.color.color_non_focus
                    )
                )
            },

            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.Bold, color = getColorWithDelay(
                    isSignupConfirmPasswordFocused, R.color.color_focus, R.color.color_non_focus
                )


            ),
            placeholder = {
                Text(
                    "Confirm Password",
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = colorResource(R.color.color_non_focus)
                )
            },
            onValueChange = {
                signupConfirmPassword = it
            },
        )

        Spacer(Modifier.height(70.dp))
        Button(
            onClick = {}, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.color_0148a4), contentColor = Color.White
            ), border = BorderStroke(
                width = 1.dp, color = colorResource(R.color.color_013684)
            ), modifier = Modifier
                .width(220.dp)
                .height(65.dp)
                .shadow(
                    elevation = 30.dp, shape = RoundedCornerShape(50), spotColor = colorResource(
                        R.color.color_803667A6
                    )
                )


        ) {
            Text(
                "CREATE",
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Right,
                fontSize = 18.sp,
                color = colorResource(R.color.white)
            )
        }



        Spacer(Modifier.height(80.dp))
        Row {
            Text(
                text = "Already have an account?",
                fontSize = 16.sp,
                color = colorResource(R.color.color_1d1e20),
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = "Login Here",
                fontSize = 16.sp,
                color = colorResource(R.color.color_2069d2),
                fontWeight = FontWeight.W800,
                /*modifier = Modifier.clickable() {
                    navController.popBackStack()
                },

                 */
            )
        }
    }
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
    return context.dataStore.data.map { preferences ->
        preferences[key] ?: false
    }
}


@Composable
fun getColorWithDelay(isFocused: Boolean, startColor: Int, endColor: Int): Color {
    val newColor by animateColorAsState(targetValue = colorResource(if (isFocused) startColor else endColor),
        animationSpec = tween(durationMillis = 5 * 1000),
        finishedListener = {

        })
    return newColor
}



