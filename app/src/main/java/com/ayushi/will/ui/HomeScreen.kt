@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.ayushi.will.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.ayushi.will.R
import com.ayushi.will.data.CatRepository
import com.ayushi.will.data.Founder
import com.ayushi.will.data.Review
import com.ayushi.will.Cat
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import com.ayushi.will.ui.theme.KksStar
import com.ayushi.will.ui.theme.KksTextSecondary
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextOverflow

private const val SANCTUARY_STORY = """At Kingdom Cats Sanctuary, "no-kill" isn't just a label — it's a sacred vow. We believe no cat should ever be euthanised due to lack of space or treatable medical conditions. We provide a lifetime commitment to every resident who enters our gates, ensuring they live with dignity, love, and professional care for as long as they need us.

Kingdom Cats Sanctuary has been caring for cats for 20 years, but the story began much earlier. Our founders, Greg Bower and Jenny Bower, have dedicated 40 years of their lives to rescuing and protecting cats in need. Their journey began with two unforgettable rescues: Rudi, who was tragically dumped in a box, and Whiskey, a feral cat discovered at the Beachwood Country Club. From those first two rescues, their passion grew into a lifelong mission — and the rest is history.

Today, the sanctuary provides the public with an opportunity to connect with our cats through dedicated cat-petting sessions. Visitors can book a session to spend time with our residents, experience their unique personalities, and learn more about the work involved in caring for rescued cats.

For those considering adoption, visitors can also book a viewing and adoption session with a specific cat. Through our website, prospective adopters can choose a cat they are interested in and arrange a session to meet them in person. This gives both the visitor and the cat an opportunity to become familiar with one another before an adoption decision is made.

The sanctuary also welcomes the support of our community through donations. Contributions can be made financially or through much-needed items that help us provide food, shelter, enrichment, medical care, and everyday necessities for our cats. A detailed list of requested donation items and their specific categories can be found on our Donation page, making it easy for supporters to see exactly where their contributions can make a difference.

After 40 years of rescuing cats, Greg and Jenny's mission remains the same: to give vulnerable cats a safe place where they are valued, protected, and loved. What began with Rudi and Whiskey has grown into a sanctuary dedicated to giving every cat the second chance they deserve."""

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun HomeScreen(
    onCatClick: (Cat) -> Unit = {},
    onBookSessionClick: () -> Unit = {},
    onMeetCatsClick: () -> Unit = {},
    onDonateClick: () -> Unit = {},
    onCommunityClick: () -> Unit = {},
    onRemindersClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onEventsClick: () -> Unit = {},
    onMerchandiseClick: () -> Unit = {},
    onMenuClick: () -> Unit
) {
    var cats by remember { mutableStateOf<List<Cat>>(emptyList()) }
    var founders by remember { mutableStateOf<List<Founder>>(emptyList()) }
    var reviews by remember { mutableStateOf<List<Review>>(emptyList()) }

    var selectedCat by remember { mutableStateOf<Cat?>(null) }
    var showCatDialog by remember { mutableStateOf(false) }

    // Navigation Drawer State
    var drawerOpen by remember { mutableStateOf(false) }

    // Review state - show all reviews or just one
    var showAllReviews by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        CatRepository.observeCats { cats = it }
        CatRepository.observeFounders { founders = it }
        CatRepository.observeReviews { reviews = it }
    }

    // Main Content
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                HeroSection(
                    onMenuClick = onMenuClick,
                    onBookSessionClick = onBookSessionClick,
                    onMeetCatsClick = onMeetCatsClick,
                    onDonateClick = onDonateClick,
                    onEventsClick = onEventsClick,
                    onMerchandiseClick = onMerchandiseClick,
                    onRemindersClick = onRemindersClick,
                    onProfileClick = onProfileClick
                )
            }

            item {
                NoKillPromiseSection()
            }

            item {
                SectionHeader(
                    title = "Looking for a forever home",
                    subtitle = "A few of our residents waiting to meet you!",
                    modifier = Modifier.padding(top = 24.dp)
                )
                CatsShowcase(
                    cats = cats,
                    onCatClick = { cat ->
                        selectedCat = cat
                        showCatDialog = true
                    }
                )
            }

            item {
                FoundersSection(founders = founders)
            }

            // ========== UPDATED: HOW YOU CAN HELP ==========
            item {
                HowYouCanHelpSection(
                    onDonateClick = onDonateClick,
                    onBookSessionClick = onBookSessionClick,
                    onCommunityClick = onCommunityClick
                )
            }

            // ========== UPDATED: REVIEWS SECTION ==========
            item {
                ReviewsSection(
                    reviews = reviews,
                    showAllReviews = showAllReviews,
                    onToggleShowAll = { showAllReviews = !showAllReviews }
                )
            }

            item {
                CommunityCTASection(onCommunityClick = onCommunityClick)
            }

            item {
                FooterSection()
            }
        }
    }

    // Cat Detail Popup
    if (showCatDialog && selectedCat != null) {
        CatDetailDialog(
            cat = selectedCat!!,
            onDismiss = { showCatDialog = false },
            onBookSession = {
                showCatDialog = false
                onBookSessionClick()
            }
        )
    }
}

