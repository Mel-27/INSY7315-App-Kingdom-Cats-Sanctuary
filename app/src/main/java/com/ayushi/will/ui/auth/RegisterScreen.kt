package com.ayushi.will.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit = { _, _, _ -> },
    onNavigateToLogin: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("Jane Doe") }
    var email by remember { mutableStateOf("jane@gmail.com") }
    var password by remember { mutableStateOf("Password123!") }
    var confirmPassword by remember { mutableStateOf("Password123!") }

    AuthCard {
        Text(
            text = "Join the sanctuary community — donate, adopt, and follow our cats.",
            color = TextGray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        AuthLabel("FULL NAME")
        AuthTextField(value = fullName, onValueChange = { fullName = it }, placeholder = "Your full name")

        Spacer(modifier = Modifier.height(16.dp))

        AuthLabel("EMAIL ADDRESS")
        AuthTextField(value = email, onValueChange = { email = it }, placeholder = "you@example.com", keyboardType = KeyboardType.Email)

        Spacer(modifier = Modifier.height(16.dp))

        AuthLabel("PASSWORD")
        AuthTextField(value = password, onValueChange = { password = it }, placeholder = "", isPassword = true)

        Spacer(modifier = Modifier.height(16.dp))

        AuthLabel("CONFIRM PASSWORD")
        AuthTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, placeholder = "", isPassword = true)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onRegisterClick(fullName, email, password)
                onNavigateToLogin()
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("CREATE ACCOUNT", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        SwitchPrompt(
            prompt = "Already have an account? ",
            actionText = "Sign in",
            onClick = onNavigateToLogin
        )
    }
}

/*
References:
Android Developers (2026) State and Jetpack Compose.
Available at: https://developer.android.com/jetpack/compose/state (Accessed: 10 August 2026).

Android Developers (2026) Text fields — OutlinedTextField.
Available at: https://developer.android.com/jetpack/compose/text/user-input (Accessed: 10 August 2026).

Android Developers (2026) Navigation with Compose.
Available at: https://developer.android.com/jetpack/compose/navigation (Accessed: 10 August 2026).
 */