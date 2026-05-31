package com.studentregist.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.studentregist.app.data.database.AppDatabase
import com.studentregist.app.data.entity.Student
import com.studentregist.app.repository.StudentRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val students: StateFlow<List<Student>>
    val totalCount: StateFlow<Int>

    init {
        val dao = AppDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(dao)

        students = _searchQuery
            .flatMapLatest { query -> repository.searchStudents(query) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        totalCount = repository.getTotalCount()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun addStudent(student: Student) {
        viewModelScope.launch { repository.insertStudent(student) }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch { repository.updateStudent(student) }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch { repository.deleteStudent(student) }
    }

    // Validate student fields
    fun validateStudent(
        name: String, nrp: String, email: String, phone: String,
        faculty: String, major: String, year: String, address: String, gpa: String
    ): String? {
        if (name.isBlank()) return "Nama tidak boleh kosong"
        if (nrp.isBlank()) return "NRP tidak boleh kosong"
        if (nrp.length < 10) return "NRP minimal 10 digit"
        if (!nrp.all { it.isDigit() }) return "NRP harus berupa angka"
        if (email.isBlank()) return "Email tidak boleh kosong"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Format email tidak valid"
        if (phone.isBlank()) return "Nomor HP tidak boleh kosong"
        if (faculty.isBlank()) return "Fakultas harus dipilih"
        if (major.isBlank()) return "Program Studi harus dipilih"
        if (year.isBlank()) return "Tahun angkatan harus dipilih"
        if (address.isBlank()) return "Alamat tidak boleh kosong"
        if (gpa.isNotBlank()) {
            val gpaVal = gpa.toDoubleOrNull() ?: return "IPK harus berupa angka"
            if (gpaVal < 0.0 || gpaVal > 4.0) return "IPK harus antara 0.00 - 4.00"
        }
        return null
    }
}