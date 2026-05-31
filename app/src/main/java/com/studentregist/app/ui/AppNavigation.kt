package com.studentregist.app.ui

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.studentregist.app.ui.login.LoginScreen
import com.studentregist.app.ui.students.StudentsScreen
import com.studentregist.app.viewmodel.StudentViewModel

@Composable
fun AppNavigation() {
    var isLoggedIn by remember { mutableStateOf(false) }
    val viewModel: StudentViewModel = viewModel()

    if (isLoggedIn) {
        StudentsScreen(
            viewModel = viewModel,
            onLogout = { isLoggedIn = false }
        )
    } else {
        LoginScreen(
            onLoginSuccess = { isLoggedIn = true }
        )
    }
}
