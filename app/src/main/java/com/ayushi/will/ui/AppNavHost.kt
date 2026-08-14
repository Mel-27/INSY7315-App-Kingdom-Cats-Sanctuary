package com.ayushi.will.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayushi.will.R
import com.ayushi.will.ui.auth.LoginScreen
import com.ayushi.will.ui.auth.RegisterScreen
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import com.ayushi.will.ui.theme.KksTextSecondary
import com.ayushi.will.ui.theme.KksWhite
import kotlinx.coroutines.delay
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.res.painterResource

private const val ADMIN_EMAIL = "admin@kingdomcats.com"
private const val ADMIN_PASSWORD = "Admin123!"

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    // ========== GLOBAL DRAWER STATE ==========
    var drawerOpen by remember { mutableStateOf(false) }
    var showContactDialog by remember { mutableStateOf(false) }

    // ========== CURRENT SCREEN FOR DRAWER HIGHLIGHT ==========
    var currentRoute by remember { mutableStateOf("home") }

    Box(modifier = Modifier.fillMaxSize()) {
        // ========== MAIN NAVIGATION ==========
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            // Splash Screen
            composable("splash") {
                LaunchedEffect(Unit) {
                    delay(2000)
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
                SplashScreen()
            }

            // Login Screen
            composable("login") {
                LoginScreen(
                    onLoginClick = { email, password ->
                        val destination = if (email == ADMIN_EMAIL && password == ADMIN_PASSWORD) "admin" else "home"
                        navController.navigate(destination) {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate("register")
                    }
                )
            }

            // Register Screen
            composable("register") {
                RegisterScreen(
                    onRegisterClick = { _, _, _ ->
                        navController.navigate("home") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                )
            }

            // ========== HOME SCREEN ==========
            composable("home") {
                currentRoute = "home"
                HomeScreen(
                    onMenuClick = { drawerOpen = true },
                    onCatClick = { /* handled in HomeScreen */ },
                    onBookSessionClick = { navController.navigate("book_session") },
                    onMeetCatsClick = { navController.navigate("cat_profile") },
                    onDonateClick = { navController.navigate("donation") },
                    onCommunityClick = { navController.navigate("community") },
                    onRemindersClick = { navController.navigate("reminders") },
                    onProfileClick = { navController.navigate("profile") },
                    onEventsClick = { navController.navigate("events") },
                    onMerchandiseClick = { navController.navigate("merchandise") }
                )
            }

            // ========== BOOK SESSION SCREEN ==========
            composable("book_session") {
                currentRoute = "adopt"
                BookSessionScreen(
                    onBackToHome = { navController.popBackStack() },
                    onMenuClick = { drawerOpen = true }
                )
            }

            // ========== CAT PROFILE SCREEN ==========
            composable("cat_profile") {
                currentRoute = "adopt"
                CatProfileScreen(
                    onBookViewingClick = { navController.navigate("book_session") },
                    onMenuClick = { drawerOpen = true }
                )
            }

            //Admin dashboard screen
            composable("admin") {
                currentRoute = "admin"
                AdminDashboardScreen(
                    onMenuClick = { drawerOpen = true }
                )
            }

            // ========== EVENTS SCREEN ==========
            composable("events") {
                currentRoute = "events"
                EventsScreen(
                    onViewCalendar = { /* Handle calendar view */ },
                    onRsvp = { eventTitle ->
                        // Handle RSVP
                    },
                    onMenuClick = { drawerOpen = true }
                )
            }

            // ========== DONATION SCREEN ==========
            composable("donation") {
                currentRoute = "donate"
                DonationScreen(
                    onBackToHome = { navController.popBackStack() },
                    onMenuClick = { drawerOpen = true }
                )
            }

            // ========== MERCHANDISE SCREEN ==========
            composable("merchandise") {
                currentRoute = "merchandise"
                MerchandiseScreen(
                    onMenuClick = { drawerOpen = true }
                )
            }

            // ========== COMMUNITY BLOG SCREEN ==========
            composable("community") {
                currentRoute = "community"
                CommunityBlogScreen(
                    onNewPost = { /* Handle new post */ },
                    onPostClick = { /* Handle post click */ },
                    onMenuClick = { drawerOpen = true }
                )
            }

            // ========== REMINDERS SCREEN ==========
            composable("reminders") {
                currentRoute = "reminders"
                RemindersScreen(
                    onDismissReminder = { /* Handle dismiss */ },
                    onMenuClick = { drawerOpen = true }
                )
            }

            // ========== PROFILE SCREEN ==========
            composable("profile") {
                currentRoute = "profile"
                ProfileScreen(
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onMenuClick = { drawerOpen = true }
                )
            }
        }

        // ========== GLOBAL NAVIGATION DRAWER ==========
        GlobalNavigationDrawer(
            drawerState = drawerOpen,
            currentRoute = currentRoute,
            onDismiss = { drawerOpen = false },
            onNavigate = { destination ->
                drawerOpen = false
                when (destination) {
                    "home" -> {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                    "adopt" -> {
                        navController.navigate("book_session") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                    "donate" -> {
                        navController.navigate("donation") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                    "events" -> {
                        navController.navigate("events") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                    "merchandise" -> {
                        navController.navigate("merchandise") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                    "community" -> {
                        navController.navigate("community") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                    "profile" -> {
                        navController.navigate("profile") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                    "contact" -> {
                        showContactDialog = true
                    }
                }
            },
            onContactClick = {
                drawerOpen = false
                showContactDialog = true
            }
        )

        // ========== CONTACT DIALOG ==========
        if (showContactDialog) {
            ContactDialog(onDismiss = { showContactDialog = false })
        }
    }
}

// ========== GLOBAL NAVIGATION DRAWER ==========
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GlobalNavigationDrawer(
    drawerState: Boolean,
    currentRoute: String,
    onDismiss: () -> Unit,
    onNavigate: (String) -> Unit,
    onContactClick: () -> Unit
) {
    AnimatedVisibility(
        visible = drawerState,
        enter = slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(200)),
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { onDismiss() }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .align(Alignment.CenterStart),
                shape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    // Drawer Header with Logo
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                "Kingdom Cats",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = KksRed
                            )
                            Text(
                                "Sanctuary",
                                fontSize = 12.sp,
                                color = KksTextSecondary
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = KksCardStroke
                    )

                    // Navigation Items
                    DrawerItemGlobal(
                        icon = Icons.Filled.Home,
                        title = "Home",
                        onClick = { onNavigate("home") },
                        isSelected = currentRoute == "home"
                    )
                    DrawerItemGlobal(
                        icon = Icons.Filled.Pets,
                        title = "Adopt",
                        onClick = { onNavigate("adopt") },
                        isSelected = currentRoute == "adopt"
                    )
                    DrawerItemGlobal(
                        icon = Icons.Filled.VolunteerActivism,
                        title = "Donate",
                        onClick = { onNavigate("donate") },
                        isSelected = currentRoute == "donate"
                    )
                    DrawerItemGlobal(
                        icon = Icons.Filled.CalendarMonth,
                        title = "Events",
                        onClick = { onNavigate("events") },
                        isSelected = currentRoute == "events"
                    )
                    DrawerItemGlobal(
                        icon = Icons.Filled.ShoppingCart,
                        title = "Merchandise",
                        onClick = { onNavigate("merchandise") },
                        isSelected = currentRoute == "merchandise"
                    )
                    DrawerItemGlobal(
                        icon = Icons.Filled.People,
                        title = "Community",
                        onClick = { onNavigate("community") },
                        isSelected = currentRoute == "community"
                    )
                    DrawerItemGlobal(
                        icon = Icons.Filled.Person,
                        title = "Profile",
                        onClick = { onNavigate("profile") },
                        isSelected = currentRoute == "profile"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = KksCardStroke
                    )

                    // Contact Item
                    DrawerItemGlobal(
                        icon = Icons.Filled.ContactMail,
                        title = "Contact Us",
                        onClick = onContactClick,
                        isSelected = false
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Footer
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = KksCardStroke
                    )
                    Text(
                        "© 2026 Kingdom Cats",
                        fontSize = 11.sp,
                        color = KksTextSecondary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerItemGlobal(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit,
    isSelected: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) KksRed.copy(alpha = 0.1f) else Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = if (isSelected) KksRed else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                title,
                fontSize = 15.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) KksRed else MaterialTheme.colorScheme.onSurface
            )
            if (isSelected) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Selected",
                    tint = KksRed,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// ========== CONTACT DIALOG ==========
@Composable
fun ContactDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 520.dp),
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
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Contact Us",
                        fontSize = 22.sp,
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

                Spacer(modifier = Modifier.height(16.dp))

                // Contact Info Items
                ContactInfoItem(
                    icon = Icons.Filled.Phone,
                    label = "Phone",
                    value = "+27 82 902 0383",
                    onClick = { /* Open phone dialer */ }
                )

                ContactInfoItem(
                    icon = Icons.Filled.Email,
                    label = "Email",
                    value = "kingdomcatssanctuary01@gmail.com",
                    onClick = { /* Open email app */ }
                )

                ContactInfoItem(
                    icon = Icons.Filled.LocationOn,
                    label = "Address",
                    value = "37 Jeanne Howes Place, Waterfall, Durban, South Africa",
                    onClick = { /* Open maps */ }
                )

                ContactInfoItem(
                    icon = Icons.Filled.Schedule,
                    label = "Hours",
                    value = "Mon - Sat: 9am - 5pm",
                    onClick = null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ========== SOCIAL MEDIA SECTION ==========
                Text(
                    "Follow Us",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Facebook
                SocialMediaItem(
                    icon = R.drawable.ic_facebook,
                    label = "Facebook",
                    handle = "@kingdomcatssanctuary",
                    onClick = { /* Open Facebook */ }
                )

                // Instagram
                SocialMediaItem(
                    icon = R.drawable.ic_instagram,
                    label = "Instagram",
                    handle = "@kingdomcatssanctuary",
                    onClick = { /* Open Instagram */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ========== ACTION BUTTONS ==========
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* Share contact info */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share")
                    }
                    Button(
                        onClick = { /* Open phone */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KksRed
                        )
                    ) {
                        Icon(
                            Icons.Filled.Call,
                            contentDescription = "Call",
                            modifier = Modifier.size(18.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Call", color = Color.White)
                    }
                }
            }
        }
    }
}

// ========== SOCIAL MEDIA ITEM ==========
@Composable
fun SocialMediaItem(
    icon: Int,
    label: String,
    handle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Social Media Icon
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    label,
                    fontSize = 11.sp,
                    color = KksTextSecondary
                )
                Text(
                    handle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = KksRed
                )
            }
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = "Open",
                tint = KksRed,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ContactInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    onClick: (() -> Unit)?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = KksRed,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    label,
                    fontSize = 11.sp,
                    color = KksTextSecondary
                )
                Text(
                    value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            if (onClick != null) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = "Action",
                    tint = KksRed
                )
            }
        }
    }
}

// ========== SPLASH SCREEN ==========
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.cat_splash_logo),
                contentDescription = "Kingdom Cats Sanctuary Logo",
                modifier = Modifier.size(180.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = KksRed,
                strokeWidth = 3.dp
            )
        }
    }
}