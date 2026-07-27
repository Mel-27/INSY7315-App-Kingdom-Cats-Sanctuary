package com.ayushi.will.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayushi.will.ui.auth.LoginScreen
import com.ayushi.will.ui.auth.RegisterScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register") {
        composable("login") {
            LoginScreen(
                onLoginClick = { _, _ -> },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterClick = { _, _, _ -> },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
    }
}