package com.example.personalfinancerackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.personalfinancerackerapp.auth.authviewmodel.AuthViewModel
import com.example.personalfinancerackerapp.data.AppDatabase
import com.example.personalfinancerackerapp.data.Transaction
import com.example.personalfinancerackerapp.ui.*
import com.example.personalfinancerackerapp.ui.signupandlogin.SignupAndLoginScreen
import com.example.personalfinancerackerapp.ui.theme.PersonalFinancerackerAppTheme
import com.example.personalfinancerackerapp.utils.SessionManager
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        setContent {
            PersonalFinancerackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val context = LocalContext.current
                    val userId = SessionManager.getUserId(context)

                    //Navigation
                    val navController = rememberNavController()
                    val authViewModel = remember { AuthViewModel() }
                    val startDestination = if (SessionManager.getUserId(context).toLong() != -1L) {
                        "dashboard" // or Routs.Dashboard
                    } else {
                        "SignupAndLoginScreen"
                    }
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("SignupAndLoginScreen") {
                            SignupAndLoginScreen(viewModel = authViewModel, navController = navController)
                        }
                        composable("dashboard") {
                            // This assumes you want one user ID for the whole session
                            val userId = SessionManager.getUserId(context)
                            val transactionViewModel: TransactionViewModel by viewModels {
                                TransactionViewModelFactory(database.transactionDao(), userId)
                            }
                            var showDialog by remember { mutableStateOf(false) }

                            DashboardScreen(
                                viewModel = transactionViewModel,
                                onAddTransactionClick = { showDialog = true },
                                onLogoutClick = {
                                    SessionManager.clearSession(context) // Clear session on logout
                                    navController.navigate("SignupAndLoginScreen") {
                                        popUpTo("dashboard") { inclusive = true }
                                    }
                                }
                            )

                            if (showDialog) {
                                AddTransactionDialog(
                                    onDismiss = { showDialog = false },
                                    onConfirm = { amount, category, type ->
                                        val transaction = Transaction(
                                            userId = userId,
                                            amount = amount,
                                            category = category,
                                            type = type
                                        )
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
