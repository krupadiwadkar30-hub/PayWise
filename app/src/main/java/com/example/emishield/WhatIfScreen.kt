package com.example.emishield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WhatIfScreen(
    languageCode: String
) {
    var salary by remember { mutableStateOf("") }
    var emi by remember { mutableStateOf("") }
    var extraEmi by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(false) }

    val salaryValue = salary.toDoubleOrNull() ?: 0.0
    val emiValue = emi.toDoubleOrNull() ?: 0.0
    val extraEmiValue = extraEmi.toDoubleOrNull() ?: 0.0

    val currentRemaining = salaryValue - emiValue
    val newTotalEmi = emiValue + extraEmiValue
    val newRemaining = salaryValue - newTotalEmi

    val currentEmiRatio =
        if (salaryValue > 0) (emiValue / salaryValue) * 100 else 0.0

    val newEmiRatio =
        if (salaryValue > 0) (newTotalEmi / salaryValue) * 100 else 0.0

    val impactMessage = when {
        salaryValue <= 0 -> {
            "Enter your salary to understand how this decision could affect your monthly finances."
        }

        extraEmiValue <= 0 -> {
            "No additional EMI has been added. Try entering an amount to see how it could affect your monthly budget."
        }

        newRemaining < 0 -> {
            "This decision would make your monthly EMI commitments higher than your salary. Consider avoiding or reducing the additional EMI so your income can continue to cover your regular needs."
        }

        newEmiRatio > 50 -> {
            "This decision would put more than half of your salary toward EMIs. That could leave less room for everyday expenses, savings, and unexpected needs. Consider reducing the additional EMI if possible."
        }

        newEmiRatio > 40 -> {
            "Your EMI burden would become quite high after this decision. Before committing, make sure your regular expenses and savings goals can still be managed comfortably."
        }

        newEmiRatio > 30 -> {
            "This decision would noticeably increase your EMI burden. Take a moment to check your monthly expenses before committing. If the purchase is not urgent, saving the same amount each month could strengthen your financial position."
        }

        else -> {
            "This decision would increase your EMI burden, but your remaining income is still positive. Make sure your regular expenses are covered and consider keeping some money aside for savings."
        }
    }

    val smartTip = when {
        extraEmiValue <= 0 -> {
            "Small financial decisions can make a big difference over time. Try a realistic amount to see your potential impact."
        }

        newRemaining < 0 -> {
            "Protect your monthly cash flow first. A decision that keeps your expenses within your income gives you more financial flexibility."
        }

        newEmiRatio > 50 -> {
            "If this EMI is not essential, consider postponing it and directing that amount toward savings instead."
        }

        newEmiRatio > 40 -> {
            "Before taking the EMI, compare it with your essential monthly expenses and savings target."
        }

        newEmiRatio > 30 -> {
            "If you can save ₹${"%.0f".format(extraEmiValue)} instead of taking this EMI, you could build a useful financial cushion over time."
        }

        else -> {
            "If this EMI is affordable and necessary, continue monitoring your expenses so you can maintain healthy savings."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            text = "What If?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBlue
        )

        Text(
            text = "See how a financial decision could affect your monthly budget.",
            color = GreyText
        )

        OutlinedTextField(
            value = salary,
            onValueChange = {
                salary = it
                result = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Monthly Salary")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = emi,
            onValueChange = {
                emi = it
                result = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Current Total EMI")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = extraEmi,
            onValueChange = {
                extraEmi = it
                result = false
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("What If I Take?")
            },
            singleLine = true
        )

        Button(
            onClick = {
                result = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            )
        ) {
            Text(
                text = "See the Impact"
            )
        }

        if (result) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LightBlue
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Financial Impact",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )

                    Text(
                        text = "Current Situation",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Current EMI")

                        Text(
                            text = "₹${"%.0f".format(emiValue)}",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Current EMI Ratio")

                        Text(
                            text = "${"%.1f".format(currentEmiRatio)}%",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Remaining Salary")

                        Text(
                            text = "₹${"%.0f".format(currentRemaining)}",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "After This Decision",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("New Total EMI")

                        Text(
                            text = "₹${"%.0f".format(newTotalEmi)}",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("New EMI Ratio")

                        Text(
                            text = "${"%.1f".format(newEmiRatio)}%",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("New Remaining Salary")

                        Text(
                            text = "₹${"%.0f".format(newRemaining)}",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Impact",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )

                    Text(
                        text = impactMessage,
                        color = DarkText
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Smart Tip",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )

                    Text(
                        text = smartTip,
                        color = DarkText
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}