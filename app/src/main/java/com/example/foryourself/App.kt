package com.example.foryourself

import android.app.Application
import com.example.kapriz.utils.Constants
import com.parse.Parse
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){


    override fun onCreate() {
        super.onCreate()


        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(Constants.APPLICATION_ID)
                .clientKey(Constants.CLIENT_KEY)
                .server(Constants.BASE_URL)
                .build())

    }


}