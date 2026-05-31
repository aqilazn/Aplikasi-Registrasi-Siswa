package com.studentregist.app.ui.students

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.studentregist.app.data.entity.Student
import com.studentregist.app.ui.*
import com.studentregist.app.ui.components.*
import com.studentregist.app.viewmodel.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsScreen(
    viewModel: StudentViewModel,
    onLogout: () -> Unit
) {
    val students by viewModel.students.collectAsStateWithLifecycle()
    val totalCount by viewModel.totalCount.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    var showAddDialog by remember { mutableStateOf(false) }
    var editingStudent by remember { mutableStateOf<Student?>(null) }
    var deletingStudent by remember { mutableStateOf<Student?>(null) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val genderStats = remember(students) {
        val male = students.count { it.gender == "Laki-laki" }
        val female = students.count { it.gender == "Perempuan" }
        Pair(male, female)
    }

    Scaffold(
        containerColor = Slate50,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                icon = { Icon(Icons.Default.PersonAdd, null) },
                text = { Text("Tambah", fontWeight = FontWeight.SemiBold) },
                containerColor = Indigo600,
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Header banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Indigo700, Color(0xFF6366F1)),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, 0f)
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("SiMahasiswa", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color.White)
                                Text("Data Registrasi Mahasiswa", fontSize = 13.sp, color = Color.White.copy(0.75f))
                            }
                            IconButton(
                                onClick = { showLogoutDialog = true },
                                modifier = Modifier
                                    .background(Color.White.copy(0.15f), CircleShape)
                            ) {
                                Icon(Icons.Default.Logout, null, tint = Color.White)
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        // Stat cards row
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            StatCard(
                                label = "Total",
                                value = totalCount.toString(),
                                icon = Icons.Default.People,
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                label = "Laki-laki",
                                value = genderStats.first.toString(),
                                icon = Icons.Default.Male,
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                label = "Perempuan",
                                value = genderStats.second.toString(),
                                icon = Icons.Default.Female,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Search bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::onSearchQueryChange,
                    placeholder = { Text("Cari nama atau NIM...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    trailingIcon = {
                        AnimatedVisibility(visible = searchQuery.isNotBlank()) {
                            IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                Icon(Icons.Default.Clear, null)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Indigo600,
                        unfocusedBorderColor = Slate200,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )
            }

            // Results info
            item {
                AnimatedVisibility(visible = searchQuery.isNotBlank()) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.FilterList, null, tint = Indigo600, modifier = Modifier.size(16.dp))
                        Text(
                            "Menampilkan ${students.size} hasil untuk \"$searchQuery\"",
                            fontSize = 13.sp,
                            color = Indigo600
                        )
                    }
                }
            }

            // Student list
            if (students.isEmpty()) {
                item {
                    EmptyState(isSearching = searchQuery.isNotBlank())
                }
            } else {
                items(students, key = { it.id }) { student ->
                    StudentCard(
                        student = student,
                        onEdit = { editingStudent = student },
                        onDelete = { deletingStudent = student },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }

    // Dialogs
    if (showAddDialog) {
        StudentFormDialog(
            onDismiss = { showAddDialog = false },
            onSave = { viewModel.addStudent(it) },
            onValidate = { n, ni, e, p, f, m, y, a, g -> viewModel.validateStudent(n, ni, e, p, f, m, y, a, g) }
        )
    }

    editingStudent?.let { s ->
        StudentFormDialog(
            student = s,
            onDismiss = { editingStudent = null },
            onSave = { viewModel.updateStudent(it) },
            onValidate = { n, ni, e, p, f, m, y, a, g -> viewModel.validateStudent(n, ni, e, p, f, m, y, a, g) }
        )
    }

    deletingStudent?.let { s ->
        DeleteConfirmDialog(
            student = s,
            onDismiss = { deletingStudent = null },
            onConfirm = { viewModel.deleteStudent(s) }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            icon = { Icon(Icons.Default.Logout, null, tint = Indigo600) },
            title = { Text("Keluar?", fontWeight = FontWeight.Bold) },
            text = { Text("Apakah Anda yakin ingin keluar dari sesi admin?") },
            dismissButton = {
                OutlinedButton(onClick = { showLogoutDialog = false }, shape = RoundedCornerShape(10.dp)) {
                    Text("Batal")
                }
            },
            confirmButton = {
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo600),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Keluar")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color.White)
            Text(label, fontSize = 11.sp, color = Color.White.copy(0.75f))
        }
    }
}

@Composable
private fun EmptyState(isSearching: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            if (isSearching) Icons.Default.SearchOff else Icons.Default.PeopleOutline,
            null,
            tint = Slate200,
            modifier = Modifier.size(80.dp)
        )
        Text(
            if (isSearching) "Tidak ada hasil pencarian" else "Belum ada data mahasiswa",
            fontWeight = FontWeight.SemiBold,
            color = Slate600,
            fontSize = 16.sp
        )
        Text(
            if (isSearching) "Coba kata kunci lain" else "Tekan tombol + untuk menambah",
            color = Slate600.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
    }
}

// Overloaded to accept modifier
@Composable
fun StudentCard(
    student: com.studentregist.app.data.entity.Student,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        com.studentregist.app.ui.components.StudentCard(student, onEdit, onDelete)
    }
}
