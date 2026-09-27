package com.example.emishield

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class PayWiseNotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val channelId = "paywise_reminders"

        val notificationManager =
            applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        // Create notification channel for Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "PayWise Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Financial reminders from PayWise"
            }

            notificationManager.createNotificationChannel(channel)
        }

        // Open PayWise when notification is tapped
        val intent = Intent(
            applicationContext,
            MainActivity::class.java
        ).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            NotificationCompat.Builder(
                applicationContext,
                channelId
            )
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("PayWise Reminder")
                .setContentText(
                    "Don't forget to review your finances today."
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )

        return Result.success()
    }
}


/*
 * Schedule PayWise financial reminder
 *
 * reminderDays can be:
 * 1, 3, 5 or 7 days
 */
fun schedulePayWiseReminder(
    context: Context,
    reminderDays: Int
) {

    val workManager =
        androidx.work.WorkManager.getInstance(context)

    // Remove previous reminder
    workManager.cancelUniqueWork(
        "paywise_financial_reminder"
    )

    val request =
        androidx.work.PeriodicWorkRequestBuilder<
                PayWiseNotificationWorker
                >(
            reminderDays.toLong(),
            java.util.concurrent.TimeUnit.DAYS
        )
            .build()

    workManager.enqueueUniquePeriodicWork(
        "paywise_financial_reminder",
        androidx.work.ExistingPeriodicWorkPolicy.UPDATE,
        request
    )
}