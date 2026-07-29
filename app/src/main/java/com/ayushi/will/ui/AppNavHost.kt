package com.ayushi.will.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayushi.will.R
import com.ayushi.will.Cat
import com.ayushi.will.ui.auth.LoginScreen
import com.ayushi.will.ui.auth.RegisterScreen
import com.ayushi.will.ui.HomeScreen
import com.ayushi.will.ui.theme.KksRed
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // ========== SPLASH SCREEN ==========
        composable("splash") {
            LaunchedEffect(Unit) {
                delay(2000) // Show splash for 2 seconds
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            SplashScreen()
        }

        // ========== LOGIN SCREEN ==========
        composable("login") {
            LoginScreen(
                onLoginClick = { email, password ->
                    // Handle login - navigate to home
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // ========== REGISTER SCREEN ==========
        composable("register") {
            RegisterScreen(
                onRegisterClick = { fullName, email, password ->
                    // Handle registration - navigate to home
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
            HomeScreen(
                onCatClick = { cat ->
                    // Show cat details as a dialog - handled inside HomeScreen
                    // We'll pass the cat back through a callback
                },
                onBookSessionClick = { navController.navigate("book_session") },
                onMeetCatsClick = { navController.navigate("cats") },
                onDonateClick = { navController.navigate("donation") },
                onCommunityClick = { navController.navigate("community") },
                onRemindersClick = { navController.navigate("reminders") },
                onProfileClick = { navController.navigate("profile") },
                onEventsClick = { navController.navigate("events") },
                onMerchandiseClick = { navController.navigate("merchandise") }
            )
        }

        // ========== OTHER SCREENS (Placeholders) ==========
        composable("book_session") {
            BookSessionScreen(
                onBackToHome = { navController.popBackStack("home", inclusive = false) }
            )
        }
        composable("cats") { CatProfileScreen(onBookViewingClick = { navController.navigate("book_session") }) }
        composable("events") { EventsPlaceholder(onBack = { navController.popBackStack() }) }
        composable("donation") { DonationPlaceholder(onBack = { navController.popBackStack() }) }
        composable("merchandise") { MerchandisePlaceholder(onBack = { navController.popBackStack() }) }
        composable("community") { CommunityPlaceholder(onBack = { navController.popBackStack() }) }
        composable("reminders") { RemindersPlaceholder(onBack = { navController.popBackStack() }) }
        composable("profile") { ProfilePlaceholder(onBack = { navController.popBackStack() }) }
    }
}

// ========== SPLASH SCREEN ==========
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KksRed),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Kingdom Cats Sanctuary Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Kingdom Cats Sanctuary",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "A HEAVEN FOR FELINES",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                letterSpacing = 2.sp
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsPlaceholder(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Events", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KksRed)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Events - Coming Soon", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationPlaceholder(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Donate", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KksRed)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Donate - Coming Soon", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MerchandisePlaceholder(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Merchandise", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KksRed)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Merchandise - Coming Soon", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityPlaceholder(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Community", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KksRed)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Community Blog - Coming Soon", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersPlaceholder(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reminders", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KksRed)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Reminders - Coming Soon", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePlaceholder(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KksRed)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Profile - Coming Soon", fontSize = 18.sp)
        }
    }
}