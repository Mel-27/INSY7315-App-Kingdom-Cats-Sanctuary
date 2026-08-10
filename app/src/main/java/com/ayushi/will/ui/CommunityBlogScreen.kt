package com.ayushi.will.ui

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

@Composable
fun CommunityBlogScreen(
    onNewPost: () -> Unit,
    onPostClick: (CommunityPost) -> Unit,
    onMenuClick: () -> Unit
) {
    var showNewPostDialog by remember { mutableStateOf(false) }
    var showPostDetailDialog by remember { mutableStateOf(false) }
    var selectedPost by remember { mutableStateOf<CommunityPost?>(null) }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertPost by remember { mutableStateOf<CommunityPost?>(null) }
    var showExploreDialog by remember { mutableStateOf(false) }

    var postsState by remember {
        mutableStateOf(
            listOf(
                CommunityPost(
                    id = "1",
                    authorName = "Kingdom Cats Sanctuary Blog",
                    authorInitials = "KCS",
                    type = "Expert Tip",
                    title = "Creating a 'Safe Room' for Your New Cat",
                    content = "When bringing a rescue home, start them in a small, quiet space with their bed, litter box, and food to prevent overwhelm.",
                    isUrgent = false,
                    isPinned = false,
                    likes = 89,
                    dislikes = 4,
                    commentCount = 12,
                    timeAgo = "2 hours ago",
                    imageUrl = "",
                    isAdoptionStory = false,
                    hasImage = true,
                    localImageRes = R.drawable.room_cat,
                    isLiked = false,
                    isDisliked = false
                ),
                CommunityPost(
                    id = "2",
                    authorName = "Mochi's Mom",
                    authorInitials = "MM",
                    type = "Adoption Story",
                    title = "Adopted Mochi today!",
                    content = "He's already claiming the best spot on the sofa. Thank you Kingdom Cats Sanctuary! ❤️",
                    isUrgent = false,
                    isPinned = false,
                    likes = 234,
                    dislikes = 2,
                    commentCount = 45,
                    timeAgo = "3 hours ago",
                    imageUrl = "",
                    isAdoptionStory = true,
                    hasImage = true,
                    localImageRes = R.drawable.cat_ginger,
                    isLiked = false,
                    isDisliked = false
                ),
                CommunityPost(
                    id = "3",
                    authorName = "Oliver Alert",
                    authorInitials = "OA",
                    type = "Missing Cat",
                    title = "MISSING: OLIVER",
                    content = "Last seen in Oak Ridge area. Please contact us immediately if you see him.",
                    isUrgent = true,
                    isPinned = true,
                    likes = 56,
                    dislikes = 0,
                    commentCount = 8,
                    timeAgo = "2h ago",
                    imageUrl = "",
                    isAdoptionStory = false,
                    hasImage = true,
                    localImageRes = R.drawable.cat_oliver,
                    isLiked = false,
                    isDisliked = false
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
                    localImageRes = R.drawable.cat_midnight,
                    isLiked = false,
                    isDisliked = false
                ),
                CommunityPost(
                    id = "5",
                    authorName = "Kingdom Cats Sanctuary Blog",
                    authorInitials = "KCS",
                    type = "Blog Post",
                    title = "How to Cat-Proof Your Modern Home",
                    content = "Protect your furniture and keep your cat safe with these designer-approved tips.",
                    isUrgent = false,
                    isPinned = false,
                    likes = 120,
                    dislikes = 3,
                    commentCount = 18,
                    timeAgo = "8 min read",
                    imageUrl = "",
                    isAdoptionStory = false,
                    hasImage = true,
                    localImageRes = R.drawable.cat_proof,
                    isLiked = false,
                    isDisliked = false
                ),
                CommunityPost(
                    id = "6",
                    authorName = "Kingdom Cats Sanctuary Blog",
                    authorInitials = "KCS",
                    type = "Blog Post",
                    title = "Decoding the Label: What Your Cat Really Needs",
                    content = "Learn how to spot high-quality ingredients and avoid common fillers.",
                    isUrgent = false,
                    isPinned = false,
                    likes = 98,
                    dislikes = 2,
                    commentCount = 14,
                    timeAgo = "8 min read",
                    imageUrl = "",
                    isAdoptionStory = false,
                    hasImage = true,
                    localImageRes = R.drawable.cat_food,
                    isLiked = false,
                    isDisliked = false
                )
            )
        )
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize()
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
                    text = "Community",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row {
                    // New Post Button
                    IconButton(onClick = { showNewPostDialog = true }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "New Post",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    // Menu Icon - calls global navigation drawer
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    LostFoundSection(
                        onMissingClick = {
                            alertPost = postsState.find { it.id == "3" }
                            showAlertDialog = true
                        },
                        onFoundClick = {
                            alertPost = postsState.find { it.id == "4" }
                            showAlertDialog = true
                        }
                    )
                }

                item {
                    Text(
                        "Community Stories",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }

                items(postsState) { post ->
                    when {
                        post.isUrgent -> UrgentPostCard(
                            post = post,
                            onClick = {
                                selectedPost = post
                                showPostDetailDialog = true
                            },
                            onLike = {
                                val index = postsState.indexOf(post)
                                postsState = postsState.toMutableList().apply {
                                    val updated = this[index].copy(
                                        likes = if (this[index].isLiked) this[index].likes - 1 else this[index].likes + 1,
                                        isLiked = !this[index].isLiked,
                                        isDisliked = false,
                                        dislikes = if (this[index].isDisliked) this[index].dislikes - 1 else this[index].dislikes
                                    )
                                    this[index] = updated
                                }
                            },
                            onDislike = {
                                val index = postsState.indexOf(post)
                                postsState = postsState.toMutableList().apply {
                                    val updated = this[index].copy(
                                        dislikes = if (this[index].isDisliked) this[index].dislikes - 1 else this[index].dislikes + 1,
                                        isDisliked = !this[index].isDisliked,
                                        isLiked = false,
                                        likes = if (this[index].isLiked) this[index].likes - 1 else this[index].likes
                                    )
                                    this[index] = updated
                                }
                            },
                            onComment = {
                                selectedPost = post
                                showPostDetailDialog = true
                            }
                        )
                        post.isAdoptionStory -> AdoptionStoryCard(
                            post = post,
                            onClick = {
                                selectedPost = post
                                showPostDetailDialog = true
                            },
                            onLike = {
                                val index = postsState.indexOf(post)
                                postsState = postsState.toMutableList().apply {
                                    val updated = this[index].copy(
                                        likes = if (this[index].isLiked) this[index].likes - 1 else this[index].likes + 1,
                                        isLiked = !this[index].isLiked,
                                        isDisliked = false,
                                        dislikes = if (this[index].isDisliked) this[index].dislikes - 1 else this[index].dislikes
                                    )
                                    this[index] = updated
                                }
                            },
                            onDislike = {
                                val index = postsState.indexOf(post)
                                postsState = postsState.toMutableList().apply {
                                    val updated = this[index].copy(
                                        dislikes = if (this[index].isDisliked) this[index].dislikes - 1 else this[index].dislikes + 1,
                                        isDisliked = !this[index].isDisliked,
                                        isLiked = false,
                                        likes = if (this[index].isLiked) this[index].likes - 1 else this[index].likes
                                    )
                                    this[index] = updated
                                }
                            },
                            onComment = {
                                selectedPost = post
                                showPostDetailDialog = true
                            }
                        )
                        post.hasImage -> BlogPostCard(
                            post = post,
                            onClick = {
                                selectedPost = post
                                showPostDetailDialog = true
                            },
                            onLike = {
                                val index = postsState.indexOf(post)
                                postsState = postsState.toMutableList().apply {
                                    val updated = this[index].copy(
                                        likes = if (this[index].isLiked) this[index].likes - 1 else this[index].likes + 1,
                                        isLiked = !this[index].isLiked,
                                        isDisliked = false,
                                        dislikes = if (this[index].isDisliked) this[index].dislikes - 1 else this[index].dislikes
                                    )
                                    this[index] = updated
                                }
                            },
                            onDislike = {
                                val index = postsState.indexOf(post)
                                postsState = postsState.toMutableList().apply {
                                    val updated = this[index].copy(
                                        dislikes = if (this[index].isDisliked) this[index].dislikes - 1 else this[index].dislikes + 1,
                                        isDisliked = !this[index].isDisliked,
                                        isLiked = false,
                                        likes = if (this[index].isLiked) this[index].likes - 1 else this[index].likes
                                    )
                                    this[index] = updated
                                }
                            },
                            onComment = {
                                selectedPost = post
                                showPostDetailDialog = true
                            }
                        )
                        else -> CommunityPostCard(
                            post = post,
                            onClick = {
                                selectedPost = post
                                showPostDetailDialog = true
                            },
                            onLike = {
                                val index = postsState.indexOf(post)
                                postsState = postsState.toMutableList().apply {
                                    val updated = this[index].copy(
                                        likes = if (this[index].isLiked) this[index].likes - 1 else this[index].likes + 1,
                                        isLiked = !this[index].isLiked,
                                        isDisliked = false,
                                        dislikes = if (this[index].isDisliked) this[index].dislikes - 1 else this[index].dislikes
                                    )
                                    this[index] = updated
                                }
                            },
                            onDislike = {
                                val index = postsState.indexOf(post)
                                postsState = postsState.toMutableList().apply {
                                    val updated = this[index].copy(
                                        dislikes = if (this[index].isDisliked) this[index].dislikes - 1 else this[index].dislikes + 1,
                                        isDisliked = !this[index].isDisliked,
                                        isLiked = false,
                                        likes = if (this[index].isLiked) this[index].likes - 1 else this[index].likes
                                    )
                                    this[index] = updated
                                }
                            },
                            onComment = {
                                selectedPost = post
                                showPostDetailDialog = true
                            }
                        )
                    }
                }

                item {
                    ExploreAllButton(
                        onClick = { showExploreDialog = true }
                    )
                }
            }
        }
    }

    if (showNewPostDialog) {
        NewPostDialog(
            onDismiss = { showNewPostDialog = false },
            onSubmit = { title, content ->
                showNewPostDialog = false
            }
        )
    }

    if (showPostDetailDialog && selectedPost != null) {
        PostDetailDialog(
            post = selectedPost!!,
            onDismiss = { showPostDetailDialog = false },
            onLike = {},
            onComment = {}
        )
    }

    if (showAlertDialog && alertPost != null) {
        AlertDetailDialog(
            post = alertPost!!,
            onDismiss = { showAlertDialog = false },
            onContact = {
                showAlertDialog = false
            }
        )
    }

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
                    "Lost & Found",
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

            // Missing Alert
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMissingClick() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = KksRed.copy(alpha = 0.08f)
                ),
                border = BorderStroke(1.dp, KksRed.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "MISSING: OLIVER",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = KksRed
                        )
                        Text(
                            "Last seen in Oak Ridge • Reported 2h ago",
                            fontSize = 12.sp,
                            color = KksTextSecondary
                        )
                    }
                    Text(
                        "→",
                        fontSize = 16.sp,
                        color = KksRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Found Alert
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFoundClick() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = KksRed.copy(alpha = 0.05f)
                ),
                border = BorderStroke(1.dp, KksRed.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "FOUND: LUNA",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = KksRed
                        )
                        Text(
                            "Safe at Sanctuary • Checking for chip...",
                            fontSize = 12.sp,
                            color = KksTextSecondary
                        )
                    }
                    Text(
                        "→",
                        fontSize = 16.sp,
                        color = KksRed
                    )
                }
            }
        }
    }
}

