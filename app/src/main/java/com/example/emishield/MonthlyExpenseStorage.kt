package com.example.emishield

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class MonthlyStoredData(
    val month: String,
    val expenses: List<Expense>,
    val spendingLimits: Map<String, Double>
)

object MonthlyExpenseStorage {

    private const val PREF_NAME = "paywise_monthly_expenses"
    private const val CURRENT_MONTH_KEY = "current_month"
    private const val EXPENSES_KEY = "current_expenses"
    private const val LIMITS_KEY = "spending_limits"
    private const val INSIGHTS_KEY = "monthly_insights"

    private val gson = Gson()

    fun getCurrentMonth(context: Context): String {
        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        return preferences.getString(
            CURRENT_MONTH_KEY,
            ""
        ) ?: ""
    }

    fun saveCurrentMonth(
        context: Context,
        month: String
    ) {
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(CURRENT_MONTH_KEY, month)
            .apply()
    }

    fun saveExpenses(
        context: Context,
        expenses: List<Expense>
    ) {
        val json = gson.toJson(expenses)

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(EXPENSES_KEY, json)
            .apply()
    }

    fun loadExpenses(
        context: Context
    ): List<Expense> {

        val json =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .getString(
                    EXPENSES_KEY,
                    null
                )

        if (json.isNullOrEmpty()) {
            return emptyList()
        }

        return try {
            val type =
                object : TypeToken<List<Expense>>() {}.type

            gson.fromJson(
                json,
                type
            ) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveSpendingLimits(
        context: Context,
        limits: Map<String, Double>
    ) {
        val json = gson.toJson(limits)

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(LIMITS_KEY, json)
            .apply()
    }

    fun loadSpendingLimits(
        context: Context
    ): Map<String, Double> {

        val json =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .getString(
                    LIMITS_KEY,
                    null
                )

        if (json.isNullOrEmpty()) {
            return emptyMap()
        }

        return try {
            val type =
                object :
                    TypeToken<Map<String, Double>>() {}.type

            gson.fromJson(
                json,
                type
            ) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }

    fun saveMonthlyInsights(
        context: Context,
        insights: List<MonthlyInsight>
    ) {
        val json = gson.toJson(insights)

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                INSIGHTS_KEY,
                json
            )
            .apply()
    }

    fun loadMonthlyInsights(
        context: Context
    ): List<MonthlyInsight> {

        val json =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .getString(
                    INSIGHTS_KEY,
                    null
                )

        if (json.isNullOrEmpty()) {
            return emptyList()
        }

        return try {
            val type =
                object :
                    TypeToken<List<MonthlyInsight>>() {}.type

            gson.fromJson(
                json,
                type
            ) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun clearCurrentMonthData(
        context: Context
    ) {
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .remove(EXPENSES_KEY)
            .remove(LIMITS_KEY)
            .apply()
    }
}