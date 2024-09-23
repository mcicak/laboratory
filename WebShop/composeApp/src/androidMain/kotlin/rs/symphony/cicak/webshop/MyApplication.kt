package rs.symphony.cicak.webshop

import android.app.Application
import org.koin.android.ext.koin.androidContext
import rs.symphony.cicak.webshop.di.initKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)
        }
    }
}