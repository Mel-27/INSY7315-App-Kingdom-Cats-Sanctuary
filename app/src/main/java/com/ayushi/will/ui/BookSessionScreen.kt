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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed


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

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "1. SELECT A DATE",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            CalendarCard(selectedDay = selectedDay, onDaySelected = { selectedDay = it })

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "2. CHOOSE A TIME",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                timeSlots.forEach { slot ->
                    TimeSlotRow(
                        slot = slot,
                        selected = slot == selectedSlot,
                        onClick = { selectedSlot = slot }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "3. REVIEW YOUR BOOKING",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, KksCardStroke),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ReviewRow(label = "SESSION TYPE", value = "Kitten petting session")
                    Spacer(modifier = Modifier.height(12.dp))
                    ReviewRow(
                        label = "DATE & TIME",
                        value = "${weekdayNameFor(selectedDay)}, $selectedDay August 2026 · ${selectedSlot.label}"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "YOUR FULL NAME", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = KksTextSecondary)
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
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { showConfirmation = true },
                colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("CONFIRM & BOOK", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
private fun CalendarCard(selectedDay: Int, onDaySelected: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, KksCardStroke),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.ChevronLeft, contentDescription = "Previous month", tint = KksTextSecondary)
                Text(text = MONTH_LABEL, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Icon(Icons.Filled.ChevronRight, contentDescription = "Next month", tint = KksTextSecondary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                WEEKDAY_HEADERS.forEach { header ->
                    Text(
                        text = header,
                        fontSize = 12.sp,
                        color = KksTextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val cells = buildList {
                repeat(FIRST_DAY_COLUMN) { add(null) }
                for (day in 1..DAYS_IN_MONTH) add(day)
                while (size % 7 != 0) add(null)
            }

            cells.chunked(7).forEach { week ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    week.forEach { day ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (day != null) {
                                val isSelected = day == selectedDay
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.Transparent)
                                        .clickable { onDaySelected(day) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.toString(),
                                        color = if (isSelected) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurface,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }



}

@Composable
private fun TimeSlotRow(slot: TimeSlot, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) KksRed else KksCardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = slot.label, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(text = slot.timeRange, fontSize = 13.sp, color = KksTextSecondary)
        }
    }
}

@Composable
private fun ReviewRow(label: String, value: String) {
    Column {
        Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = KksTextSecondary)
        Text(text = value, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

