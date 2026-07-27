package com.ayushi.will.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayushi.will.R

// ---- Shared palette pulled from the Kingdom Cats Sanctuary web design ----
internal val BackgroundCream = Color(0xFFF4EFE9)
internal val CardWhite = Color(0xFFFFFFFF)
internal val MaroonPrimary = Color(0xFF8E1F2F)
internal val TextGray = Color(0xFF6B6B6B)
internal val TextDark = Color(0xFF2B2B2B)
internal val FieldBorder = Color(0xFFDDDDDD)

/**
 * Shared card shell (logo, branding text, white rounded card, cream background)
 * used by both LoginScreen and RegisterScreen.
 */
@Composable
internal fun AuthCard(content: @Composable ColumnScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(CardWhite)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Kingdom Cats Sanctuary logo",
                modifier = Modifier.size(90.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "A HEAVEN FOR FELINES",
                color = TextGray,
                fontSize = 12.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Kingdom Cats Sanctuary",
                color = MaroonPrimary,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(24.dp))

            content()
        }
    }
}

@Composable
internal fun AuthLabel(text: String) {
    Text(
        text = text,
        color = TextDark,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    )
}

@Composable
internal fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = TextGray.copy(alpha = 0.6f)) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = FieldBorder,
            focusedBorderColor = MaroonPrimary
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
internal fun SwitchPrompt(prompt: String, actionText: String, onClick: () -> Unit) {
    Row {
        Text(text = prompt, color = TextGray, fontSize = 13.sp)
        Text(
            text = actionText,
            color = MaroonPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}