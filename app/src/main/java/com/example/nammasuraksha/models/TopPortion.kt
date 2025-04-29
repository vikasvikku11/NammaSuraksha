package com.example.nammasuraksha.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammasuraksha.R

@Composable
fun TopPortion(modifier: Modifier = Modifier) {

    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 55.dp, top = 25.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.the_karnataka_government_kannada_seeklogo),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    "Karnataka State Police",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(top = 12.dp)
                )
                Text("Government of Karnataka", fontSize = 12.sp, fontWeight = FontWeight.W400)
            }
        }
    }
}