package com.example.nammasuraksha.models.FeaturesScreen

import android.R.attr.text
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.nammasuraksha.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    var loading by remember { mutableStateOf(true) }
    var showDetails by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2500) // Simulate prediction delay
        loading = false
        delay(300)
        showDetails = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.surface
                    ),
                    startY = 0f,
                    endY = 800f
                )
            )
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(16.dp))

        // Header with location
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Safety Prediction",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Yelahanka, Bengaluru",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }

            // Last updated time
            Text(
                "Updated: ${java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"))}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Prediction Card
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (loading) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Analyzing Traffic Patterns...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "Checking historical accident data",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.pin),
                        contentDescription = "Risk Map",
                        modifier = Modifier.size(40.dp)
                      ,
                        contentScale = ContentScale.Crop
                    )

                    // Map overlay legend
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                "Risk Legend",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color(0xFFF44336), CircleShape)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text("High", style = MaterialTheme.typography.labelSmall)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color(0xFFFFC107), CircleShape)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text("Medium", style = MaterialTheme.typography.labelSmall)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color(0xFF4CAF50), CircleShape)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text("Low", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        AnimatedVisibility(
            visible = showDetails,
            enter = fadeIn() + slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
            exit = fadeOut() + slideOutVertically()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "Predicted High-Risk Zones",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )

                RiskItem(
                    location = "Yelahanka Junction",
                    riskLevel = "Severe",
                    riskScore = "8.7/10",
                    reasons = listOf("Peak hour congestion", "High accident history", "Poor visibility")
                )

                RiskItem(
                    location = "Mother Dairy Circle",
                    riskLevel = "Moderate",
                    riskScore = "5.2/10",
                    reasons = listOf("Frequent pedestrian crossing", "Inadequate lighting")
                )

                RiskItem(
                    location = "MVIT Road",
                    riskLevel = "Low",
                    riskScore = "2.1/10",
                    reasons = listOf("Low traffic volume", "Good road condition")
                )

                Spacer(Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        loading = true
                        showDetails = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    Spacer(Modifier.width(8.dp))
                    Text("Update Predictions")
                }

                Text(
                    "Next update in 15 minutes",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun RiskItem(
    location: String,
    riskLevel: String,
    riskScore: String,
    reasons: List<String>
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val color = when (riskLevel) {
        "Severe" -> MaterialTheme.colorScheme.error
        "Moderate" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.secondary
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { setExpanded(!expanded) },
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        location,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        riskScore,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                Surface(
                    color = color.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        riskLevel,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = color,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        "Key Risk Factors:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(Modifier.height(4.dp))
                    Column {
                        reasons.forEach { reason ->
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(color, CircleShape)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    reason,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}