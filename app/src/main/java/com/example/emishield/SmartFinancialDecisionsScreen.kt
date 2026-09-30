package com.example.emishield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round

@Composable
fun SmartFinancialDecisionsScreen(
    languageCode: String,
    onWhatIfClick: () -> Unit,
    onFinancialHealthTipsClick: () -> Unit,
    expenses: List<Expense>
) {

    // Use the same calculation as Everyday Expenses
    val categoryTotals = expenseCategories.associateWith { category ->
        expenses
            .filter { it.category == category }
            .sumOf { it.amount }
    }

    val suggestions = categoryTotals
        .filter { it.value > 500.0 }
        .map { (category, amount) ->

            val cutPercent = when {
                amount >= 2000.0 -> 0.25
                amount >= 1000.0 -> 0.15
                else -> 0.10
            }

            category to round(amount * cutPercent)
        }
        .filter { it.second >= 50.0 }
        .sortedByDescending { it.second }

    val potentialSavings = suggestions.sumOf { it.second }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        // ============================================================
        // TITLE
        // ============================================================

        Text(
            text = tr(
                "Smart Financial Decisions",
                languageCode
            ),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBlue
        )

        Text(
            text = "Make smarter decisions using your financial data.",
            color = GreyText
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        // ============================================================
        // WHAT CAN I CUT?
        // ============================================================

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
                        "what_should_i_cut",
                        languageCode
                    ),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = tr(
                        "potential_monthly_savings",
                        languageCode
                    ) + ": ${formatPayWiseMoney(potentialSavings)}",
                    color = DarkText,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                if (suggestions.isEmpty()) {

                    Text(
                        text = tr(
                            "no_expenses",
                            languageCode
                        ),
                        color = GreyText
                    )

                } else {

                    suggestions.forEach { suggestion ->

                        val category = suggestion.first
                        val cut = suggestion.second

                        ExpenseSuggestionRow(
                            category = category,
                            spent = categoryTotals[category] ?: 0.0,
                            cut = cut,
                            languageCode = languageCode
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )
                    }
                }
            }
        }

        // ============================================================
        // WHAT IF?
        // ============================================================

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onWhatIfClick()
                },
            colors = CardDefaults.cardColors(
                containerColor = LightBlue
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "What If?",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Explore how small spending changes could affect your savings.",
                    color = DarkText
                )
            }
        }
    }
}
