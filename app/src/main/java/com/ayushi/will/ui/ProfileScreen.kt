package com.ayushi.will.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import com.ayushi.will.ui.theme.KksTextSecondary

@Composable
fun ProfileScreen(
    userName: String = "Tiara N.",
    userEmail: String = "tiara@gmail.com",
    isDarkMode: Boolean = false,
    onToggleDarkMode: (Boolean) -> Unit = {},
    onEditProfile: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onDonationHistoryClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var showLogoutConfirm by remember { mutableStateOf(false) }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                text = "Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProfileHeader(
                userName = userName,
                userEmail = userEmail,
                onEditProfile = onEditProfile
            )

            Spacer(modifier = Modifier.height(28.dp))

            SettingsSectionLabel("PREFERENCES")
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard {
                SettingsToggleRow(
                    icon = Icons.Filled.DarkMode,
                    label = "Dark Mode",
                    checked = isDarkMode,
                    onCheckedChange = onToggleDarkMode
                )
                SettingsDivider()
                SettingsRow(
                    icon = Icons.Filled.Notifications,
                    label = "Notifications",
                    onClick = onNotificationsClick
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            SettingsSectionLabel("ACCOUNT")
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard {
                SettingsRow(
                    icon = Icons.Filled.CardGiftcard,
                    label = "Donation History",
                    onClick = onDonationHistoryClick
                )
                SettingsDivider()
                SettingsRow(
                    icon = Icons.Filled.HelpOutline,
                    label = "Help & Support",
                    onClick = onHelpClick
                )
                SettingsDivider()
                SettingsRow(
                    icon = Icons.Filled.Info,
                    label = "About Kingdom Cats",
                    onClick = onAboutClick
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = { showLogoutConfirm = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, KksRed),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = KksRed)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("LOG OUT", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showLogoutConfirm) {
        LogoutConfirmDialog(
            onConfirm = {
                showLogoutConfirm = false
                onLogout()
            },
            onDismiss = { showLogoutConfirm = false }
        )
    }
}

@Composable
private fun ProfileHeader(
    userName: String,
    userEmail: String,
    onEditProfile: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(KksRed.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = null,
                tint = KksRed,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = userName, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = userEmail, fontSize = 12.sp, color = KksTextSecondary)
        }
        TextButton(onClick = onEditProfile) {
            Text("Edit", color = KksRed, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }
    }
}

@Composable
private fun SettingsSectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        color = KksTextSecondary
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, KksCardStroke)
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(color = KksCardStroke, thickness = 1.dp)
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = KksTextSecondary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Text(text = label, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = KksTextSecondary,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun SettingsToggleRow(
    icon: ImageVector,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = KksTextSecondary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Text(text = label, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = KksRed,
                checkedThumbColor = Color.White
            )
        )
    }
}

@Composable
private fun LogoutConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Log out of Kingdom Cats?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "You'll need to sign in again to book viewings, donate, or check reminders.",
                    fontSize = 13.sp,
                    color = KksTextSecondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, KksCardStroke)
                    ) {
                        Text("Cancel", color = MaterialTheme.colorScheme.onSurface)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KksRed),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Log Out", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}