package com.ayushi.will.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ayushi.will.R
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import com.ayushi.will.ui.theme.KksTextSecondary
import com.ayushi.will.ui.theme.KksWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityBlogScreen(
    onBack: () -> Unit,
    onNewPost: () -> Unit,
    onPostClick: (CommunityPost) -> Unit
) {
    // Dialog states
    var showNewPostDialog by remember { mutableStateOf(false) }
    var showPostDetailDialog by remember { mutableStateOf(false) }
    var selectedPost by remember { mutableStateOf<CommunityPost?>(null) }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertPost by remember { mutableStateOf<CommunityPost?>(null) }
    var showExploreDialog by remember { mutableStateOf(false) }

    // Sample data with image support
    val posts = remember {
        listOf(
            CommunityPost(
                id = "1",
                authorName = "Sarah Chen",
                authorInitials = "SC",
                type = "Expert Tip",
                title = "Creating a 'Safe Room' for Your New Cat",
                content = "When bringing a rescue home, start them in a small, quiet space with their bed, litter box, and food to prevent overwhelm. This helps them adjust gradually and build confidence in their new environment.",
                isUrgent = false,
                isPinned = false,
                likes = 89,
                dislikes = 4,
                commentCount = 12,
                timeAgo = "2 hours ago",
                imageUrl = "",
                isAdoptionStory = false,
                hasImage = true,
                localImageRes = R.drawable.cat_ginger
            ),
            CommunityPost(
                id = "2",
                authorName = "Mochi's Mom",
                authorInitials = "MM",
                type = "Adoption Story",
                title = "Adopted Mochi today!",
                content = "He's already claiming the best spot on the sofa. Thank you Kingdom Cats Sanctuary! ❤️ #AdoptionSuccess #NewBeginnings",
                isUrgent = false,
                isPinned = false,
                likes = 234,
                dislikes = 2,
                commentCount = 45,
                timeAgo = "3 hours ago",
                imageUrl = "",
                isAdoptionStory = true,
                hasImage = true,
                localImageRes = R.drawable.cat_oliver
            ),
            CommunityPost(
                id = "3",
                authorName = "Oliver Alert",
                authorInitials = "OA",
                type = "Missing Cat",
                title = "⚠️ MISSING: OLIVER",
                content = "Last seen in Oak Ridge area. Please contact us immediately if you see him. He's a friendly tabby with a red collar.",
                isUrgent = true,
                isPinned = true,
                likes = 56,
                dislikes = 0,
                commentCount = 8,
                timeAgo = "2h ago",
                imageUrl = "",
                isAdoptionStory = false,
                hasImage = true,
                localImageRes = R.drawable.cat_oliver
            ),
            CommunityPost(
                id = "4",
                authorName = "Luna's Rescuer",
                authorInitials = "LR",
                type = "Found Cat",
                title = "FOUND: LUNA",
                content = "Safe at Sanctuary. Checking for chip... She's a beautiful black cat, very friendly.",
                isUrgent = false,
                isPinned = false,
                likes = 45,
                dislikes = 1,
                commentCount = 5,
                timeAgo = "5h ago",
                imageUrl = "",
                isAdoptionStory = false,
                hasImage = true,
                localImageRes = R.drawable.cat_midnight
            ),
            CommunityPost(
                id = "5",
                authorName = "Whiskers Blog",
                authorInitials = "WB",
                type = "Blog Post",
                title = "How to Cat-Proof Your Modern Home",
                content = "Protect your furniture and keep your cat safe with these designer-approved tips for a harmonious living space. From cord management to plant safety, we've got you covered.",
                isUrgent = false,
                isPinned = false,
                likes = 120,
                dislikes = 3,
                commentCount = 18,
                timeAgo = "8 min read",
                imageUrl = "",
                isAdoptionStory = false,
                hasImage = true,
                localImageRes = R.drawable.cat_ginger
            ),
            CommunityPost(
                id = "6",
                authorName = "Nutrition Guide",
                authorInitials = "NG",
                type = "Blog Post",
                title = "Decoding the Label: What Your Cat Really Needs",
                content = "Learn how to spot high-quality ingredients and avoid common fillers in commercial cat food brands. Your cat's health starts with what's in their bowl.",
                isUrgent = false,
                isPinned = false,
                likes = 98,
                dislikes = 2,
                commentCount = 14,
                timeAgo = "8 min read",
                imageUrl = "",
                isAdoptionStory = false,
                hasImage = true,
                localImageRes = R.drawable.cat_midnight
            )
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Community",
                        color = KksWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = KksWhite
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showNewPostDialog = true }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "New Post",
                            tint = KksWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = KksRed
                ),
                modifier = Modifier.height(64.dp)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Lost & Found Section
            item {
                LostFoundSection(
                    onMissingClick = {
                        alertPost = posts.find { it.id == "3" }
                        showAlertDialog = true
                    },
                    onFoundClick = {
                        alertPost = posts.find { it.id == "4" }
                        showAlertDialog = true
                    }
                )
            }

            // Community Stories Header
            item {
                Text(
                    "Community Stories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            // Posts
            items(posts) { post ->
                when {
                    post.isUrgent -> UrgentPostCard(
                        post = post,
                        onClick = {
                            selectedPost = post
                            showPostDetailDialog = true
                        }
                    )
                    post.isAdoptionStory -> AdoptionStoryCard(
                        post = post,
                        onClick = {
                            selectedPost = post
                            showPostDetailDialog = true
                        }
                    )
                    post.hasImage -> BlogPostCard(
                        post = post,
                        onClick = {
                            selectedPost = post
                            showPostDetailDialog = true
                        }
                    )
                    else -> CommunityPostCard(
                        post = post,
                        onClick = {
                            selectedPost = post
                            showPostDetailDialog = true
                        }
                    )
                }
            }

            // Explore All Articles
            item {
                ExploreAllButton(
                    onClick = { showExploreDialog = true }
                )
            }
        }
    }

    // ========== DIALOGS ==========

    // New Post Dialog
    if (showNewPostDialog) {
        NewPostDialog(
            onDismiss = { showNewPostDialog = false },
            onSubmit = { title, content ->
                showNewPostDialog = false
                // In a real app, this would save to Firebase
            }
        )
    }

    // Post Detail Dialog
    if (showPostDetailDialog && selectedPost != null) {
        PostDetailDialog(
            post = selectedPost!!,
            onDismiss = { showPostDetailDialog = false },
            onLike = {
                // Handle like
            },
            onComment = {
                // Handle comment
            }
        )
    }

    // Alert Dialog (Lost/Found)
    if (showAlertDialog && alertPost != null) {
        AlertDetailDialog(
            post = alertPost!!,
            onDismiss = { showAlertDialog = false },
            onContact = {
                showAlertDialog = false
                // Show contact info
            }
        )
    }

    // Explore All Dialog
    if (showExploreDialog) {
        ExploreAllDialog(
            onDismiss = { showExploreDialog = false }
        )
    }
}

