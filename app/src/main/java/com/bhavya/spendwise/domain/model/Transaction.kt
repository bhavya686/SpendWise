package com.bhavya.spendwise.domain.model

import java.time.LocalDate

enum class TransactionType {
    INCOME,
    EXPENSE
}

data class Transaction(
    val id: Long = 0L,
    val type: TransactionType,
    val amountPaise: Long,
    val category: String,
    val description: String? = null,
    val date: LocalDate,
    val createdAtMillis: Long = System.currentTimeMillis()
)
