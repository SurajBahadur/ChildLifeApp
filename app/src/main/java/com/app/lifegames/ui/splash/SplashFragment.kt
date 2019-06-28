package com.app.lifegames.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.lifegames.R
import com.app.lifegames.base_classes.BaseFragment
import android.os.CountDownTimer
import android.util.Log
import com.app.lifegames.database.DatabaseClient
import com.app.lifegames.database.Entities.ScheduleDatesTable
import com.app.lifegames.ui.language.LanguageSelectionFragment
import com.app.lifegames.ui.splash.apiHit.SaveDeviceIDViewModel
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.security.ApiFailureTypes
import android.R.id.edit
import android.content.SharedPreferences
import com.app.lifegames.ui.selectAge.SelectionFragment
import com.braintreepayments.api.internal.BraintreeSharedPreferences.getSharedPreferences


/**
 * Created by ${Shubham} on 12/25/2018.
 */
class SplashFragment : BaseFragment() {
    var firstTime: Boolean? = null
    private var mViewModel: SaveDeviceIDViewModel? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DeviceId", "******" + deviceToken())
        mViewModel = ViewModelProviders.of(this).get(SaveDeviceIDViewModel::class.java)
        var token = PreferenceClass.getStringPreferences(activity, Constants.FCM_TOKEN);
        mViewModel!!.getDates()
        /*  if (isNetworkAvailable(getView())) {
              mViewModel!!.authenticate(deviceToken(),token)

          } else {*/

        setUpData()
        /* }


         attachObservers()*/
        attachObservers()
    }

    private fun setUpData() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (isFirstTime() == false) {
                    addFragment(LanguageSelectionFragment(), false, R.id.container)
                    PreferenceClass.setBooleanPreference(activity, Constants.CHECK_RUN, true)
                } else {
                    addFragment(SelectionFragment(), true, R.id.container)

                }


            }
        }.start()
    }

    private fun checkFirstRun() {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    private fun attachObservers() {
        mViewModel?.responseDates?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                    InsertTask(it.data!!, activity!!, it.date!!).execute()

                } else {

                }


            }
        })

        mViewModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mViewModel?.isLoading?.observe(this, Observer {

            // it?.let { showLoading(it) }
        })

        mViewModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })

        ////////////////


    }

    //===================Save Categories in database ==============================//
    inner class InsertTask// only retain a weak reference to the activity
    internal constructor(val myDataset: MutableList<String>, val context: Context, val date: MutableList<String?>) : AsyncTask<Void, Void, List<ScheduleDatesTable>>() {

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs: Void): List<ScheduleDatesTable>? {
            var db = DatabaseClient.getInstance(context).appDatabase.taskDao
            if (db.getAllDates().size != 0) {
                db.deleteDates()
            }
            for (j in 0..myDataset.size - 1) {

                val table = ScheduleDatesTable(myDataset.get(j).toString(), "0", date.get(j).toString())
                db.insertDates(table)


            }





            return DatabaseClient.getInstance(context).appDatabase
                    .taskDao
                    .getAllDates()
        }

        // onPostExecute runs on main thread
        override fun onPostExecute(list: List<ScheduleDatesTable>?) {
            if (list!!.size != 0) {

            }
        }

    }

    private fun isFirstTime(): Boolean {


        if (PreferenceClass.getBooleanPreferences(activity, Constants.CHECK_RUN) == true) {

            return true
        } else {

            return false
        }

    }
}