package com.example.proyekmanajemen.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uasa1.R
import com.example.uasa1.ui.navigation.DestinasiNavigasi
import com.example.uasa1.ui.view.Proyek.DestinasiDetailProyek

// Mock data class for Project
data class Project(val name: String, val description: String)

fun getProyek(): List<Project> {
    return listOf(

    )
}

object DestinasiHomePage : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Manajemen"
}
@Preview(showBackground = true)
@Composable
fun HomePage(
    onAddProject: () -> Unit = {},
    onManageTeams: () -> Unit = {},
    onManageMembers: () -> Unit = {}
) {
    val proyekList = remember { getProyek() }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize().padding()) {

            Card(
                shape = RoundedCornerShape(bottomEnd = 70.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x80000000)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 20.dp, top = 12.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Manajemen Proyek",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Kelola proyek, tugas, tim, dan anggota dengan mudah!",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(proyekList) { proyek ->
                    ProjectCard(project = proyek)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ButtonCard(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAddProject() },
                        icon = Icons.Default.Add,
                        text = "Manajemen Proyek",
                        backgroundColor = Color(0xFF9C27B0)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ButtonCard(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onManageTeams() },
                        icon = Icons.Default.Person,
                        text = "Manajemen Tim",
                        backgroundColor = Color(0xFF00BCD4)
                    )

                    ButtonCard(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onManageMembers() },
                        icon = Icons.Default.Add,
                        text = "Manajemen Anggota",
                        backgroundColor = Color(0xFFFF9800)
                    )
                }
            }
        }
    }
}

@Composable
fun ProjectCard(project: Project) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { DestinasiDetailProyek.routeWithArgs },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = project.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = project.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ButtonCard(
    modifier: Modifier,
    icon: ImageVector,
    text: String,
    backgroundColor: Color
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

