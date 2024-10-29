package rs.symphony.cicak.webshop

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.android.ext.koin.androidContext
import rs.symphony.cicak.webshop.dependencies.NotificationHandler
import rs.symphony.cicak.webshop.di.AppInitializer

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppInitializer.initialize(notificationHandler = NotificationHandler(applicationContext)) {
            androidContext(this@MyApplication)

            NotifierManager.initialize(
                NotificationPlatformConfiguration.Android(
                    notificationIconResId = R.drawable.ic_launcher_foreground,
                    showPushNotification = true,
                    notificationChannelData = NotificationPlatformConfiguration.Android.NotificationChannelData(
                        id = "CHANNEL_ID_GENERAL",
                        name = "General"
                    )
                )
            )
        }
    }
}