// ========== URGENT POST CARD ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UrgentPostCard(
    post: CommunityPost,
    onClick: () -> Unit,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onComment: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, KksRed.copy(alpha = 0.3f)),
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
                                containerColor = KksRed.copy(alpha = 0.15f)
                            )
                        ) {
                            Text(
                                "URGENT",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = KksRed,
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
                Text(
                    "⋯",
                    fontSize = 16.sp,
                    color = KksTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (post.localImageRes != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(KksRed.copy(alpha = 0.1f))
                ) {
                    Image(
                        painter = painterResource(id = post.localImageRes),
                        contentDescription = post.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .align(Alignment.BottomStart)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f))
                                )
                            )
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                post.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = KksRed
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

            Spacer(modifier = Modifier.height(12.dp))

            PostActions(
                likes = post.likes,
                dislikes = post.dislikes,
                commentCount = post.commentCount,
                isLiked = post.isLiked,
                isDisliked = post.isDisliked,
                onLike = onLike,
                onDislike = onDislike,
                onComment = onComment
            )
        }
    }
}

// ========== COMMUNITY POST CARD ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityPostCard(
    post: CommunityPost,
    onClick: () -> Unit,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onComment: () -> Unit
) {
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
                Text(
                    "⋯",
                    fontSize = 16.sp,
                    color = KksTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (post.localImageRes != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(KksRed.copy(alpha = 0.1f))
                ) {
                    Image(
                        painter = painterResource(id = post.localImageRes),
                        contentDescription = post.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
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

            Spacer(modifier = Modifier.height(12.dp))

            PostActions(
                likes = post.likes,
                dislikes = post.dislikes,
                commentCount = post.commentCount,
                isLiked = post.isLiked,
                isDisliked = post.isDisliked,
                onLike = onLike,
                onDislike = onDislike,
                onComment = onComment
            )
        }
    }
}

// ========== ADOPTION STORY CARD ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdoptionStoryCard(
    post: CommunityPost,
    onClick: () -> Unit,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onComment: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, KksRed.copy(alpha = 0.2f)),
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
                        .background(KksRed.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "🏠",
                        fontSize = 18.sp
                    )
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
                        containerColor = KksRed.copy(alpha = 0.15f)
                    )
                ) {
                    Text(
                        "Adoption Story",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = KksRed,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (post.localImageRes != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(KksRed.copy(alpha = 0.1f))
                ) {
                    Image(
                        painter = painterResource(id = post.localImageRes),
                        contentDescription = post.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                post.content,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "#AdoptionSuccess #NewBeginnings",
                fontSize = 12.sp,
                color = KksRed,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            PostActions(
                likes = post.likes,
                dislikes = post.dislikes,
                commentCount = post.commentCount,
                isLiked = post.isLiked,
                isDisliked = post.isDisliked,
                onLike = onLike,
                onDislike = onDislike,
                onComment = onComment
            )
        }
    }
}

// ========== BLOG POST CARD ==========
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BlogPostCard(
    post: CommunityPost,
    onClick: () -> Unit,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onComment: () -> Unit
) {
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
                        "BLOG",
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(KksRed.copy(alpha = 0.2f), KksRed.copy(alpha = 0.05f))
                            )
                        )
                ) {
                    Image(
                        painter = painterResource(id = post.localImageRes),
                        contentDescription = post.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Read Full Guide →",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = KksRed
            )

            Spacer(modifier = Modifier.height(12.dp))

            PostActions(
                likes = post.likes,
                dislikes = post.dislikes,
                commentCount = post.commentCount,
                isLiked = post.isLiked,
                isDisliked = post.isDisliked,
                onLike = onLike,
                onDislike = onDislike,
                onComment = onComment
            )
        }
    }
}

