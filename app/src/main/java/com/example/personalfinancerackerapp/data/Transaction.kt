package com.example.personalfinancerackerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val category: String,
    val type: String, // "INCOME" or "EXPENSE"
    val date: Long = System.currentTimeMillis()
)
