package com.example.nammasuraksha.models.FeaturesScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsDashboard(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Sample data from "uploaded" CSV for Bengaluru, Yelahanka
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
    val monthlyAccidents = listOf(120f, 150f, 90f, 200f, 180f, 160f)
    val severityBreakdown = listOf(40f, 80f, 160f) // Minor, Serious, Fatal
    val severityColors = listOf(
        Color(0xFF4CAF50),  // Green for minor
        Color(0xFFFFC107),  // Amber for serious
        Color(0xFFF44336)   // Red for fatal
    )
    val severityLabels = listOf("Minor", "Serious", "Fatal")
    val hotspots = listOf("Yelahanka Main Road", "Hebbal Flyover", "Jakkur Cross")

    val totalAccidents = monthlyAccidents.sum().toInt()
    val avgMonthly = (totalAccidents / monthlyAccidents.size.toFloat()).toInt()
    val topHotspot = hotspots.first()
    val avgSeverity = if (severityBreakdown.isEmpty()) "0.0" else {
        val weightedSum = severityBreakdown.foldIndexed(0f) { index, acc, severity ->
            acc + severity * (index + 1)  // weights are 1, 2, 3 based on position
        }
        val total = severityBreakdown.sum()
        String.format("%.1f", if (total > 0) weightedSum / total else 0f)
    }
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Trends", "Hotspots")
    val scope = rememberCoroutineScope()
    var showDataTips by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Safety Analytics",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = { showDataTips = true }) {
                        Icon(Icons.Default.Info, contentDescription = "Tips")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Refresh data */ },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Location Chip for Bengaluru, Yelahanka
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = true,
                    onClick = { /* Location is fixed for this demo */ },
                    label = { Text("Bengaluru, Yelahanka") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Location",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
            }

            // Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        height = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            scope.launch {
                                selectedTab = index
                            }
                        },
                        text = {
                            Text(
                                title,
                                style = MaterialTheme.typography.labelLarge,
                                color = if (selectedTab == index) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                }
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // KPI Cards
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KpiCard("Total Accidents", "$totalAccidents", Icons.Default.TrendingUp)
                KpiCard("Avg/Month", "$avgMonthly", Icons.Default.CalendarToday)
                KpiCard("Top Hotspot", topHotspot, Icons.Default.Place)
                KpiCard("Avg Severity", avgSeverity, Icons.Default.Warning)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Charts Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        "Monthly Accident Trend - Yelahanka",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BarChart(
                        data = monthlyAccidents,
                        labels = months,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Pie Chart Card
                Card(
                    modifier = Modifier.weight(1f),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Severity Breakdown - Yelahanka",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        PieChart(
                            data = severityBreakdown,
                            labels = severityLabels,
                            colors = severityColors,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        )
                    }
                }

                // Hotspot Map Placeholder
                Card(
                    modifier = Modifier.weight(1f),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Map,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Yelahanka Hotspots",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Top 3 Locations",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Column {
                                hotspots.forEachIndexed { index, hotspot ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        Text(
                                            "${index + 1}. ",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            hotspot,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Data Tips Dialog
    if (showDataTips) {
        AlertDialog(
            onDismissRequest = { showDataTips = false },
            title = { Text("Analytics Tips") },
            text = {
                Text("This dashboard shows safety data specifically for Bengaluru, Yelahanka. " +
                        "The visualizations help identify accident trends, severity patterns, " +
                        "and high-risk locations in this area.")
            },
            confirmButton = {
                TextButton(onClick = { showDataTips = false }) {
                    Text("Got It")
                }
            }
        )
    }
}

@Composable
private fun KpiCard(title: String, value: String, icon: ImageVector) {
    val animatedColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primaryContainer,
        animationSpec = tween(500)
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = animatedColor),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .width(160.dp)
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun BarChart(data: List<Float>, labels: List<String>, modifier: Modifier = Modifier) {
    val max = remember(data) { (data.maxOrNull() ?: 1f) * 1.2f } // Add 20% headroom
    val colorScheme = MaterialTheme.colorScheme
    val barColors = remember {
        listOf(
            colorScheme.primary,
            colorScheme.secondary,
            colorScheme.tertiary
        )
    }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barWidth = size.width / (data.size * 1.8f)
            val labelPadding = 8.dp.toPx()

            // Draw grid lines
            val gridLines = 4
            repeat(gridLines) { i ->
                val yPos = size.height * (1f - (i + 1).toFloat() / gridLines)
                drawLine(
                    color = colorScheme.onSurface.copy(alpha = 0.1f),
                    start = Offset(0f, yPos),
                    end = Offset(size.width, yPos),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Draw bars
            data.forEachIndexed { i, v ->
                val left = i * (barWidth * 1.5f) + barWidth / 4
                val top = size.height * (1f - v / max)
                val color = barColors[i % barColors.size]

                drawRoundRect(
                    color = color,
                    topLeft = Offset(left, top),
                    size = Size(barWidth, size.height - top),
                    cornerRadius = CornerRadius(4.dp.toPx())
                )
            }
        }

        // X-axis labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            labels.forEach {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun PieChart(
    data: List<Float>,
    labels: List<String>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val total = remember(data) { data.sum() }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidthPx = 4.dp.toPx()
            val diameter = min(size.width, size.height) - strokeWidthPx * 2

            val topLeft = Offset(
                (size.width - diameter) / 2,
                (size.height - diameter) / 2
            )

            var startAngle = -90f
            data.forEachIndexed { i, value ->
                val sweep = (value / total) * 360f
                drawArc(
                    color = colors[i % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = true,
                    topLeft = topLeft,
                    size = Size(diameter, diameter),
                    style = Stroke(strokeWidthPx)
                )
                startAngle += sweep
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(start = 16.dp)
        ) {
            labels.forEachIndexed { i, label ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(colors[i % colors.size], RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "$label: ${String.format("%.1f%%", data[i] / total * 100)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}