// ========== POST ACTIONS (Text-based, no icons) ==========
@Composable
private fun PostActions(
    likes: Int,
    dislikes: Int,
    commentCount: Int,
    isLiked: Boolean,
    isDisliked: Boolean,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onComment: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Like
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onLike() }
        ) {
            Text(
                "👍",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                likes.toString(),
                fontSize = 13.sp,
                color = if (isLiked) KksRed else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (isLiked) FontWeight.Bold else FontWeight.Normal
            )
        }

        // Dislike
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onDislike() }
        ) {
            Text(
                "👎",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                dislikes.toString(),
                fontSize = 13.sp,
                color = if (isDisliked) KksRed else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (isDisliked) FontWeight.Bold else FontWeight.Normal
            )
        }

        // Comment
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onComment() }
        ) {
            Text(
                "💬",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                commentCount.toString(),
                fontSize = 13.sp,
                color = KksRed,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Share
        Text(
            "🔗",
            fontSize = 14.sp,
            modifier = Modifier.clickable { /* Share */ }
        )
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
                "Explore All Articles",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = KksRed
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "→",
                fontSize = 16.sp,
                color = KksRed
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
                    Text(
                        "✕",
                        fontSize = 20.sp,
                        color = KksTextSecondary,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

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

                if (post.localImageRes != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(KksRed.copy(alpha = 0.1f))
                    ) {
                        Image(
                            painter = painterResource(id = post.localImageRes),
                            contentDescription = post.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onLike() }
                    ) {
                        Text("👍", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(post.likes.toString(), fontSize = 14.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("👎", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(post.dislikes.toString(), fontSize = 14.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onComment() }
                    ) {
                        Text("💬", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
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
                        if (post.title.contains("MISSING")) "Missing Cat Alert" else "Found Cat Alert",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = KksRed
                    )
                    Text(
                        "✕",
                        fontSize = 20.sp,
                        color = KksTextSecondary,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (post.localImageRes != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(KksRed.copy(alpha = 0.1f))
                    ) {
                        Image(
                            painter = painterResource(id = post.localImageRes),
                            contentDescription = post.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
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
                            containerColor = KksRed
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
                        "All Articles",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = KksRed
                    )
                    Text(
                        "✕",
                        fontSize = 20.sp,
                        color = KksTextSecondary,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

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
                            Text(
                                "→",
                                fontSize = 14.sp,
                                color = KksRed
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
    var likes: Int = 0,
    var dislikes: Int = 0,
    var commentCount: Int = 0,
    val timeAgo: String = "",
    val imageUrl: String = "",
    val isAdoptionStory: Boolean = false,
    val hasImage: Boolean = false,
    val localImageRes: Int? = null,
    var isLiked: Boolean = false,
    var isDisliked: Boolean = false
)