// ========== LOST & FOUND SECTION ==========
@Composable
private fun LostFoundSection(
    onMissingClick: () -> Unit,
    onFoundClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, KksCardStroke),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "📢 Lost & Found",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KksRed
                )
                TextButton(
                    onClick = { /* View all */ },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        "View All",
                        fontSize = 12.sp,
                        color = KksRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Missing Alert - Clickable
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMissingClick() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF3E0)
                ),
                border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("⚠️", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "MISSING: OLIVER",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Red
                        )
                        Text(
                            "Last seen in Oak Ridge • Reported 2h ago",
                            fontSize = 12.sp,
                            color = KksTextSecondary
                        )
                    }
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Found Alert - Clickable
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFoundClick() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                ),
                border = BorderStroke(1.dp, Color(0xFF4CAF50).copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🐾", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "FOUND: LUNA",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF2E7D32)
                        )
                        Text(
                            "Safe at Sanctuary • Checking for chip...",
                            fontSize = 12.sp,
                            color = KksTextSecondary
                        )
                    }
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32)
                    )
                }
            }
        }
    }
}

// ========== URGENT POST CARD ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UrgentPostCard(post: CommunityPost, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Red.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        post.authorInitials,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            post.authorName,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Card(
                            shape = RoundedCornerShape(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Red.copy(alpha = 0.15f)
                            )
                        ) {
                            Text(
                                "URGENT",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        post.timeAgo,
                        fontSize = 11.sp,
                        color = KksTextSecondary
                    )
                }
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = null,
                    tint = KksTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (post.localImageRes != null) {
                Image(
                    painter = painterResource(id = post.localImageRes),
                    contentDescription = post.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(KksRed.copy(alpha = 0.1f))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                post.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )

            Text(
                post.content,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            PostActions(post = post)
        }
    }
}