// ========== 1. HERO SECTION ==========
@Composable
private fun HeroSection(
    onMenuClick: () -> Unit,
    onBookSessionClick: () -> Unit,
    onMeetCatsClick: () -> Unit,
    onDonateClick: () -> Unit,
    onEventsClick: () -> Unit,
    onMerchandiseClick: () -> Unit,
    onRemindersClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        // Background Cat Image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2B1818),
                            Color(0xFF4A2A2A),
                            Color(0xFF8E3B3B)
                        )
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat_home),
                contentDescription = "Cat Hero Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.85f)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.6f),
                                Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    )
            )
        }

        // Content on top of image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 28.dp)
        ) {
            // Top bar with Menu, Logo, Notifications, Profile
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Menu Icon - Opens GLOBAL slide-in drawer
                IconButton(onClick = onMenuClick) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }

                // Logo and Title
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Cat Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Kingdom Cats",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = " Sanctuary",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }

                // Notifications and Profile
                IconButton(onClick = onRemindersClick) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "Reminders",
                        tint = Color.White
                    )
                }
                IconButton(onClick = onProfileClick) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Hero Text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "NO-KILL RESCUE SANCTUARY",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Every life is worth a thousand purrs.",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 34.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Kingdom Cats Sanctuary is a dedicated no-kill haven providing permanent solace and finding forever homes for abandoned and abused cats.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onDonateClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(25.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "DONATE NOW",
                            color = KksRed,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                    OutlinedButton(
                        onClick = onMeetCatsClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(25.dp),
                        border = BorderStroke(1.dp, Color.White),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "MEET THE CATS",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

// ========== 2. NO-KILL PROMISE ==========
@Composable
private fun NoKillPromiseSection() {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = KksRed.copy(alpha = 0.08f)
        ),
        border = BorderStroke(1.dp, KksRed.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Our no-kill promise",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = KksRed
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = SANCTUARY_STORY,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = if (expanded) Int.MAX_VALUE else 4,
                overflow = if (expanded) TextOverflow.Clip else TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (expanded) "Read less" else "Read more",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = KksRed,
                modifier = Modifier.clickable { expanded = !expanded }
            )
        }
    }
}

