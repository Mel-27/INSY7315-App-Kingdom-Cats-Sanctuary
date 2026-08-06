package com.ayushi.will.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.horizontalScroll
import androidx.annotation.DrawableRes
import com.ayushi.will.R
private val amountOptions = listOf("R50", "R100", "R250", "R500")

private data class PaymentMethod(val name: String, val subtitle: String, val description: String)
private data class DonationItem(
    val name: String,
    val description: String,
    @DrawableRes val imageRes: Int
)

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

private val donationItems = listOf(
    DonationItem(
        name = "Equipment",
        description = "Carriers, scratching posts, litter boxes, and general sanctuary equipment.",
        imageRes = R.drawable.donate_equipment
    ),
    DonationItem(
        name = "Toys & blankets",
        description = "Soft blankets, cat toys, and scratching pads — comfort and play for our residents.",
        imageRes = R.drawable.donate_toys
    ),
    DonationItem(
        name = "Food",
        description = "Dry and wet cat food to help feed over 300 cats in our care.",
        imageRes = R.drawable.donate_food
    ),
    DonationItem(
        name = "Cleaning supplies",
        description = "Disinfectant, litter, and sanitation supplies to keep enclosures safe and healthy.",
        imageRes = R.drawable.donate_cleaning
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
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "1. CHOOSE AN AMOUNT",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                amountOptions.forEach { amount ->
                    AmountChip(
                        label = amount,
                        selected = !isCustomSelected && amount == selectedAmount,
                        onClick = {
                            selectedAmount = amount
                            isCustomSelected = false
                        }
                    )
                }
                AmountChip(
                    label = "Custom",
                    selected = isCustomSelected,
                    onClick = { isCustomSelected = true }
                )
            }
            if (isCustomSelected) {
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = customAmount,
                    onValueChange = { customAmount = it },
                    placeholder = { Text("Enter amount (R)") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = KksCardStroke,
                        focusedBorderColor = KksRed
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "2. CHOOSE A PAYMENT METHOD",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                paymentMethods.forEach { method ->
                    PaymentMethodCard(
                        method = method,
                        selected = method == selectedPayment,
                        onClick = { selectedPayment = method }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "3. YOUR DETAILS",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "FULL NAME", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = KksTextSecondary)
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = KksCardStroke,
                    focusedBorderColor = KksRed
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "EMAIL ADDRESS", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = KksTextSecondary)
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = KksCardStroke,
                    focusedBorderColor = KksRed
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { showConfirmation = true },
                colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("CONTINUE TO DONATE", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (showConfirmation) {
                val amountLabel = if (isCustomSelected) "R$customAmount" else (selectedAmount ?: "R0")
                DonationConfirmedDialog(
                    email = email,
                    amountLabel = amountLabel,
                    paymentLabel = selectedPayment.name,
                    onBackToHome = {
                        showConfirmation = false
                        onBackToHome()
                    }
                )
            }

        }
    }
}

@Composable
private fun AmountChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) KksRed else Color.Transparent)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = KksCardStroke,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PaymentMethodCard(method: PaymentMethod, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) KksRed else KksCardStroke)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = method.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = method.subtitle, fontSize = 10.sp, color = KksTextSecondary)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = method.description, fontSize = 12.sp, color = KksTextSecondary)
        }
    }
}

@Composable
private fun DonationConfirmedDialog(
    email: String,
    amountLabel: String,
    paymentLabel: String,
    onBackToHome: () -> Unit
) {
    Dialog(onDismissRequest = onBackToHome) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(KksRed.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = KksRed)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Thank you for your donation!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "A receipt will be sent to $email.",
                    fontSize = 13.sp,
                    color = KksTextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DonationSummaryRow("Amount", amountLabel)
                        Spacer(modifier = Modifier.height(8.dp))
                        DonationSummaryRow("Payment Method", paymentLabel)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onBackToHome,
                    colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("BACK TO HOME", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun DonationSummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = KksTextSecondary)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

