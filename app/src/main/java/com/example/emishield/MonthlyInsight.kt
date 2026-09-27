package com.example.emishield

data class MonthlyInsight(
    val month: String,
    val totalSpent: Double,
    val categoryTotals: Map<String, Double>
)