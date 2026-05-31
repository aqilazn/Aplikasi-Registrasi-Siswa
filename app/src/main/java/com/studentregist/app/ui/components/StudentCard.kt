package com.studentregist.app.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studentregist.app.data.entity.Student
import com.studentregist.app.ui.*

@Composable
fun StudentCard(
    student: Student,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val avatarColor = if (student.gender == "Laki-laki") Indigo600 else Teal600
    val genderIcon = if (student.gender == "Laki-laki") Icons.Default.Male else Icons.Default.Female

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Avatar circle
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(avatarColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        student.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        student.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Slate900
                    )
                    Text(
                        student.nrp,
                        fontSize = 13.sp,
                        color = Slate600
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Chip(text = student.faculty.split(" ").lastOrNull() ?: student.faculty, color = Indigo50, textColor = Indigo700)
                        Chip(text = student.year, color = Color(0xFFCCFBF1), textColor = Teal600)
                    }
                }

                // Actions
                Row {
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Default.Edit, null, tint = Indigo600, modifier = Modifier.size(18.dp))
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Default.Delete, null, tint = Error, modifier = Modifier.size(18.dp))
                    }
                }
            }

            // Expanded detail
            if (expanded) {
                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Slate200)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoRow(Icons.Default.MenuBook, "Prodi", student.major)
                    InfoRow(Icons.Default.Email, "Email", student.email)
                    InfoRow(Icons.Default.Phone, "HP", student.phone)
                    InfoRow(genderIcon, "Gender", student.gender)
                    if (student.gpa.isNotBlank()) {
                        InfoRow(Icons.Default.Grade, "IPK", student.gpa)
                    }
                    InfoRow(Icons.Default.Home, "Alamat", student.address)
                }
            }
        }
    }
}

@Composable
private fun Chip(text: String, color: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(text, fontSize = 11.sp, color = textColor, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, null, tint = Slate600, modifier = Modifier.size(16.dp).padding(top = 2.dp))
        Text("$label:", fontSize = 13.sp, color = Slate600, modifier = Modifier.width(52.dp))
        Text(value, fontSize = 13.sp, color = Slate800, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
    }
}