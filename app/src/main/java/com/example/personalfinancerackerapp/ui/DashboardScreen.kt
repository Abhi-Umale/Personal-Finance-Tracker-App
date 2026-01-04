package com.example.personalfinancerackerapp.ui

import android.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalfinancerackerapp.data.Transaction
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: TransactionViewModel,
    onAddTransactionClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val transactions by viewModel.allTransactions.collectAsState()
    val income by viewModel.income.collectAsState()
    val expense by viewModel.expense.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(imageVector = Icons.Default.Clear,
                            contentDescription = "Logout",
                            tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Cyan.copy(0.4f)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTransactionClick,
                containerColor = Color.Red.copy(0.6f)
                ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaction",
                    modifier = Modifier.size(32.dp))
            }
        },
        bottomBar = {
            BottomAppBar(
               containerColor = Color.Cyan.copy(0.4f)
            ) {
                Row(modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 30.dp)
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(imageVector = Icons.Default.Home,
                        contentDescription = "Search",
                        modifier = Modifier.size(35.dp))
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(35.dp))
                    Icon(imageVector = Icons.Default.FavoriteBorder ,
                        contentDescription = "Search",
                        modifier = Modifier.size(30.dp))
                    Icon(imageVector = Icons.Default.Person,
                        contentDescription = "Search",
                        modifier = Modifier.size(35.dp))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(color = Color.Cyan.copy(0.2f))
        ) {
            // Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Cyan.copy(0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total Balance: $${income - expense}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Income: $$income",
                            fontSize = 18.sp,
                            color = Color.Blue)
                        Text("Expense: $$expense",
                            fontSize = 18.sp,
                            color = Color.Red)
                    }
                }
            }

            // Pie Chart Placeholder (Simple implementation)
            if (income > 0 || expense > 0) {
                Text("Distribution", fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SimplePieChart(income, expense)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Recent Transactions List
            Text("Recent Transactions",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onDeleteClick = { viewModel.deleteTransaction(transaction) }
                    )
                }
            }
        }
    }
}

@Composable
fun SimplePieChart(income: Double, expense: Double,) {
    val total = income + expense
    val incomeAngle = if (total == 0.0) 0f else (360 * (income / total)).toFloat()
    val expenseAngle = if (total == 0.0) 0f else (360 * (expense / total)).toFloat()

    Canvas(modifier = Modifier.size(150.dp)) {
        // Income Arc
        drawArc(
            color = Color.Blue.copy(0.5f),
            startAngle = -90f,
            sweepAngle = incomeAngle,
            useCenter = true
        )
        // Expense Arc
        drawArc(
            color = Color.Red,
            startAngle = -90f + incomeAngle,
            sweepAngle = expenseAngle,
            useCenter = true
        )
    }
}

@Composable
fun TransactionItem(transaction: Transaction, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Cyan.copy(0.3f))
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = transaction.category, fontSize = 22.sp,fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(transaction.date)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${if (transaction.type == "INCOME") "+" else "-"} $${transaction.amount}",
                    color = if (transaction.type == "INCOME") Color.Blue else Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(30.dp))
                }
            }
        }
    }
}