// ========== COMMUNITY POST CARD ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityPostCard(post: CommunityPost, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, KksCardStroke),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(KksRed.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        post.authorInitials,
                        fontWeight = FontWeight.Bold,
                        color = KksRed,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        post.authorName,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            post.timeAgo,
                            fontSize = 11.sp,
                            color = KksTextSecondary
                        )
                        if (post.type.isNotBlank()) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Card(
                                shape = RoundedCornerShape(4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = KksRed.copy(alpha = 0.1f)
                                )
                            ) {
                                Text(
                                    post.type,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = KksRed,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = null,
                    tint = KksTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (post.localImageRes != null) {
                Image(
                    painter = painterResource(id = post.localImageRes),
                    contentDescription = post.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(KksRed.copy(alpha = 0.1f))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                post.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                post.content,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            PostActions(post = post)
        }
    }
}

// ========== ADOPTION STORY CARD ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdoptionStoryCard(post: CommunityPost, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8E1)
        ),
        border = BorderStroke(1.dp, Color(0xFFFFD54F).copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFD54F).copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🏠", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        post.authorName,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Text(
                        post.timeAgo,
                        fontSize = 11.sp,
                        color = KksTextSecondary
                    )
                }
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFD54F).copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        "❤️ Adoption Story",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (post.localImageRes != null) {
                Image(
                    painter = painterResource(id = post.localImageRes),
                    contentDescription = post.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFD54F).copy(alpha = 0.2f))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                post.content,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "#AdoptionSuccess #NewBeginnings",
                fontSize = 12.sp,
                color = KksRed,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            PostActions(post = post)
        }
    }
}

// ========== BLOG POST CARD ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BlogPostCard(post: CommunityPost, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, KksCardStroke),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = KksRed.copy(alpha = 0.1f)
                    )
                ) {
                    Text(
                        "📖 BLOG",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = KksRed,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    post.timeAgo,
                    fontSize = 11.sp,
                    color = KksTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (post.localImageRes != null) {
                Image(
                    painter = painterResource(id = post.localImageRes),
                    contentDescription = post.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(KksRed.copy(alpha = 0.2f), KksRed.copy(alpha = 0.05f))
                            )
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                post.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                post.content,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Read Full Guide →",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = KksRed
            )

            Spacer(modifier = Modifier.height(8.dp))

            PostActions(post = post)
        }
    }
}

// ========== POST ACTIONS ==========
@Composable
private fun PostActions(post: CommunityPost) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.ThumbUp,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = KksRed
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(post.likes.toString(), fontSize = 12.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.ThumbDown,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = KksTextSecondary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(post.dislikes.toString(), fontSize = 12.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.ChatBubbleOutline,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = KksTextSecondary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(post.commentCount.toString(), fontSize = 12.sp)
        }
    }
}

// ========== EXPLORE ALL BUTTON ==========
@Composable
private fun ExploreAllButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = KksRed.copy(alpha = 0.08f)
        ),
        border = BorderStroke(1.dp, KksRed.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "📚 Explore All Articles",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = KksRed
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = KksRed
            )
        }
    }
}

// ========== NEW POST DIALOG ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewPostDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("General") }
    val postTypes = listOf("General", "Expert Tip", "Adoption Story", "Lost Cat", "Found Cat")

    Dialog(onDismissRequest = onDismiss) {
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
                    .padding(20.dp)
            ) {
                Text(
                    "Create New Post",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = KksRed
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Post Type Dropdown
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Post Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KksRed
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        postTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KksRed
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KksRed
                    ),
                    minLines = 4
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            if (title.isNotBlank() && content.isNotBlank()) {
                                onSubmit(title, content)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KksRed
                        )
                    ) {
                        Text("Post", color = KksWhite)
                    }
                }
            }
        }
    }
}

