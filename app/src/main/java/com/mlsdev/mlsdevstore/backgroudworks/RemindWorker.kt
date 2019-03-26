package com.mlsdev.mlsdevstore.backgroudworks

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import io.reactivex.Single

class RemindWorker(context: Context, workerParams: WorkerParameters) : RxWorker(context, workerParams) {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME).build()
    }

    private val channelId = "reminder_channel_id"

    override fun createWork(): Single<Result> {
        return Single.fromCallable { sendNotification() }
                .map { Result.success() }
                .onErrorReturn { Result.failure() }
    }

    private fun sendNotification() {
        createNotificationChannel()
        val notificationId = 2019
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.ic_mlsdev_logo)
                .setContentTitle("Test title")
                .setContentText("Test notification text")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Channel Name"
            val channelDescription = "Channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = channelDescription
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}