package com.example.emishield

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object EMIReminderScheduler {

    private const val REMINDER_HOUR = 9
    private const val REMINDER_MINUTE = 0

    fun scheduleAllReminders(
        context: Context,
        emiList: List<EMIData>,
        reminderDays: Int
    ) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        emiList.forEach { emi ->

            val reminderTime = calculateReminderTime(
                dueDate = emi.dueDate,
                reminderDays = reminderDays
            ) ?: return@forEach

            if (reminderTime <= System.currentTimeMillis()) {
                return@forEach
            }

            val intent = Intent(
                context,
                EMIReminderReceiver::class.java
            ).apply {
                putExtra("emi_id", emi.id)
                putExtra("lender", emi.lender)
                putExtra("amount", emi.amount)
                putExtra("due_date", emi.dueDate)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                emi.id,
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
    }

    private fun calculateReminderTime(
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

        val date = formats.firstNotNullOfOrNull { format ->
            try {
                format.isLenient = false
                format.parse(dueDate)
            } catch (
                e: Exception
            ) {
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
                REMINDER_HOUR
            )

            set(
                Calendar.MINUTE,
                REMINDER_MINUTE
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