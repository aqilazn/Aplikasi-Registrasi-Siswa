package com.studentregist.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.studentregist.app.AppConstants
import com.studentregist.app.data.entity.Student
import com.studentregist.app.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormDialog(
    student: Student? = null,
    onDismiss: () -> Unit,
    onSave: (Student) -> Unit,
    onValidate: (String, String, String, String, String, String, String, String, String) -> String?
) {
    var name by remember { mutableStateOf(student?.name ?: "") }
    var nrp by remember { mutableStateOf(student?.nrp ?: "") }
    var email by remember { mutableStateOf(student?.email ?: "") }
    var phone by remember { mutableStateOf(student?.phone ?: "") }
    var gender by remember { mutableStateOf(student?.gender ?: "Laki-laki") }
    var faculty by remember { mutableStateOf(student?.faculty ?: "") }
    var major by remember { mutableStateOf(student?.major ?: "") }
    var year by remember { mutableStateOf(student?.year ?: "") }
    var address by remember { mutableStateOf(student?.address ?: "") }
    var gpa by remember { mutableStateOf(student?.gpa ?: "") }
    var errorMessage by remember { mutableStateOf("") }

    var facultyExpanded by remember { mutableStateOf(false) }
    var majorExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }

    val availableMajors = AppConstants.MAJORS[faculty] ?: emptyList()
    val isEditing = student != null

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Column {
                        Text(
                            if (isEditing) "Edit Data Mahasiswa" else "Tambah Mahasiswa Baru",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Slate900
                        )
                        Text(
                            "Isi semua field yang tersedia dengan benar",
                            fontSize = 13.sp,
                            color = Slate600
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(Icons.Default.Close, null, tint = Slate600)
                    }
                }

                Divider(color = Slate200)

                // Form body
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    SectionLabel("Informasi Pribadi")

                    FormField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Nama Lengkap",
                        icon = Icons.Default.Person
                    )

                    FormField(
                        value = nrp,
                        onValueChange = { nrp = it },
                        label = "NRP",
                        icon = Icons.Default.Badge,
                        keyboardType = KeyboardType.Number
                    )

                    // Gender toggle
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Jenis Kelamin", fontSize = 13.sp, color = Slate600, fontWeight = FontWeight.Medium)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            listOf("Laki-laki", "Perempuan").forEach { opt ->
                                val selected = gender == opt
                                FilterChip(
                                    selected = selected,
                                    onClick = { gender = opt },
                                    label = { Text(opt, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal) },
                                    leadingIcon = if (selected) {
                                        { Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp)) }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Indigo600,
                                        selectedLabelColor = Color.White,
                                        selectedLeadingIconColor = Color.White
                                    )
                                )
                            }
                        }
                    }

                    SectionLabel("Kontak")

                    FormField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )

                    FormField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Nomor HP",
                        icon = Icons.Default.Phone,
                        keyboardType = KeyboardType.Phone
                    )

                    SectionLabel("Akademik")

                    // Faculty dropdown
                    ExposedDropdownMenuBox(
                        expanded = facultyExpanded,
                        onExpandedChange = { facultyExpanded = !facultyExpanded }
                    ) {
                        OutlinedTextField(
                            value = faculty,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Fakultas") },
                            leadingIcon = { Icon(Icons.Default.AccountBalance, null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = facultyExpanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Indigo600, focusedLabelColor = Indigo600)
                        )
                        ExposedDropdownMenu(expanded = facultyExpanded, onDismissRequest = { facultyExpanded = false }) {
                            AppConstants.FACULTIES.forEach { fac ->
                                DropdownMenuItem(
                                    text = { Text(fac, fontSize = 14.sp) },
                                    onClick = {
                                        faculty = fac
                                        major = ""
                                        facultyExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Major dropdown
                    ExposedDropdownMenuBox(
                        expanded = majorExpanded,
                        onExpandedChange = { if (faculty.isNotBlank()) majorExpanded = !majorExpanded }
                    ) {
                        OutlinedTextField(
                            value = major,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Program Studi") },
                            leadingIcon = { Icon(Icons.Default.MenuBook, null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = majorExpanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            enabled = faculty.isNotBlank(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Indigo600, focusedLabelColor = Indigo600)
                        )
                        if (availableMajors.isNotEmpty()) {
                            ExposedDropdownMenu(expanded = majorExpanded, onDismissRequest = { majorExpanded = false }) {
                                availableMajors.forEach { mj ->
                                    DropdownMenuItem(
                                        text = { Text(mj, fontSize = 14.sp) },
                                        onClick = { major = mj; majorExpanded = false }
                                    )
                                }
                            }
                        }
                    }

                    // Year dropdown
                    ExposedDropdownMenuBox(
                        expanded = yearExpanded,
                        onExpandedChange = { yearExpanded = !yearExpanded }
                    ) {
                        OutlinedTextField(
                            value = year,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tahun Angkatan") },
                            leadingIcon = { Icon(Icons.Default.CalendarToday, null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearExpanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Indigo600, focusedLabelColor = Indigo600)
                        )
                        ExposedDropdownMenu(expanded = yearExpanded, onDismissRequest = { yearExpanded = false }) {
                            AppConstants.YEARS.forEach { yr ->
                                DropdownMenuItem(
                                    text = { Text(yr, fontSize = 14.sp) },
                                    onClick = { year = yr; yearExpanded = false }
                                )
                            }
                        }
                    }

                    FormField(
                        value = gpa,
                        onValueChange = { gpa = it },
                        label = "IPK (0.00 - 4.00)",
                        icon = Icons.Default.Grade,
                        keyboardType = KeyboardType.Decimal
                    )

                    SectionLabel("Lainnya")

                    FormField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Alamat",
                        icon = Icons.Default.Home,
                        singleLine = false,
                        maxLines = 3
                    )

                    if (errorMessage.isNotBlank()) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Error.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.ErrorOutline, null, tint = Error, modifier = Modifier.size(18.dp))
                                Text(errorMessage, color = Error, fontSize = 13.sp)
                            }
                        }
                    }
                }

                // Action buttons
                Divider(color = Slate200)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Batal")
                    }
                    Button(
                        onClick = {
                            val error = onValidate(name, nrp, email.trim().lowercase(), phone, faculty, major, year, address, gpa)
                            if (error != null) {
                                errorMessage = error
                            } else {
                                onSave(
                                    Student(
                                        id = student?.id ?: 0,
                                        name = name.trim(),
                                        nrp = nrp.trim(),
                                        email = email.trim().lowercase(),
                                        phone = phone.trim(),
                                        gender = gender,
                                        faculty = faculty,
                                        major = major,
                                        year = year,
                                        address = address.trim(),
                                        gpa = gpa.trim()
                                    )
                                )
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo600)
                    ) {
                        Icon(
                            if (isEditing) Icons.Default.Save else Icons.Default.PersonAdd,
                            null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(if (isEditing) "Simpan" else "Daftarkan", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Indigo600,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
private fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, null) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = singleLine,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Indigo600,
            focusedLeadingIconColor = Indigo600,
            focusedLabelColor = Indigo600
        )
    )
}