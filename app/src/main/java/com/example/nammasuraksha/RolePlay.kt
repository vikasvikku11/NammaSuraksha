package com.example.nammasuraksha

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammasuraksha.models.TopPortion

@Preview
@Composable
fun RolePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopPortion()

        Spacer(modifier = Modifier.height(200.dp))

        Box(
            modifier = Modifier
                .size(300.dp) // Make it a perfect square
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant) // Light background
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { /* Navigate to Admin Panel */ },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = "Admin Panel",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = { /* Navigate to User Panel */ },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = "User Panel",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
