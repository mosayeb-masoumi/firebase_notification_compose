package com.example.firebasenotificationcompose

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.firebasenotificationcompose.destinations.Destination
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage



const val channelId = "notification_channel"
const val channelName = "com.example.firebasenotificationcompose"


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.notification != null) {
            generateNotification(
                remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!
            )
        }
    }

    private fun generateNotification(title: String, message: String) {

        /****************************** for XML **************************/
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            intent,
//            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
//        )



        /****************************** for Compose **************************/
        val intent = Intent(this, MainActivity::class.java)
        intent.action = "com.example.ACTION_OPEN_SCREEN"
        intent.putExtra("title", title)
        intent.putExtra("message", message)
        intent.putExtra("notif_destination", Destination.DetailScreen.passOptionalTitleAndMessage(title,message))

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )






//        showNormalNotification(pendingIntent , title , message)
//        showBigDrawablePictureNotification(pendingIntent , title , message)
        showBigNetworkPictureNotification(pendingIntent, title, message)

    }


    private fun showBigNetworkPictureNotification(
        pendingIntent: PendingIntent?,
        title: String,
        message: String
    ) {
        val imageUrl = "https://images.freeimages.com/images/large-previews/c56/scn-prk-012-jpg-1364337.jpg"

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                    return true
                }
                override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {

                    val bigPictureStyle = NotificationCompat.BigPictureStyle()
                        .bigPicture(resource)
                        .bigLargeIcon(resource) // Optionally set a large icon

// Build the notification
                    var builder: NotificationCompat.Builder =
                        NotificationCompat.Builder(applicationContext, channelId)
                            .setSmallIcon(R.drawable.flower)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setStyle(bigPictureStyle)
                            .setAutoCancel(true)
                            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                            .setOnlyAlertOnce(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(pendingIntent) // Set a pending intent for when the notification is clicked

                    builder = builder.setContent(getBigPictureNetworkRemoteView(title, message , resource))
                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val notificationChannel = NotificationChannel(
                            channelId,
                            channelName,
                            NotificationManager.IMPORTANCE_HIGH
                        )
                        notificationManager.createNotificationChannel(notificationChannel)
                    }

                    val id = System.currentTimeMillis().toInt()
                    notificationManager.notify(id, builder.build())

                    return true
                }
            }).submit().get()


    }


    private fun showBigDrawablePictureNotification(
        pendingIntent: PendingIntent?,
        title: String,
        message: String
    ) {
        // Create a big picture style
        val bigPicture = BitmapFactory.decodeResource(resources, R.drawable.flower)
        val bigIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(bigPicture)
            .bigLargeIcon(bigIcon) // Optionally set a large icon

// Build the notification
        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.flower)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(bigPictureStyle)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent) // Set a pending intent for when the notification is clicked

        builder = builder.setContent(getBigPictureDrawableRemoteView(title, message))
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val id = System.currentTimeMillis().toInt()
        notificationManager.notify(id, builder.build())
    }


    private fun showNormalNotification(
        pendingIntent: PendingIntent?,
        title: String,
        message: String
    ) {
        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.flower)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }


        val id = System.currentTimeMillis().toInt()
        notificationManager.notify(id, builder.build())
    }


    private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView =
            RemoteViews("com.example.firebasenotificationcompose", R.layout.notification)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.img, R.drawable.flower)
        return remoteView
    }


    private fun getBigPictureDrawableRemoteView(title: String, message: String): RemoteViews {
        val remoteView =
            RemoteViews("com.example.firebasenotificationcompose", R.layout.notification_big)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.img, R.drawable.flower)
        remoteView.setImageViewResource(R.id.big_image, R.drawable.flower)
        return remoteView
    }

    private fun getBigPictureNetworkRemoteView(
        title: String,
        message: String,
        bitmap: Bitmap
    ): RemoteViews {
        val remoteView =
            RemoteViews("com.example.firebasenotificationcompose", R.layout.notification_big)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewBitmap(R.id.img, bitmap)
        remoteView.setImageViewBitmap(R.id.big_image, bitmap)
        return remoteView
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val mytoken = token
        var f = mytoken;
    }
}