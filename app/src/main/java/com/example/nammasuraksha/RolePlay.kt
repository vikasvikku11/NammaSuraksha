package com.example.nammasuraksha

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammasuraksha.models.TopPortion

@Preview
@Composable
fun RolePage() {

    Column {
        TopPortion()
        Spacer(modifier = Modifier.height(100.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = {  },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Admin Panel",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = { },

                shape = RoundedCornerShape(8.dp)
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


