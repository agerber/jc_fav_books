package edu.uchicago.gerber.favs.presentation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import edu.uchicago.gerber.favs.authorization.AmplifyService
import edu.uchicago.gerber.favs.authorization.AmplifyServiceImpl
import edu.uchicago.gerber.favs.presentation.navigation.Screen
import edu.uchicago.gerber.favs.presentation.viewmodels.BookViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(viewModel: BookViewModel, navController: NavController, amplifyService: AmplifyService) {
    val state by viewModel.signUpState


    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.username,
            onValueChange = { viewModel.updateSignUpState(username = it) },
            placeholder = { Text(text = "Username") }
        )

        TextField(
            value = state.email,
            onValueChange = { viewModel.updateSignUpState(email = it) },
            placeholder = { Text(text = "Email") }
        )

        TextField(
            value = state.password,
            onValueChange = { viewModel.updateSignUpState(password = it) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text(text = "Password") }
        )


        Button(onClick = {
            amplifyService.signUp(viewModel.signUpState.value){
                MainScope().launch {
                    navController.navigate(route = Screen.SignUp.route)
                }

            }


        }) {
            Text(text = "Sign Up")
        }

        TextButton(onClick = {navController.navigate(route = Screen.Login.route)}) {
            Text(text = "Already have an account? Login.")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: BookViewModel, navController: NavController, amplifyService: AmplifyService) {
    val state by viewModel.loginState

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.username,
            onValueChange = { viewModel.updateLoginState(username = it) },
            placeholder = { Text(text = "Username") }
        )

        TextField(
            value = state.password,
            onValueChange = { viewModel.updateLoginState(password = it) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text(text = "Password") }
        )

        Button(onClick =   {

            amplifyService.login(viewModel.loginState.value){
                MainScope().launch {
                    navController.navigate(route = Screen.Search.route)
                }

            }

        }

        ) {
            Text(text = "Login")
        }

        TextButton(onClick = { navController.navigate(route = Screen.SignUp.route)}) {
            Text(text = "Don't have an account? Sign up.")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyScreen(viewModel: BookViewModel, navController: NavController, amplifyService: AmplifyService) {
    val state by viewModel.verificationCodeState

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.code,
            onValueChange = { viewModel.updateVerificationCodeState(code = it) },
            placeholder = { Text(text = "Verification Code") }
        )

        Button(onClick = {

            amplifyService.verifyCode(viewModel.verificationCodeState.value){
                MainScope().launch {
                    navController.navigate(route = Screen.Login.route)
                }

            }

        }) {
            Text(text = "Verify")
        }
    }
}