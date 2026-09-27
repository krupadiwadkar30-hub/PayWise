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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun MonthlyInsightsScreen(
    monthlyInsights: List<MonthlyInsight>,
    languageCode: String
) {

    // 12 pastel colours.
    // After December, the colours repeat from January.
    val pastelColors = listOf(
        Color(0xFFFAD2CF), // January - Peach
        Color(0xFFDDD6FE), // February - Lavender
        Color(0xFFCDECCF), // March - Mint
        Color(0xFFCFE8F7), // April - Sky Blue
        Color(0xFFFFF1B8), // May - Soft Yellow
        Color(0xFFC9EAE6), // June - Aqua
        Color(0xFFF8D0C4), // July - Soft Coral
        Color(0xFFC8E6E0), // August - Pale Teal
        Color(0xFFF9D5B4), // September - Soft Orange
        Color(0xFFCDE8C7), // October - Soft Green
        Color(0xFFC9DDF5), // November - Powder Blue
        Color(0xFFF6E6C8)  // December - Cream
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(
                text = tr("Monthly Insights", languageCode),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Text(
                text = "Review your previous months and understand your spending patterns.",
                color = GreyText,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (monthlyInsights.isEmpty()) {

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

            monthlyInsights
                .asReversed()
                .forEachIndexed { index, insight ->

                    // % makes the 12 colours repeat automatically.
                    val cardColor = pastelColors[index % pastelColors.size]

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

                                Text(
                                    text = insight.month,
                                    fontSize = 21.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBlue
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                Text(
                                    text =
                                        "${tr("total_spent", languageCode)}: " +
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
                                        insight.categoryTotals[category]
                                            ?: 0.0

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