// ========== 3. SECTION HEADER ==========
@Composable
private fun SectionHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (subtitle != null) {
            Text(
                subtitle,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

// ========== 4. CATS SHOWCASE ==========
@Composable
private fun CatsShowcase(cats: List<Cat>, onCatClick: (Cat) -> Unit) {
    if (cats.isEmpty()) {
        Text(
            "No cats available",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }

    LazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(cats) { cat ->
            CatShowcaseCard(
                cat = cat,
                onClick = { onCatClick(cat) }
            )
        }
    }
}

@Composable
private fun CatShowcaseCard(cat: Cat, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, KksCardStroke),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(KksRed.copy(alpha = 0.1f))
            ) {
                if (cat.localImageRes != null) {
                    Image(
                        painter = painterResource(id = cat.localImageRes),
                        contentDescription = cat.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (cat.imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = cat.imageUrl,
                        contentDescription = cat.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(KksRed.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🐱", fontSize = 48.sp)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                Text(
                    text = cat.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 12.dp, bottom = 10.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = cat.breed,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = cat.ageLabel,
                    fontSize = 12.sp,
                    color = KksRed,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KksRed
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Text(
                        "BOOK A VIEWING",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// ========== 5. CAT DETAIL POPUP DIALOG ==========
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CatDetailDialog(
    cat: Cat,
    onDismiss: () -> Unit,
    onBookSession: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        AnimatedVisibility(
            visible = true,
            enter = scaleIn(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300)),
            exit = scaleOut(
                animationSpec = tween(200)
            ) + fadeOut(animationSpec = tween(200))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Cat Image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .background(KksRed.copy(alpha = 0.1f))
                    ) {
                        if (cat.localImageRes != null) {
                            Image(
                                painter = painterResource(id = cat.localImageRes),
                                contentDescription = cat.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else if (cat.imageUrl.isNotBlank()) {
                            AsyncImage(
                                model = cat.imageUrl,
                                contentDescription = cat.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(KksRed.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🐱", fontSize = 64.sp)
                            }
                        }

                        // Close Button
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.5f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }

                        // Status Badge
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = KksRed
                            )
                        ) {
                            Text(
                                text = "AVAILABLE",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // Cat Info
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                cat.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                cat.ageLabel,
                                fontSize = 14.sp,
                                color = KksTextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Text(
                            cat.breed,
                            fontSize = 14.sp,
                            color = KksTextSecondary,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            cat.bio,
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            InfoChip("Indoor Only", Icons.Filled.Favorite)
                            InfoChip("Vaccinated", Icons.Filled.FavoriteBorder)
                            InfoChip("Spayed", Icons.Filled.BookmarkBorder)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                onDismiss()
                                onBookSession()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = KksRed
                            )
                        ) {
                            Icon(
                                Icons.Filled.CalendarMonth,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Book a Viewing",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, KksCardStroke)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = KksRed
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text,
                fontSize = 11.sp,
                color = KksTextSecondary
            )
        }
    }
}

// ========== 6. FOUNDERS SECTION ==========
@Composable
private fun FoundersSection(founders: List<Founder>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        SectionHeader(
            title = "Meet the founders of KCS",
            subtitle = "The two people behind Kingdom Cats Sanctuary and the vow that keeps it running."
        )

        if (founders.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(founders) { founder ->
                    FounderCard(founder = founder)
                }
            }
        }
    }
}

@Composable
private fun FounderCard(founder: Founder) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, KksCardStroke)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Founder Image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(KksRed.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                if (founder.localImageRes != null) {
                    Image(
                        painter = painterResource(id = founder.localImageRes),
                        contentDescription = founder.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else if (founder.imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = founder.imageUrl,
                        contentDescription = founder.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = KksRed
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                founder.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                founder.role,
                fontSize = 12.sp,
                color = KksRed,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))
            val description = when (founder.id) {
                "f1" -> "Passionate about animal welfare, Greg is committed to providing every cat with a safe, caring environment."
                "f2" -> "Jenny is dedicated to creating a safe and nurturing environment where every cat can heal and find a loving home."
                else -> ""
            }
            Text(
                description,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

// ========== 7. HOW YOU CAN HELP (UPDATED) ==========
@Composable
private fun HowYouCanHelpSection(
    onDonateClick: () -> Unit,
    onBookSessionClick: () -> Unit,
    onCommunityClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        SectionHeader(
            title = "How you can help",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Donate Card
        HelpCard(
            icon = Icons.Filled.VolunteerActivism,
            title = "Donate",
            description = "Give toward food, medical care, or a specific fundraiser.",
            onClick = onDonateClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            cardColor = KksRed.copy(alpha = 0.12f),
            iconColor = KksRed
        )

        // Book a Session Card
        HelpCard(
            icon = Icons.Filled.CalendarMonth,
            title = "Book a session",
            description = "Spend time with our kittens through a private therapy session.",
            onClick = onBookSessionClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            cardColor = KksRed.copy(alpha = 0.08f),
            iconColor = KksRed
        )

        // Adopt Card
        HelpCard(
            icon = Icons.Filled.Favorite,
            title = "Adopt",
            description = "Give one of our residents a forever home.",
            onClick = onBookSessionClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            cardColor = KksRed.copy(alpha = 0.05f),
            iconColor = KksRed
        )
    }
}

@Composable
private fun HelpCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardColor: Color = KksRed.copy(alpha = 0.08f),
    iconColor: Color = KksRed
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        border = BorderStroke(1.dp, KksRed.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconColor
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }

            // Arrow
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}

// ========== 8. REVIEWS SECTION (UPDATED) ==========
@Composable
private fun ReviewsSection(
    reviews: List<Review>,
    showAllReviews: Boolean,
    onToggleShowAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        SectionHeader(
            title = "Check Out Our Reviews!",
            subtitle = "Stories from adopters, donors, and visitors of the sanctuary."
        )

        if (reviews.isEmpty()) {
            Text(
                "No reviews yet",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            return
        }

        // Show first review or all reviews
        val displayReviews = if (showAllReviews) reviews else reviews.take(1)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            displayReviews.forEach { review ->
                ReviewCard(review = review, modifier = Modifier.padding(vertical = 6.dp))
            }

            // See More / See Less button
            if (reviews.size > 1) {
                TextButton(
                    onClick = onToggleShowAll,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        if (showAllReviews) "See Less" else "See More Reviews",
                        color = KksRed,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        if (showAllReviews) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = KksRed,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewCard(review: Review, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, KksCardStroke)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Name and Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(KksRed.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        review.authorName.take(2).uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = KksRed
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    review.authorName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )

                // Star rating
                Row {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < review.rating) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = null,
                            tint = KksStar,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Comment
            Text(
                "\"${review.comment}\"",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ========== 9. COMMUNITY CTA ==========
@Composable
private fun CommunityCTASection(onCommunityClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable { onCommunityClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = KksRed.copy(alpha = 0.08f)
        ),
        border = BorderStroke(1.dp, KksRed.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Join the Kingdom Cats Sanctuary Community",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = KksRed
            )
            Text(
                text = "Follow adoption stories, share cat tips, and help reunite lost cats with their families.",
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onCommunityClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = KksRed
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    "VISIT THE COMMUNITY FEED",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

// ========== 10. FOOTER ==========
@Composable
private fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = "© 2026 Kingdom Cats Sanctuary. All rights reserved.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}