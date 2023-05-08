import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ha_remote.clientvm.ui.main.PCloud
import com.ha_remote.clientvm.R
import com.ha_remote.clientvm.ui.main.exceptions.PcloudException
import android.content.Intent
import android.app.PendingIntent
import com.ha_remote.clientvm.MainActivity
import androidx.core.app.NotificationManagerCompat
class CleanDataWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        uploadUserData(applicationContext)
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadUserData(context: Context) {
        try {
            var pcloud = PCloud()
            pcloud.RemoveFilesBeforeDate(3)
//            displayNotification("cleaning ended")
            NotificationHandler.createReminderNotification(context,"cleaning ended")
        }
        catch(e: PcloudException)
        {
            try {
                NotificationHandler.createReminderNotification(context, e.message.toString())
            }catch( throwable: Throwable) {

            }
//            displayNotification(e.message.toString())
        }


    }

    @SuppressLint("ServiceCast")
    private fun displayNotification(message: String) {
        val notificationManager =  applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "DemoNotificationChannelId",
                "simplifiedcoding",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            assert(notificationManager != null)
            notificationManager?.createNotificationChannel(channel)
        }
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, "DemoNotificationChannelId")
                .setContentTitle("HA Remote")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher_foreground)

        assert(notificationManager != null)
        notificationManager?.notify(1, builder.build())


    }
}




object NotificationHandler {
    private const val CHANNEL_ID = "transactions_reminder_channel2"

    fun createReminderNotification(context: Context, message: String) {
        //  No back-stack when launched
//        val intent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
//            else PendingIntent.FLAG_UPDATE_CURRENT)

        createNotificationChannel(context) // This won't create a new channel everytime, safe to call

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("HA Remote")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent) // For launching the MainActivity
            .setAutoCancel(true) // Remove notification when tapped
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }

    /**
     * Required on Android O+
     */
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Daily Reminders"
            val descriptionText = "This channel sends daily reminders to add your transactions"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
