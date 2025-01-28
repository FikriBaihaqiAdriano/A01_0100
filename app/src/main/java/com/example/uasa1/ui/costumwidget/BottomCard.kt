package com.example.uasa1.ui.costumwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uasa1.R

@Composable
fun BottomCard(
    content: String,
    backgroundColor: Color = Color(0xFFEEEEEE),
    textColor: Color = Color.Gray,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(100.dp)
            .shadow(8.dp, shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)) // Adding shadow for depth
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Image on the left
                Image(
                    painter = painterResource(id = R.drawable.target), // Replace with your left image resource
                    contentDescription = "Left Image",
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.None
                )

                // Centered Text
                Text(
                    text = content,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                // Image on the right
                Image(
                    painter = painterResource(id = R.drawable.target), // Replace with your right image resource
                    contentDescription = "Right Image",
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.None
                )
            }
        }
    }
}
