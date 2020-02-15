package com.atilsamancioglu.kotlinadvanced.Util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.atilsamancioglu.kotlinadvanced.R
import com.atilsamancioglu.kotlinadvanced.view.MainActivity

class NotificationsHelper(val context: Context) {

    private val CHANNEL_ID = "Dog Channel"
    private val NOTIFICATION_ID = 12

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification() {
        createNotificationChannel()
        val intent = Intent(context,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }

        val pendingIntent = PendingIntent.getActivity(context, 0,intent,0) //it manages what happens when user clicks on an icon
        val icon = BitmapFactory.decodeResource(context.resources,R.drawable.dogicon)

        val notification = NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.dogicon)
            .setLargeIcon(icon)
            .setContentTitle("Dogs Here!")
            .setContentText("Dogs Are Here, Ready")
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID,notification)
    }


}