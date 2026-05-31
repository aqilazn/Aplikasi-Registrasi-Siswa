package com.studentregist.app.repository

import com.studentregist.app.data.dao.StudentDao
import com.studentregist.app.data.entity.Student
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val dao: StudentDao) {

    fun getAllStudents(): Flow<List<Student>> = dao.getAllStudents()

    fun searchStudents(query: String): Flow<List<Student>> =
        if (query.isBlank()) dao.getAllStudents()
        else dao.searchStudents(query)

    fun getTotalCount(): Flow<Int> = dao.getTotalCount()

    suspend fun insertStudent(student: Student) = dao.insertStudent(student)

    suspend fun updateStudent(student: Student) = dao.updateStudent(student)

    suspend fun deleteStudent(student: Student) = dao.deleteStudent(student)
}
