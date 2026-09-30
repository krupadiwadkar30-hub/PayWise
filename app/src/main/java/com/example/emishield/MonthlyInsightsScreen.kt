package com.example.emishield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MonthlyInsightsScreen(
    monthlyInsights: List<MonthlyInsight>,
    expenses: List<Expense>,
    languageCode: String
) {

    // ============================================================
    // CURRENT MONTH
    // ============================================================

    val currentMonthName =
        SimpleDateFormat(
            "MMMM yyyy",
            Locale.getDefault()
        ).format(Date())

    // ============================================================
    // CURRENT MONTH TOTAL
    // ============================================================

    val currentTotal =
        expenses.sumOf {
            it.amount
        }

    // ============================================================
    // CURRENT MONTH CATEGORY TOTALS
    // ============================================================

    val currentCategoryTotals =
        expenseCategories.associateWith { category ->

            expenses
                .filter {
                    it.category == category
                }
                .sumOf {
                    it.amount
                }
        }

    // ============================================================
    // CURRENT MONTH INSIGHT
    // ============================================================

    val currentMonthInsight =
        MonthlyInsight(
            month = currentMonthName,
            totalSpent = currentTotal,
            categoryTotals = currentCategoryTotals
        )

    // ============================================================
    // DISPLAY LIST
    // ============================================================

    val displayInsights =
        monthlyInsights
            .filter {
                it.month != currentMonthName
            } +
                currentMonthInsight

    // ============================================================
    // PASTEL COLORS
    // ============================================================

    val pastelColors = listOf(
        androidx.compose.ui.graphics.Color(0xFFE3F2FD), // January - Ice Blue
        androidx.compose.ui.graphics.Color(0xFFE0F2F1), // February - Cool Mint
        androidx.compose.ui.graphics.Color(0xFFE8F4F8), // March - Powder Blue
        androidx.compose.ui.graphics.Color(0xFFE0F7FA), // April - Soft Cyan
        androidx.compose.ui.graphics.Color(0xFFE1F5FE), // May - Aqua Mist
        androidx.compose.ui.graphics.Color(0xFFE8F5F2), // June - Pale Teal
        androidx.compose.ui.graphics.Color(0xFFE8EAF6), // July - Soft Blue
        androidx.compose.ui.graphics.Color(0xFFE7F0FA), // August - Blue Mist
        androidx.compose.ui.graphics.Color(0xFFEDE7F6), // September - Cool Lavender
        androidx.compose.ui.graphics.Color(0xFFE6F0F9), // October - Cool Sky Blue
        androidx.compose.ui.graphics.Color(0xFFECEFF1), // November - Blue Grey
        androidx.compose.ui.graphics.Color(0xFFE0F4F8)  // December - Frost Blue
    )

    // ============================================================
    // SCREEN
    // ============================================================

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // ========================================================
        // HEADER
        // ========================================================

        item {

            Text(
                text = tr(
                    "Monthly Insights",
                    languageCode
                ),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Text(
                text =
                    "Review your previous months and understand your spending patterns.",
                color = GreyText,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // ========================================================
        // MONTHLY INSIGHTS
        // ========================================================

        if (displayInsights.isEmpty()) {

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LightBlue
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = tr(
                                "No Monthly Insights",
                                languageCode
                            ),
                            color = GreyText
                        )
                    }
                }
            }

        } else {

            displayInsights
                .asReversed()
                .forEachIndexed { index, insight ->

                    val cardColor =
                        pastelColors[
                            index % pastelColors.size
                        ]

                    item {

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = cardColor
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(18.dp)
                            ) {

                                // ==================================
                                // MONTH
                                // ==================================

                                Text(
                                    text = insight.month,
                                    fontSize = 21.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBlue
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                // ==================================
                                // TOTAL SPENT
                                // ==================================

                                Text(
                                    text =
                                        "${tr(
                                            "total_spent",
                                            languageCode
                                        )}: " +
                                                formatPayWiseMoney(
                                                    insight.totalSpent
                                                ),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkText
                                )

                                Spacer(
                                    modifier = Modifier.height(14.dp)
                                )

                                // ==================================
                                // CATEGORY BREAKDOWN
                                // ==================================

                                Text(
                                    text = tr(
                                        "category_breakdown",
                                        languageCode
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBlue
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                expenseCategories.forEach { category ->

                                    val amount =
                                        insight.categoryTotals[
                                            category
                                        ] ?: 0.0

                                    if (amount > 0.0) {

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    vertical = 4.dp
                                                ),
                                            horizontalArrangement =
                                                Arrangement.SpaceBetween
                                        ) {

                                            Text(
                                                text = tr(
                                                    expenseCategoryKey(
                                                        category
                                                    ),
                                                    languageCode
                                                ),
                                                color = DarkText
                                            )

                                            Text(
                                                text =
                                                    formatPayWiseMoney(
                                                        amount
                                                    ),
                                                fontWeight =
                                                    FontWeight.SemiBold,
                                                color = DarkText
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}