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

    // ============================================================
    // USER-SPECIFIC KEY
    // ============================================================

    private fun userKey(
        baseKey: String,
        userId: Int
    ): String {
        return "${baseKey}_user_$userId"
    }

    // ============================================================
    // CURRENT MONTH
    // ============================================================

    fun getCurrentMonth(
        context: Context,
        userId: Int
    ): String {

        if (userId == 0) {
            return ""
        }

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        return preferences.getString(
            userKey(CURRENT_MONTH_KEY, userId),
            ""
        ) ?: ""
    }

    fun saveCurrentMonth(
        context: Context,
        userId: Int,
        month: String
    ) {

        if (userId == 0) return

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                userKey(CURRENT_MONTH_KEY, userId),
                month
            )
            .apply()
    }

    // ============================================================
    // EXPENSES
    // ============================================================

    fun saveExpenses(
        context: Context,
        userId: Int,
        expenses: List<Expense>
    ) {

        if (userId == 0) return

        val json = gson.toJson(expenses)

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                userKey(EXPENSES_KEY, userId),
                json
            )
            .apply()
    }

    fun loadExpenses(
        context: Context,
        userId: Int
    ): List<Expense> {

        if (userId == 0) {
            return emptyList()
        }

        val json =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .getString(
                    userKey(EXPENSES_KEY, userId),
                    null
                )

        if (json.isNullOrEmpty()) {
            return emptyList()
        }

        return try {

            val type =
                object :
                    TypeToken<List<Expense>>() {}.type

            gson.fromJson<List<Expense>>(
                json,
                type
            ) ?: emptyList()

        } catch (e: Exception) {

            emptyList()
        }
    }

    // ============================================================
    // SPENDING LIMITS
    // ============================================================

    fun saveSpendingLimits(
        context: Context,
        userId: Int,
        limits: Map<String, Double>
    ) {

        if (userId == 0) return

        val json = gson.toJson(limits)

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                userKey(LIMITS_KEY, userId),
                json
            )
            .apply()
    }

    fun loadSpendingLimits(
        context: Context,
        userId: Int
    ): Map<String, Double> {

        if (userId == 0) {
            return emptyMap()
        }

        val json =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .getString(
                    userKey(LIMITS_KEY, userId),
                    null
                )

        if (json.isNullOrEmpty()) {
            return emptyMap()
        }

        return try {

            val type =
                object :
                    TypeToken<Map<String, Double>>() {}.type

            gson.fromJson<Map<String, Double>>(
                json,
                type
            ) ?: emptyMap()

        } catch (e: Exception) {

            emptyMap()
        }
    }

    // ============================================================
    // MONTHLY INSIGHTS
    // ============================================================

    fun saveMonthlyInsights(
        context: Context,
        userId: Int,
        insights: List<MonthlyInsight>
    ) {

        if (userId == 0) return

        val json = gson.toJson(insights)

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                userKey(INSIGHTS_KEY, userId),
                json
            )
            .apply()
    }

    fun loadMonthlyInsights(
        context: Context,
        userId: Int
    ): List<MonthlyInsight> {

        if (userId == 0) {
            return emptyList()
        }

        val json =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
                .getString(
                    userKey(INSIGHTS_KEY, userId),
                    null
                )

        if (json.isNullOrEmpty()) {
            return emptyList()
        }

        return try {

            val type =
                object :
                    TypeToken<List<MonthlyInsight>>() {}.type

            gson.fromJson<List<MonthlyInsight>>(
                json,
                type
            ) ?: emptyList()

        } catch (e: Exception) {

            emptyList()
        }
    }

    // ============================================================
    // CLEAR CURRENT MONTH
    // ============================================================

    fun clearCurrentMonthData(
        context: Context,
        userId: Int
    ) {

        if (userId == 0) return

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .remove(
                userKey(EXPENSES_KEY, userId)
            )
            .remove(
                userKey(LIMITS_KEY, userId)
            )
            .apply()
    }
}