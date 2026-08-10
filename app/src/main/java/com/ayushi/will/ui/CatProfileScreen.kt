package com.ayushi.will.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import com.ayushi.will.ui.theme.KksTextSecondary
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.text.style.TextAlign

// Base URL for the catprofile blob container
private const val BLOB_BASE_URL = "https://kingdomcatstorage.blob.core.windows.net/catprofile"
private enum class CatCategory { KITTEN, ADULT, SENIOR }

private data class CatCardDisplay(
    val name: String,
    val ageGender: String,
    val category: CatCategory,
    val traits: List<String>,
    val badge: String? = null,
    val imageUrl: String,
    val description: String
)

// Name pool cycles through 40 cats
private val catNamePool = listOf(
    "Oliver", "Luna", "Mochi", "Whiskers", "Bella", "Simba", "Milo", "Nala",
    "Tom", "Cleo", "Shadow", "Willow", "Jasper", "Ivy", "Leo", "Ruby",
    "Felix", "Daisy", "Max", "Coco", "Charlie", "Lily", "Oscar", "Molly",
    "Loki", "Chloe", "Simon", "Zoe", "Gizmo", "Nova", "Tigger", "Penny",
    "Smokey", "Mia", "Buddy", "Rosie", "Winston", "Stella", "Duke", "Olive"
)

// Trait pairs cycles through cats
private val traitPairPool = listOf(
    "Cuddly" to "Indoor Only",
    "Playful" to "Good with Dogs",
    "Energetic" to "Vocal",
    "Gentle" to "Senior Friendly",
    "Shy" to "Warming Up",
    "Calm" to "Lap Cat",
    "Curious" to "Loves Toys",
    "Affectionate" to "Good with Kids"
)

private const val TOTAL_CATS = 40

private val dummyCats = List(TOTAL_CATS) { index ->
    // Every group of 4 -> 1 kitten, 1 senior, 2 adults
    val category = when (index % 4) {
        0 -> CatCategory.KITTEN
        1 -> CatCategory.SENIOR
        else -> CatCategory.ADULT
    }

    val gender = if (index % 2 == 0) "Male" else "Female"
    val ageGender = when (category) {
        CatCategory.KITTEN -> "${3 + (index % 6)} Months · $gender"
        CatCategory.SENIOR -> "${8 + (index % 6)} Years · $gender"
        CatCategory.ADULT -> "${1 + (index % 5)} Years · $gender"
    }

    val traitPair = traitPairPool[index % traitPairPool.size]
    val name = catNamePool[index % catNamePool.size]

    val badge = when {
        index % 10 == 0 -> "NEW ARRIVAL"
        index % 10 == 5 -> "STAFF FAVORITE"
        else -> null
    }

    CatCardDisplay(
        name = name,
        ageGender = ageGender,
        category = category,
        traits = listOf(traitPair.first, traitPair.second),
        badge = badge,
        imageUrl = "$BLOB_BASE_URL/blob${index + 1}.jpeg",
        description = "$name is ${traitPair.first.lowercase()} and ${traitPair.second.lowercase()}, currently looking for a loving forever home at Kingdom Cats Sanctuary."
    )
}

private val filterChips = listOf("ALL RESIDENTS", "KITTENS", "SENIOR CATS")

@Composable
fun CatProfileScreen(
    onBookViewingClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf(filterChips.first()) }
    val favoritedNames = remember { mutableStateOf(setOf<String>()) }
    var selectedCatForDetail by remember { mutableStateOf<CatCardDisplay?>(null) }

    val displayedCats = remember(selectedFilter) {
        when (selectedFilter) {
            "KITTENS" -> dummyCats.filter { it.category == CatCategory.KITTEN }
            "SENIOR CATS" -> dummyCats.filter { it.category == CatCategory.SENIOR }
            else -> dummyCats
        }
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // ========== TOP BAR - NO RED BACKGROUND, NO BACK BUTTON ==========
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Find Your Companion",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                // Menu Icon - calls global navigation drawer
                IconButton(onClick = onMenuClick) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
                        Text(
                            text = "Our sanctuary is home to gentle souls seeking their forever families. Every adoption saves a life and brings warmth to a home.",
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filterChips) { chip ->
                            FilterChipItem(
                                label = chip,
                                selected = chip == selectedFilter,
                                onClick = { selectedFilter = chip }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (displayedCats.isEmpty()) {
                    item {
                        Text(
                            text = "No cats in this category right now.",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                items(displayedCats, key = { it.name }) { cat ->
                    CatProfileCard(
                        cat = cat,
                        isFavorited = favoritedNames.value.contains(cat.name),
                        onFavoriteToggle = {
                            favoritedNames.value = if (favoritedNames.value.contains(cat.name)) {
                                favoritedNames.value - cat.name
                            } else {
                                favoritedNames.value + cat.name
                            }
                        },
                        onBookViewingClick = onBookViewingClick,
                        onCardClick = { selectedCatForDetail = cat },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                }
            }
            selectedCatForDetail?.let { cat ->
                CatDescriptionDialog(cat = cat, onDismiss = { selectedCatForDetail = null })
            }
        }
    }
}

@Composable
private fun FilterChipItem(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) Color.Black else Color.Transparent)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = KksCardStroke,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CatProfileCard(
    cat: CatCardDisplay,
    isFavorited: Boolean,
    onFavoriteToggle: () -> Unit,
    onBookViewingClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onCardClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, KksCardStroke),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(KksRed.copy(alpha = 0.1f))
            ) {
                AsyncImage(
                    model = cat.imageUrl,
                    contentDescription = cat.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (cat.badge != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.75f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat.badge,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cat.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = onFavoriteToggle) {
                        Icon(
                            imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = KksRed
                        )
                    }
                }

                Text(
                    text = cat.ageGender,
                    fontSize = 13.sp,
                    color = KksTextSecondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    cat.traits.forEach { trait ->
                        Text(
                            text = trait,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = KksRed
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onBookViewingClick,
                    colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Text(
                        "BOOK A VIEWING",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CatDescriptionDialog(cat: CatCardDisplay, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "About ${cat.name}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KksRed
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = cat.description,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("CLOSE", fontWeight = FontWeight.Bold, fontSize = 13.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}