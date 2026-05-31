package com.studentregist.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val nrp: String,
    val email: String,
    val phone: String,
    val gender: String,        // "Laki-laki" or "Perempuan"
    val faculty: String,
    val major: String,
    val year: String,          // angkatan
    val address: String,
    val gpa: String            // IPK
)