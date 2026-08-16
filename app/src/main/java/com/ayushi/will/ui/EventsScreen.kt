package com.ayushi.will.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayushi.will.R
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import com.ayushi.will.ui.theme.KksTextSecondary

private data class SanctuaryEvent(
    val title: String,
    val dateTime: String,
    val description: String,
    val location: String,
    val cause: String,
    val price: String? = null,
    @DrawableRes val imageRes: Int,
    val isMostPopular: Boolean = false
)

private val sanctuaryEvents = listOf(
    SanctuaryEvent(
        title = "Nip, Sip & Paint — International Cats Day",
        dateTime = "Saturday, 8 August 2026 • 10:30 AM",
        description = "Come celebrate International Cats Day with Nip, Sip a coffee or mimosa, get creative on canvas, and spend the morning with our sanctuary cats. Includes a beverage, catnip, canvas, and paint - please bring a blanket to sit on.",
        location = "Kingdom Cats Sanctuary",
        cause = "Cause: Sanctuary Fundraiser",
        price = "R100,00",
        imageRes = R.drawable.event_nip_sip_paint,
        isMostPopular = true
    ),
    SanctuaryEvent(
        title = "Golden Hours Family Market",
        dateTime = "Sunday, 19 July 2026 • 10:00 AM – 3:00 PM",
        description = "Join us at the Golden Hours Family Market for a morning of shopping, music, and family fun - come find our stall and say hello to the team.",
        location = "21 Uitsig Road, Durban North",
        cause = "Cause: Community Market",
        imageRes = R.drawable.event_golden_hours_market
    ),
    SanctuaryEvent(
        title = "Spring Purrathon",
        dateTime = "Friday, 11 September 2026 • Overnight at the Sanctuary",
        description = "Spend all night in the enclosure with the kitties! Bring your own bedding, sleeping bag, pillow, and torch, and enjoy a night surrounded by love, purrs, and paws.",
        location = "37 Jeanne Howes Place, Crestholme",
        cause = "Cause: Sanctuary Fundraiser",
        imageRes = R.drawable.event_spring_purrathon
    ),

    SanctuaryEvent(
        title = "Picnic with the Kitties",
        dateTime = "Saturday, 5 September 2026 • 11:00 AM – 2:00 PM",
        description = "Bring your picnic and blanket and chill under the trees with the kitties. A relaxed afternoon outdoors with our resident cats roaming nearby - snacks, sunshine, and plenty of purrs.",
        location = "Kingdom Cats Sanctuary",
        cause = "Cause: Sanctuary Fundraiser",
        price = "R50,00",
        imageRes = R.drawable.event_picnic_kitties
    ),
    SanctuaryEvent(
        title = "Open Day",
        dateTime = "Sunday, 4 October 2026 • 9:00 AM – 1:00 PM",
        description = "Open day in the sanctuary to meet the kitties. Bring your picnic basket and relax in the tranquil surrounds. Tour the grounds, meet the founders, and see our rescues up close - free entry, donations welcome.",
        location = "Kingdom Cats Sanctuary",
        cause = "Cause: Community Awareness",
        imageRes = R.drawable.event_open_day
    )
)

private const val INITIAL_EVENT_COUNT = 3

@Composable
fun EventsScreen(
    onViewCalendar: () -> Unit = {},
    onRsvp: (String) -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    var showAllEvents by remember { mutableStateOf(false) }
    val visibleEvents = if (showAllEvents) sanctuaryEvents else sanctuaryEvents.take(INITIAL_EVENT_COUNT)
    val hasMoreEvents = sanctuaryEvents.size > INITIAL_EVENT_COUNT

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
                Text(
                    text = "Events",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(onClick = onMenuClick) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            EventsHeroSection(onViewCalendar = onViewCalendar)

            Spacer(modifier = Modifier.height(16.dp))

            ImpactCard(
                amount = "R12 450",
                caption = "RAISED THROUGH EVENTS",
                message = "Your participation funded 42 emergency surgeries and 150 vaccinations for our newest rescues."
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                visibleEvents.forEach { event ->
                    EventCard(event = event, onRsvp = { onRsvp(event.title) })
                }
            }

            if (hasMoreEvents) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (showAllEvents) "See Less" else "See More",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = KksRed,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showAllEvents = !showAllEvents }
                        .padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
private fun EventsHeroSection(onViewCalendar: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Image(
            painter = painterResource(id = R.drawable.events_hero_cat),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.40f),
                            Color.Black.copy(alpha = 0.60f)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.62f)
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "SUPPORT OUR MISSION",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = KksRed
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Gather for a cause, stay for the whiskers.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Join us for our upcoming sanctuary events. Every ticket purchased and every donation made goes directly to the medical care and comfort of our feline residents.",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onViewCalendar,
                colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("VIEW CALENDAR", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun ImpactCard(amount: String, caption: String, message: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.FavoriteBorder, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Impact Last Month", fontSize = 13.sp, color = Color.White.copy(alpha = 0.8f))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = amount, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = caption,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = message,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.75f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EventCard(event: SanctuaryEvent, onRsvp: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, KksCardStroke)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box {
                    Image(
                        painter = painterResource(id = event.imageRes),
                        contentDescription = event.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 100.dp, height = 130.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    if (event.isMostPopular) {
                        Box(
                            modifier = Modifier
                                .padding(6.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Black.copy(alpha = 0.75f))
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "MOST POPULAR",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = event.dateTime, fontSize = 11.sp, color = KksTextSecondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = event.title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = event.description,
                        fontSize = 11.sp,
                        color = KksTextSecondary,
                        maxLines = 4
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = event.location, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            Text(text = event.cause, fontSize = 11.sp, color = KksTextSecondary)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (event.price != null) {
                    Text(text = event.price, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }
                Button(
                    onClick = onRsvp,
                    colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("RSVP NOW", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

/*
References:
Android Developers (2026) Jetpack Compose.
Available at: https://developer.android.com/jetpack/compose
(Accessed: 10 August 2026).

Android Developers (2026) Compose layout basics — Row, Column, Box, Card, Surface.
Available at: https://developer.android.com/jetpack/compose/layouts/basics
(Accessed: 10 August 2026).

Android Developers (2026) Material Design 3 in Compose.
Available at: https://developer.android.com/jetpack/compose/designsystems/material3
(Accessed: 10 August 2026).

Android Developers (2026) State and Jetpack Compose.
Available at: https://developer.android.com/jetpack/compose/state
(Accessed: 10 August 2026).

Android Developers (2026) Scroll in Compose — verticalScroll.
Available at: https://developer.android.com/jetpack/compose/touch-input/pointer-input/scroll
(Accessed: 10 August 2026).

Android Developers (2026) Images in Compose — Image, painterResource.
Available at: https://developer.android.com/jetpack/compose/graphics/images/loading
(Accessed: 10 August 2026).

Android Developers (2026) Sizing modifiers — IntrinsicSize.
Available at: https://developer.android.com/develop/ui/compose/layouts/basics#intrinsic-measurements
(Accessed: 10 August 2026).

Google (2026) Material Symbols and Icons.
Available at: https://fonts.google.com/icons
(Accessed: 10 August 2026).

Kotlin (2026) Kotlin documentation.
Available at: https://kotlinlang.org/docs/home.html
(Accessed: 10 August 2026).
 */