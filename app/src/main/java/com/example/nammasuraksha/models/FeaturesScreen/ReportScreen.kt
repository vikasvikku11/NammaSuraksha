package com.example.nammasuraksha.models.FeaturesScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UserReportsScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    var processingSteps by remember { mutableStateOf(listOf(false, false, false)) }

    LaunchedEffect(Unit) {
        processingSteps = processingSteps.mapIndexed { index, _ ->
            delay(800L * (index + 1))
            true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Report Analysis",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(Modifier.height(32.dp))

        AnimatedVisibility(
            visible = processingSteps.any { !it },
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            ProcessingIndicator(alpha = shimmerAlpha)
        }

        Spacer(Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            processingSteps.forEachIndexed { index, completed ->
                AnimatedReportCard(
                    index = index,
                    completed = completed,
                    title = when (index) {
                        0 -> "Analyzing User Reports"
                        1 -> "Correlating Location Data"
                        else -> "Generating Insights"
                    },
                    subtitle = when (index) {
                        0 -> "Identifying common patterns"
                        1 -> "Mapping to known hotspots"
                        else -> "Creating visual summary"
                    }
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        StatusTimeline(
            statuses = listOf("Collected", "Processed", "Analyzed", "Ready"),
            currentStep = processingSteps.count { it }
        )

        Spacer(Modifier.weight(1f))

        FloatingActionButton(
            onClick = { navController.navigate("statsDashboard") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Text(
                "View Analysis Report",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun ProcessingIndicator(alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = alpha),
                        MaterialTheme.colorScheme.secondary.copy(alpha = alpha)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Text(
                "Processing 142 reports...",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
private fun AnimatedReportCard(index: Int, completed: Boolean, title: String, subtitle: String) {
    val enterTransition = remember {
        slideInVertically(animationSpec = tween(500)) { it * (index + 1) } + fadeIn()
    }
    val exitTransition = remember { slideOutVertically() + fadeOut() }

    AnimatedVisibility(
        visible = !completed,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            ),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        strokeWidth = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.width(20.dp))
                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        subtitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusTimeline(statuses: List<String>, currentStep: Int) {
    val lineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Analysis Progress",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(Modifier.height(16.dp))

        Column {
            statuses.forEachIndexed { index, status ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(48.dp)
                ) {
                    Box(modifier = Modifier.width(32.dp)) {
                        if (index <= currentStep) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Completed",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        color = lineColor,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    Text(
                        status,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = if (index <= currentStep)
                                MaterialTheme.colorScheme.onBackground
                            else
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.padding(start = 12.dp))
                }

                if (index < statuses.lastIndex) {
                    Box(
                        modifier = Modifier
                            .padding(start = 15.dp)
                        .width(2.dp)
                        .height(24.dp)
                        .background(lineColor)
                    )
                }
            }
        }
    }
}