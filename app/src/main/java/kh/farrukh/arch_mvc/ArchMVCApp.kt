package kh.farrukh.arch_mvc

import android.app.Application
import kh.farrukh.arch_mvc.di.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 *Created by farrukh_kh on 4/8/22 4:10 PM
 *kh.farrukh.arch_mvc
 **/
class ArchMVCApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            //There is bug in Koin with Kotlin 1.6: https://github.com/InsertKoinIO/koin/issues/1188
            //androidLogger()
            androidContext(this@ArchMVCApp)
            modules(appModule)
        }
    }
}