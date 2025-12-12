package com.example.personalfinancerackerapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.personalfinancerackerapp.data.User
import com.example.personalfinancerackerapp.data.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userDao: UserDao) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthResult?>(null)
    val loginState = _loginState.asStateFlow()

    private val _signupState = MutableStateFlow<AuthResult?>(null)
    val signupState = _signupState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (username.isBlank() || password.isBlank()) {
                _loginState.value = AuthResult.Error("Please enter username and password")
                return@launch
            }
            val user = userDao.getUserByUsernameAndPassword(username, password)
            if (user != null) {
                _loginState.value = AuthResult.Success
            } else {
                _loginState.value = AuthResult.Error("Invalid credentials")
            }
        }
    }

    fun signup(username: String, password: String) {
        viewModelScope.launch {
            if (username.isBlank() || password.isBlank()) {
                _signupState.value = AuthResult.Error("Please enter username and password")
                return@launch
            }
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                _signupState.value = AuthResult.Error("Username already exists")
            } else {
                userDao.insertUser(User(username = username, password = password))
                _signupState.value = AuthResult.Success
            }
        }
    }
    
    fun logout() {
        _loginState.value = null
        _signupState.value = null
    }

    fun resetState() {
        _loginState.value = null
        _signupState.value = null
    }
}

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
