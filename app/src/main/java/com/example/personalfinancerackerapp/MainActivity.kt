package com.example.personalfinancerackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.personalfinancerackerapp.data.AppDatabase
import com.example.personalfinancerackerapp.ui.*
import com.example.personalfinancerackerapp.ui.theme.PersonalFinancerackerAppTheme

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(database.transactionDao())
    }
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(database.userDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PersonalFinancerackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                viewModel = authViewModel,
                                onLoginSuccess = {
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToSignup = {
                                    navController.navigate("signup")
                                }
                            )
                        }
                        composable("signup") {
                            SignupScreen(
                                viewModel = authViewModel,
                                onSignupSuccess = {
                                    navController.navigate("login") {
                                        popUpTo("signup") { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable("dashboard") {
                            var showDialog by remember { mutableStateOf(false) }
                            
                            DashboardScreen(
                                viewModel = transactionViewModel,
                                onAddTransactionClick = { showDialog = true },
                                onLogoutClick = {
                                    authViewModel.logout()
                                    navController.navigate("login") {
                                        popUpTo("dashboard") { inclusive = true }
                                    }
                                }
                            )

                            if (showDialog) {
                                AddTransactionDialog(
                                    onDismiss = { showDialog = false },
                                    onConfirm = { transaction ->
                                        transactionViewModel.addTransaction(transaction)
                                        showDialog = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
