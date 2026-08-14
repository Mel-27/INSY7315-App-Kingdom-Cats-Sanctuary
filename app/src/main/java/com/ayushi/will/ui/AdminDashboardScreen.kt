package com.ayushi.will.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import com.ayushi.will.ui.theme.KksTextSecondary
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material3.HorizontalDivider

private data class AdminStat(val icon: ImageVector, val value: String, val label: String)

private val adminStats = listOf(
    AdminStat(Icons.Filled.Pets, "24", "Sanctuary cats"),
    AdminStat(Icons.Filled.CalendarMonth, "8", "Upcoming bookings"),
    AdminStat(Icons.AutoMirrored.Filled.ListAlt, "18", "Event RSVPs"),
    AdminStat(Icons.Filled.FavoriteBorder, "R12,450", "Donations received"),
    AdminStat(Icons.Filled.Forum, "6", "Recent community posts")
)

private data class BookingRequest(
    val name: String,
    val email: String,
    val sessionType: String,
    val date: String,
    val time: String
)

private val bookingRequests = listOf(
    BookingRequest("Sarah Naidoo", "sarah@example.com", "Cat Viewing", "20 August 2026", "10:00"),
    BookingRequest("Jason Singh", "jason@example.com", "Petting Session", "21 August 2026", "11:00"),
    BookingRequest("Amy Govender", "amy@example.com", "Cat Viewing", "22 August 2026", "14:00")
)

private data class ManageItem(val icon: ImageVector, val title: String, val subtitle: String)

private val manageItems = listOf(
    ManageItem(Icons.Filled.Pets, "Cats", "Add or update sanctuary cats"),
    ManageItem(Icons.Filled.CalendarMonth, "Events", "Manage sanctuary events"),
    ManageItem(Icons.AutoMirrored.Filled.ListAlt, "Merchandise", "Update products and availability"),
    ManageItem(Icons.Filled.Groups, "Community", "Review community posts")
)

private data class EventRsvp(
    val attendee: String,
    val email: String,
    val eventName: String,
    val eventDateTime: String,
    val guests: Int,
    val confirmed: Boolean
)

private val eventRsvps = listOf(
    EventRsvp("Thandiwe Ngcobo", "thandiwe@example.com", "KCS Adoption Day", "24 August 2026 · 10:00", 2, confirmed = true),
    EventRsvp("Jason Singh", "jason@example.com", "Fundraising Walk", "5 September 2026 · 09:00", 1, confirmed = false),
    EventRsvp("Amy Govender", "amy@example.com", "KCS Adoption Day", "24 August 2026 · 10:00", 3, confirmed = false)
)

private data class AdminDonation(val donor: String, val amount: String, val method: String, val completed: Boolean)

private val recentDonations = listOf(
    AdminDonation("John Smith", "R500.00", "EFT", completed = true),
    AdminDonation("Anonymous", "R250.00", "Card", completed = true),
    AdminDonation("Priya Naidoo", "R1,000.00", "PayPal", completed = false)
)

@Composable
fun AdminDashboardScreen(
    onMenuClick: () -> Unit = {}
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "SANCTUARY MANAGEMENT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = KksRed
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Welcome back, Admin",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Here's a quick overview of what's happening at Kingdom Cats Sanctuary. Review bookings, event RSVPs, donations and community activity from here.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = KksTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(adminStats) { stat ->
                    AdminStatCard(stat)
                }
            }
            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Booking requests", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Review visitors waiting for confirmation.", fontSize = 12.sp, color = KksTextSecondary)
                }
                Text(text = "VIEW ALL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = KksRed)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                bookingRequests.forEachIndexed { index, request ->
                    BookingRequestRow(request = request)
                    if (index != bookingRequests.lastIndex) {
                        HorizontalDivider(Modifier, thickness = 1.dp, color = KksCardStroke)
                    }
                }
            }
            Spacer(modifier = Modifier.height(28.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(text = "Manage", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text(text = "Quick access to sanctuary content.", fontSize = 12.sp, color = KksTextSecondary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                manageItems.forEach { item ->
                    ManageRow(item = item)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Event RSVPs", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text(text = "See who has RSVP'd for upcoming sanctuary events.", fontSize = 12.sp, color = KksTextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                eventRsvps.forEach { rsvp ->
                    EventRsvpCard(rsvp = rsvp)
                }
            }
            //
            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Recent donations", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text(text = "The latest contributions made to the sanctuary.", fontSize = 12.sp, color = KksTextSecondary)
                }
                Text(text = "VIEW ALL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = KksRed)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                recentDonations.forEachIndexed { index, donation ->
                    DonationRow(donation = donation)
                    if (index != recentDonations.lastIndex) {
                        HorizontalDivider(Modifier, thickness = 1.dp, color = KksCardStroke)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AdminStatCard(stat: AdminStat) {
    Card(
        modifier = Modifier.width(140.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, KksCardStroke)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(KksRed.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(stat.icon, contentDescription = null, tint = KksRed, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = stat.value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = stat.label, fontSize = 11.sp, color = KksTextSecondary)
        }
    }
}

@Composable
private fun BookingRequestRow(request: BookingRequest) {
    Column(modifier = Modifier.padding(vertical = 14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = request.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    StatusBadge(text = "PENDING", color = androidx.compose.ui.graphics.Color(0xFFB8860B))
                }
                Text(text = request.email, fontSize = 12.sp, color = KksTextSecondary)
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Session: ${request.sessionType}   Date: ${request.date}   Time: ${request.time}",
            fontSize = 11.sp,
            color = KksTextSecondary
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Accept", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Reject", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun StatusBadge(text: String, color: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(text = text, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
private fun ManageRow(item: ManageItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, KksCardStroke)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(item.icon, contentDescription = null, tint = KksRed, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = item.subtitle, fontSize = 11.sp, color = KksTextSecondary)
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = KksTextSecondary)
        }
    }
}

@Composable
private fun EventRsvpCard(rsvp: EventRsvp) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, KksCardStroke)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = rsvp.attendee, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(text = rsvp.email, fontSize = 11.sp, color = KksTextSecondary)
                }
                if (rsvp.confirmed) {
                    StatusBadge(text = "CONFIRMED", color = androidx.compose.ui.graphics.Color(0xFF2E7D32))
                } else {
                    StatusBadge(text = "PENDING", color = androidx.compose.ui.graphics.Color(0xFFB8860B))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "${rsvp.eventName} · ${rsvp.eventDateTime}", fontSize = 12.sp, color = KksTextSecondary)
            Text(text = "${rsvp.guests} guest${if (rsvp.guests != 1) "s" else ""}", fontSize = 12.sp, fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(10.dp))

            if (rsvp.confirmed) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(onClick = { }, shape = RoundedCornerShape(8.dp)) {
                        Text("View", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(onClick = { }, shape = RoundedCornerShape(8.dp)) {
                        Text("Cancel", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Accept", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(onClick = { }, shape = RoundedCornerShape(8.dp)) {
                        Text("Reject", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun DonationRow(donation: AdminDonation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = donation.donor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(text = donation.method, fontSize = 11.sp, color = KksTextSecondary)
        }
        Text(text = donation.amount, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        if (donation.completed) {
            StatusBadge(text = "Completed", color = androidx.compose.ui.graphics.Color(0xFF2E7D32))
        } else {
            StatusBadge(text = "Pending", color = androidx.compose.ui.graphics.Color(0xFFB8860B))
        }
    }
}