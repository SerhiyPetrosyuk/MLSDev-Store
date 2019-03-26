package com.mlsdev.mlsdevstore.backgroudworks

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.room.Room
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode
import com.mlsdev.mlsdevstore.presentaion.products.ProductsFragmentArgs
import io.reactivex.Single
import kotlin.random.Random

class RemindWorker(context: Context, workerParams: WorkerParameters) : RxWorker(context, workerParams) {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME).build()
    }

    private val channelId = "reminder_channel_id"

    override fun createWork(): Single<Result> {
        return database.categoriesDao().queryCategoryTreeNode()
                .map { it[Random.nextInt(0, it.size)] }
                .doOnSuccess { sendNotification(it) }
                .map { Result.success() }
                .onErrorReturn { Result.failure() }
    }

    private fun sendNotification(category: CategoryTreeNode) {
        createNotificationChannel()
        val notificationId = 2019

        val argsBuilder = ProductsFragmentArgs.Builder()
        argsBuilder.categoryId = category.category.categoryId
        argsBuilder.categoryName = category.category.categoryName

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
                .setGraph(R.navigation.app_nav)
                .setDestination(R.id.products_fragment)
                .setArguments(argsBuilder.build().toBundle())
                .createPendingIntent()

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.ic_mlsdev_logo)
                .setContentTitle("MLSDev Store")
                .setContentText("Check new products in the ${category.category.categoryName}")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
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