// ========== POST DETAIL DIALOG ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetailDialog(
    post: CommunityPost,
    onDismiss: () -> Unit,
    onLike: () -> Unit,
    onComment: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 550.dp),
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
                    .padding(20.dp)
            ) {
                // Header with close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Post Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = KksRed
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = KksTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Author
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(KksRed.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            post.authorInitials,
                            fontWeight = FontWeight.Bold,
                            color = KksRed,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            post.authorName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            "${post.timeAgo} • ${post.type}",
                            fontSize = 12.sp,
                            color = KksTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Image if available
                if (post.localImageRes != null) {
                    Image(
                        painter = painterResource(id = post.localImageRes),
                        contentDescription = post.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(KksRed.copy(alpha = 0.1f))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Content
                Text(
                    post.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    post.content,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onLike() }
                    ) {
                        Icon(
                            Icons.Filled.ThumbUp,
                            contentDescription = null,
                            tint = KksRed
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(post.likes.toString(), fontSize = 14.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.ThumbDown,
                            contentDescription = null,
                            tint = KksTextSecondary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(post.dislikes.toString(), fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onComment() }
                    ) {
                        Icon(
                            Icons.Filled.ChatBubbleOutline,
                            contentDescription = null,
                            tint = KksRed
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(post.commentCount.toString(), fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KksRed
                    )
                ) {
                    Text("Close", color = KksWhite)
                }
            }
        }
    }
}

// ========== ALERT DETAIL DIALOG ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlertDetailDialog(
    post: CommunityPost,
    onDismiss: () -> Unit,
    onContact: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        if (post.title.contains("MISSING")) "⚠️ Missing Cat Alert" else "🐾 Found Cat Alert",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (post.title.contains("MISSING")) Color.Red else Color(0xFF2E7D32)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = KksTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (post.localImageRes != null) {
                    Image(
                        painter = painterResource(id = post.localImageRes),
                        contentDescription = post.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(KksRed.copy(alpha = 0.1f))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Text(
                    post.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    post.content,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Close")
                    }
                    Button(
                        onClick = onContact,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (post.title.contains("MISSING")) Color.Red else Color(0xFF2E7D32)
                        )
                    ) {
                        Text(
                            if (post.title.contains("MISSING")) "Contact" else "View Details",
                            color = KksWhite
                        )
                    }
                }
            }
        }
    }
}

// ========== EXPLORE ALL DIALOG ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExploreAllDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "📚 All Articles",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = KksRed
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = KksTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Sample blog articles
                listOf(
                    "How to Cat-Proof Your Modern Home" to "5 min read",
                    "Decoding the Label: What Your Cat Really Needs" to "8 min read",
                    "Creating a Safe Room for Your New Cat" to "4 min read",
                    "Understanding Your Cat's Body Language" to "6 min read"
                ).forEach { (title, time) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { /* Navigate to article */ },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = KksRed.copy(alpha = 0.05f)
                        ),
                        border = BorderStroke(1.dp, KksCardStroke)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                time,
                                fontSize = 11.sp,
                                color = KksTextSecondary
                            )
                            Icon(
                                Icons.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = KksRed
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KksRed
                    )
                ) {
                    Text("Close", color = KksWhite)
                }
            }
        }
    }
}

// ========== DATA CLASS ==========
data class CommunityPost(
    val id: String = "",
    val authorName: String = "",
    val authorInitials: String = "",
    val type: String = "",
    val title: String = "",
    val content: String = "",
    val isUrgent: Boolean = false,
    val isPinned: Boolean = false,
    val likes: Int = 0,
    val dislikes: Int = 0,
    val commentCount: Int = 0,
    val timeAgo: String = "",
    val imageUrl: String = "",
    val isAdoptionStory: Boolean = false,
    val hasImage: Boolean = false,
    val localImageRes: Int? = null
)