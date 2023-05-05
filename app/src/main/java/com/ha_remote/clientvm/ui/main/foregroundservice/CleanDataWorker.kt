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

class CleanDataWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        uploadUserData()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadUserData() {
        try {
            var pcloud = PCloud()
            pcloud.RemoveFilesBeforeDate(3)
            displayNotification("cleaning ended")
        }
        catch(e: PcloudException)
        {
            displayNotification(e.message.toString())
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

