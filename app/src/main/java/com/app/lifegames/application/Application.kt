package com.app.lifegames.application

import Preferences
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.splunk.mint.Mint
import com.twitter.sdk.android.core.Twitter
import android.util.Log
import com.app.lifegames.R
import com.google.firebase.messaging.FirebaseMessaging
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.TwitterConfig



/**
 * Created by android on 3/11/17.
 */
class Application : Application() {
   //
    var mContext: Context? = null


    companion object AppContext {
        lateinit var instance: com.app.lifegames.application.Application
         fun getContext(): Context {
            return instance
        }
    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()
        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(TwitterAuthConfig(resources.getString(R.string.API_KEY), resources.getString(R.string.API_SECRET_KEY)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build()

        //finally initialize twitter with created configs
        Twitter.initialize(config)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        Mint.initAndStartSession(applicationContext, "8baf3480");
        Preferences.initPreferences(this)
        mContext = applicationContext
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
