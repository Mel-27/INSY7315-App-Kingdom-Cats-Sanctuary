package com.ayushi.will.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.annotation.DrawableRes
import com.ayushi.will.R
import com.ayushi.will.ui.theme.KksCardStroke
import com.ayushi.will.ui.theme.KksRed
import com.ayushi.will.ui.theme.KksTextSecondary
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu

private data class MerchItem(
    val category: String,
    val name: String,
    val description: String,
    val price: String,
    val inStock: Boolean,
    val isNew: Boolean = false,
    @DrawableRes val frontImageRes: Int,
    @DrawableRes val backImageRes: Int
)

private val merchItems = listOf(
    MerchItem(
        category = "APPAREL",
        name = "Kingdom Cats Logo Tee",
        description = "Soft cotton tee with the sanctuary logo on the front.",
        price = "R220,00",
        inStock = true,
        isNew = true,
        frontImageRes = R.drawable.merch_tee_front,
        backImageRes = R.drawable.merch_tee_back
    )
)

@Composable
fun MerchandiseScreen(
    onMenuClick: () -> Unit = {}
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ========== TOP BAR - NO RED BACKGROUND ==========
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kingdom Cats Merch",
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

            Text(
                text = "Every purchase directly supports the food, medical care, and shelter of our sanctuary residents. Wear it, sip from it, or carry it - it all helps.",
                fontSize = 12.sp,
                color = KksTextSecondary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(merchItems) { item ->
                    MerchProductCard(item = item)
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
        }
    }
}

@Composable
private fun MerchProductCard(item: MerchItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, KksCardStroke)
    ) {
        Column {
            FlippableProductImage(
                frontImageRes = item.frontImageRes,
                backImageRes = item.backImageRes,
                isNew = item.isNew
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.category,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = KksTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.description, fontSize = 12.sp, color = KksTextSecondary)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.price, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    StockBadge(inStock = item.inStock)
                }
            }
        }
    }
}

@Composable
private fun FlippableProductImage(
    @DrawableRes frontImageRes: Int,
    @DrawableRes backImageRes: Int,
    isNew: Boolean
) {
    var showFront by remember { mutableStateOf(true) }
    val rotation by animateFloatAsState(
        targetValue = if (showFront) 0f else 180f,
        animationSpec = tween(durationMillis = 500),
        label = "cardFlip"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .clickable { showFront = !showFront }
    ) {
        Image(
            painter = painterResource(id = if (rotation <= 90f) frontImageRes else backImageRes),
            contentDescription = if (rotation <= 90f) "Front of shirt" else "Back of shirt",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
        )

        if (isNew) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(KksRed)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(Alignment.TopStart)
            ) {
                Text("NEW", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "CLICK TO FLIP",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun StockBadge(inStock: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (inStock) Color(0xFF2E7D32).copy(alpha = 0.12f)
                else Color.Gray.copy(alpha = 0.15f)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = if (inStock) "In Stock" else "Out of Stock",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (inStock) Color(0xFF2E7D32) else Color.Gray
        )
    }
}