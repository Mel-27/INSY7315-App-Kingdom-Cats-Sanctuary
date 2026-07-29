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

// ---- Static "August 2026" calendar data (non-functional prev/next, matches the mockup) ----
private const val MONTH_LABEL = "August 2026"
private const val DAYS_IN_MONTH = 31
private const val FIRST_DAY_COLUMN = 6 // August 1, 2026 falls on a Saturday (0 = Sunday)
private val WEEKDAY_HEADERS = listOf("S", "M", "T", "W", "T", "F", "S")
private val WEEKDAY_NAMES = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

private fun weekdayNameFor(day: Int): String {
    val column = (FIRST_DAY_COLUMN + day - 1) % 7
    return WEEKDAY_NAMES[column]
}

private data class TimeSlot(val label: String, val timeRange: String)

private val timeSlots = listOf(
    TimeSlot("Morning Bliss", "10:00 AM - 10:45 AM"),
    TimeSlot("Coffee & Cats", "11:30 AM - 12:15 PM"),
    TimeSlot("Golden Hour Purrs", "01:00 PM - 01:45 PM")
)

@Composable
fun BookSessionScreen(
    onBackToHome: () -> Unit = {}
) {
    var selectedDay by remember { mutableStateOf(19) }
    var selectedSlot by remember { mutableStateOf(timeSlots[1]) }
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
                text = "Kitten Petting Sessions",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Experience the healing power of purrs. Book a private or group session with our sanctuary residents.",
                fontSize = 13.sp,
                color = KksTextSecondary
            )

        }
    }
}