package com.ayushi.will.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onNavigateToRegister: () -> Unit = {}
) {
    var email by remember { mutableStateOf("jane@gmail.com") }
    var password by remember { mutableStateOf("Password123!") }
    val context = LocalContext.current

    AuthCard {
        Text(
            text = "Sign in to manage your donations, bookings, and adoption journey.",
            color = TextGray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        AuthLabel("EMAIL ADDRESS")
        AuthTextField(value = email, onValueChange = { email = it }, placeholder = "you@example.com", keyboardType = KeyboardType.Email)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PASSWORD",
                color = TextDark,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(text = "Forgot?", color = MaroonPrimary, fontSize = 12.sp)
        }
        AuthTextField(value = password, onValueChange = { password = it }, placeholder = "", isPassword = true)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onLoginClick(email, password)
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("SIGN IN", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        SwitchPrompt(
            prompt = "Don't have an account? ",
            actionText = "Sign up",
            onClick = onNavigateToRegister
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