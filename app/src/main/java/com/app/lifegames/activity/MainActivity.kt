package com.app.lifegames.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.app.lifegames.R
import com.app.lifegames.base_classes.BaseActivity
import com.app.lifegames.data.selectiondata.DataItem
import com.app.lifegames.database.AppDatabase
import com.app.lifegames.database.DatabaseClient
import com.app.lifegames.database.Entities.CategoriesTable
import com.app.lifegames.ui.selectAge.SelectionFragment
import com.app.lifegames.ui.splash.SplashFragment
import com.app.lifegames.utils.Utils


class MainActivity : BaseActivity() {
    public var database: AppDatabase? = null
    var context: Context? = null;

    override fun getID(): Int {


        return R.layout.activity_main_new
    }


    override fun iniView(savedInstanceState: Bundle?) {
        //  database = AppDatabase.getInstance(this)
        context = applicationContext
        initViews()
    }


    private fun initViews() {
        val intent = getIntent();
        if (intent != null) {
            if (intent.getBooleanExtra("Back", false) == true) {
                addFragment(SelectionFragment(), true, R.id.container)

            } else {
                addFragment(SplashFragment(), true, R.id.container)
            }
        } else {
            addFragment(SplashFragment(), true, R.id.container)
        }

    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is SelectionFragment) {
            finish()
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()

        } else if (!mDoubleBackToExitPressedOnce) {
            this.mDoubleBackToExitPressedOnce = true
            Utils.showToast(this, resources.getString(R.string.home_back_button))

            Handler().postDelayed({ mDoubleBackToExitPressedOnce = false }, 1000)
        } else {
            super.onBackPressed()
            return
        }
    }
}
