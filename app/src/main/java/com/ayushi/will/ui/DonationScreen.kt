package com.ayushi.will.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayushi.will.ui.theme.KksTextSecondary

private val amountOptions = listOf("R50", "R100", "R250", "R500")

private data class PaymentMethod(val name: String, val subtitle: String, val description: String)

private val paymentMethods = listOf(
    PaymentMethod(
        name = "PayPal",
        subtitle = "CARD / PAYPAL BALANCE",
        description = "Pay securely using your PayPal account or any major card, no PayPal account required."
    ),
    PaymentMethod(
        name = "Ozow",
        subtitle = "INSTANT EFT",
        description = "Pay directly from your South African bank account via secure instant EFT."
    )
)

@Composable
fun DonationScreen(
    onBackToHome: () -> Unit = {}
) {
    var selectedAmount by remember { mutableStateOf<String?>("R100") }
    var isCustomSelected by remember { mutableStateOf(false) }
    var customAmount by remember { mutableStateOf("") }
    var selectedPayment by remember { mutableStateOf(paymentMethods[0]) }
    var fullName by remember { mutableStateOf("Red") }
    var email by remember { mutableStateOf("red@gmail.com") }
    var showConfirmation by remember { mutableStateOf(false) }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                text = "Support the Sanctuary",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Every donation helps feed, shelter, and care for our cats until they find their forever homes. Choose whichever payment method suits you best.",
                fontSize = 13.sp,
                color = KksTextSecondary
            )

        }
    }
}