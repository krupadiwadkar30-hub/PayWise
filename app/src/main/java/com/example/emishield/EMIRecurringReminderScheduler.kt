package com.example.emishield

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object EMIRecurringReminderScheduler {

    private const val REMINDER_INTERVAL_HOURS = 3L

    // ============================================================
    // REAL RECURRING EMI REMINDER
    // ============================================================

    fun scheduleRecurringReminder(
        context: Context,
        emi: EMIData,
        reminderDays: Int
    ) {

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Android 12+ exact alarm permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        val startTime = calculateStartTime(
            dueDate = emi.dueDate,
            reminderDays = reminderDays
        ) ?: return

        val now = System.currentTimeMillis()

        // If the calculated reminder time has already passed,
        // start the recurring reminder 3 hours from now.
        val firstReminderTime =
            if (startTime > now) {
                startTime
            } else {
                now + (REMINDER_INTERVAL_HOURS * 60 * 60 * 1000L)
            }

        val intent =
            Intent(
                context,
                EMIReminderReceiver::class.java
            ).apply {
                putExtra("emi_id", emi.id)
                putExtra("lender", emi.lender)
                putExtra("amount", emi.amount)
                putExtra("due_date", emi.dueDate)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                emi.id + 10000,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            firstReminderTime,
            pendingIntent
        )
    }

    // ============================================================
    // DEMO NOTIFICATION
    // Fires after 1 minute for the judge demonstration.
    // ============================================================

    fun scheduleDemoReminder(
        context: Context,
        emi: EMIData,
        delayMinutes: Int = 1
    ) {

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Android 12+ exact alarm permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        val reminderTime =
            System.currentTimeMillis() +
                    (delayMinutes * 60 * 1000L)

        val intent =
            Intent(
                context,
                EMIReminderReceiver::class.java
            ).apply {
                putExtra("emi_id", emi.id)
                putExtra("lender", emi.lender)
                putExtra("amount", emi.amount)
                putExtra("due_date", emi.dueDate)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                emi.id + 20000,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            reminderTime,
            pendingIntent
        )
    }

    // ============================================================
    // CALCULATE WHEN THE REAL REMINDER PERIOD STARTS
    // ============================================================

    private fun calculateStartTime(
        dueDate: String,
        reminderDays: Int
    ): Long? {

        val formats = listOf(
            SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ),
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )
        )

        val date =
            formats.firstNotNullOfOrNull { format ->
                try {
                    format.isLenient = false
                    format.parse(dueDate)
                } catch (e: Exception) {
                    null
                }
            } ?: return null

        return Calendar.getInstance().apply {

            time = date

            add(
                Calendar.DAY_OF_YEAR,
                -reminderDays
            )

            set(
                Calendar.HOUR_OF_DAY,
                9
            )

            set(
                Calendar.MINUTE,
                0
            )

            set(
                Calendar.SECOND,
                0
            )

            set(
                Calendar.MILLISECOND,
                0
            )

        }.timeInMillis
    }
}