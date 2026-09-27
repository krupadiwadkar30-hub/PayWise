package com.example.emishield

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.app.AlarmManager
import android.os.Build

class EMIReminderReceiver : BroadcastReceiver() {

    companion object {
        private const val CHANNEL_ID = "paywise_emi_reminders"
        private const val RECURRING_INTERVAL_HOURS = 3L
    }

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        createNotificationChannel(context)

        if (
            android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.TIRAMISU
        ) {
            if (
                context.checkSelfPermission(
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val lender =
            intent.getStringExtra("lender")
                ?: "EMI"

        val amount =
            intent.getDoubleExtra(
                "amount",
                0.0
            )

        val dueDate =
            intent.getStringExtra("due_date")
                ?: ""

        // Stop recurring reminders after the EMI due date
        try {
            val formats = listOf(
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()),
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            )

            val dueDateObject = formats.firstNotNullOfOrNull { format ->
                try {
                    format.isLenient = false
                    format.parse(dueDate)
                } catch (e: Exception) {
                    null
                }
            }

            if (dueDateObject != null) {
                val dueCalendar = Calendar.getInstance().apply {
                    time = dueDateObject

                    // Allow reminders throughout the due date.
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }

                if (System.currentTimeMillis() > dueCalendar.timeInMillis) {
                    return
                }
            }
        } catch (e: Exception) {
            // If the date cannot be parsed, continue normally.
        }

        val openAppIntent =
            Intent(
                context,
                MainActivity::class.java
            )

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                openAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(
                    android.R.drawable.ic_dialog_info
                )
                .setContentTitle(
                    "PayWise EMI Reminder"
                )
                .setContentText(
                    "$lender EMI of ₹${"%.0f".format(amount)} is due on $dueDate."
                )
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            "Your $lender EMI of " +
                                    "₹${"%.0f".format(amount)} " +
                                    "is due on $dueDate. " +
                                    "Please make sure you are prepared for the payment."
                        )
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                intent.getIntExtra("emi_id", 0),
                notification
            )

        // Schedule the next recurring reminder after 3 hours
        val nextReminderTime =
            System.currentTimeMillis() +
                    (RECURRING_INTERVAL_HOURS * 60 * 60 * 1000)

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as android.app.AlarmManager

        val nextIntent =
            Intent(
                context,
                EMIReminderReceiver::class.java
            ).apply {
                putExtra(
                    "emi_id",
                    intent.getIntExtra("emi_id", 0)
                )
                putExtra(
                    "lender",
                    lender
                )
                putExtra(
                    "amount",
                    amount
                )
                putExtra(
                    "due_date",
                    dueDate
                )
            }

        val nextPendingIntent =
            PendingIntent.getBroadcast(
                context,
                intent.getIntExtra("emi_id", 0) + 10000,
                nextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextReminderTime,
            nextPendingIntent
        )
    }

    private fun createNotificationChannel(
        context: Context
    ) {

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                "PayWise EMI Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description =
                    "Reminders for upcoming EMI payments"
            }

        val manager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        manager.createNotificationChannel(channel)
    }
}