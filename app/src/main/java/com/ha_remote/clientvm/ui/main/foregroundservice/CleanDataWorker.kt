import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ha_remote.clientvm.ui.main.PCloud

class CleanDataWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        uploadUserData()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadUserData() {
        var pcloud = PCloud()
        pcloud.RemoveFilesBeforeDate(3)
    }
}