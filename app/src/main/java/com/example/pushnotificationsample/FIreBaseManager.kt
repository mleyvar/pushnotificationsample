package com.example.pushnotificationsample

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FIreBaseManager : FirebaseMessagingService() {
    val TAG = "Ser-MPL"

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e(TAG, "inicio")
        Log.e(TAG, p0)
        // aqui guardar token en local storage
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.e(TAG, "From: " + p0!!.from)

        Log.e(TAG, "Title: " + p0!!.notification?.title)
        Log.e(TAG, "Tag: " + p0!!.notification?.tag)
        Log.e(TAG, "Body: " + p0.notification?.body!!)
        Log.e(TAG, "image: " + p0.notification?.imageUrl)
        sendNotification(p0)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val mNotificationManager: NotificationManager

        val mBuilder = NotificationCompat.Builder(this, "notify_001")
        val ii = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, ii, 0)

        val imageLarge = MediaStore.Images.Media.getBitmap(this.contentResolver, remoteMessage.notification?.imageUrl)

        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.mipmap.sym_def_app_icon)
        remoteMessage.notification?.imageUrl.apply {
            mBuilder.setLargeIcon(imageLarge)
        }
        mBuilder.color = this.getColor(R.color.holo_purple)
        mBuilder.setContentTitle(remoteMessage.notification?.title)
        mBuilder.setContentText(remoteMessage.notification?.body)
        mBuilder.priority = 2 // MAX_PRIORITY
        mNotificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "Your_channel_id"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
        mNotificationManager.notify(0, mBuilder.build())
    }
}
