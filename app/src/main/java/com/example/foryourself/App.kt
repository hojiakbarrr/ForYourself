package com.example.foryourself

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.foryourself.db.ProductDao
import com.example.foryourself.utils.Constants
import com.parse.Parse
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App: Application(){
    @Inject
    lateinit var dao : ProductDao

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(Constants.APPLICATION_ID)
                .clientKey(Constants.CLIENT_KEY)
                .server(Constants.BASE_URL)
                .build())

        applicationScope.launch {
            dao.clearTable()
            dao.clearTableReklama()
        }

    }



}