package com.app.lifegames.ui.favouriteActivitiesList

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.app.lifegames.R
import com.app.lifegames.adapter.FavouriteActivitiesListAdapter
import com.app.lifegames.adapter.ScheduleDateListAdapter
import com.app.lifegames.adapter.ScheduleTimeAdapter
import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.data.favouriteActivities.DataItem
import com.app.lifegames.database.DatabaseClient
import com.app.lifegames.database.Entities.FavouriteActivitiesTable
import com.app.lifegames.database.Entities.ScheduleDatesTable
import com.app.lifegames.ui.activitiesDetail.FavouriteActivitiesDetail

import com.app.lifegames.ui.favouriteActivitiesList.apiHit.FavouriteActivityViewModel
import com.app.lifegames.ui.premimum.FragmentPremimum
import com.app.lifegames.ui.selectAge.apiHit.ActivityListViewModel
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_favourite_activities.*

class FavouriteActivitiesFragment : BaseFragment() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var arrayAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewManager2: RecyclerView.LayoutManager
    private  var activitiesList: MutableList<DataItem> = ArrayList()
    private var mViewListModel: FavouriteActivityViewModel? = null
    private var mmViewListModel: ActivityListViewModel? = null
    var userID:String?=null
    var timeList:ArrayList<String>?=null
    var selectedDate:String?=null
    var selectedTime:String?=null
    var scheduleString:String?=null
    var newDate:String?=null
    var newTime:String?=null
    var finalDate:String?=null
    var setRemd:String?="1"
    var position:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mmViewListModel= ViewModelProviders.of(this).get(ActivityListViewModel::class.java)
        userID = PreferenceClass.getStringPreferences(this.activity, Constants.USER_ID)
        mViewListModel= ViewModelProviders.of(this).get(FavouriteActivityViewModel::class.java)

        if (isNetworkAvailable(getView())) {
            mViewListModel!!.authenticate(userID!!)

        } else {
            RetrieveTask(activity!!,mViewListModel!!,userID!!).execute()
        }


        getDoneActivitesList()

    }

    private fun getDoneActivitesList() {
        mViewListModel?.response?.observe(this, Observer {
            it?.let {

                if(it.status==1)
                {
                    activitiesList.clear()
                    it.data?.let { it1 -> activitiesList.addAll(it1) }
                    InsertTask(activitiesList!!,activity!!).execute()
                }

                if(it.status==0)
                {
                    activitiesList.clear()
                    InsertTask(activitiesList!!,activity!!).execute()
                }


            }
        })

        mmViewListModel?.responseRemoveFromFav?.observe(this, Observer {
            it?.let {
                if(it.status==1)
                {
                   // activitiesList.clear()
                    Toast.makeText(activity,it.message.toString(),Toast.LENGTH_SHORT).show()
                  //  viewAdapter.notifyItemRemoved(position)
                   // viewAdapter.notifyDataSetChanged()
                    if (isNetworkAvailable(getView())) {
                        mViewListModel!!.authenticate(userID!!)

                    } else {
                        RetrieveTask(activity!!,mViewListModel!!,userID!!).execute()
                    }
                   // RetrieveTask(activity!!,mViewListModel!!,userID!!).execute()

                }


            }
        })

        mViewListModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mViewListModel?.isLoading?.observe(this, Observer {
            if(it==true)
            {
                loading_fav_lay.visibility= View.VISIBLE
            }
            else
            {
                loading_fav_lay.visibility= View.GONE
            }
            // it?.let { showLoading(it) }
        })

        mViewListModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })

        ////////////////
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favourite_activities,container,false)
    }

    val openFragment: (String) -> Unit={
        PreferenceClass.setStringPreference(this.activity, Constants.ACTIVITY_TYPE,"fav")
        PreferenceClass.setStringPreference(activity, Constants.ACTIVIY_ID,it)
        addFragment(FavouriteActivitiesDetail(),true, R.id.container_2)
    }
    //===================Save Categories in database ==============================//
    inner class InsertTask// only retain a weak reference to the activity
    internal constructor(val myDataset: MutableList<DataItem>, val context: Context) : AsyncTask<Void, Void, List<FavouriteActivitiesTable>>()
    {

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs: Void): List<FavouriteActivitiesTable>? {
            for (j in 0..myDataset.size - 1) {

                //adding to database


                var db = DatabaseClient.getInstance(context).appDatabase.taskDao
                if (db.getFavActvities(userID!!).size != myDataset.size) {
                    if (db.getPartFavActvities(myDataset.get(j).id.toString()).size != 0) {


                        db.updateFavActivities(myDataset.get(j).id.toString(),
                                myDataset.get(j).image.toString()
                                ,myDataset.get(j).title.toString(), myDataset.get(j).ageGroup.toString(),
                                myDataset.get(j).ageGroupIcon.toString(),
                                myDataset.get(j).overview.toString(), myDataset.get(j).catColor.toString(),
                                myDataset.get(j).catName.toString(), myDataset.get(j).catValue.toString()
                                ,myDataset.get(j).time.toString(),myDataset.get(j).materials.toString(),
                                myDataset.get(j).warnings.toString(),myDataset.get(j).completed.toString(),
                                myDataset.get(j).favourite.toString(),
                                myDataset.get(j).activityDetails.toString(),
                                myDataset.get(j).catIcon.toString(),myDataset.get(j).ratings.toString(),userID!!)



                    } else {

                        var cat: FavouriteActivitiesTable = FavouriteActivitiesTable(myDataset.get(j).id.toString(),
                                myDataset.get(j).image.toString()
                                , myDataset.get(j).title.toString(), myDataset.get(j).ageGroup.toString(),
                                myDataset.get(j).ageGroupIcon.toString(),
                                myDataset.get(j).overview.toString(), myDataset.get(j).catColor.toString(),
                                myDataset.get(j).catName.toString(), myDataset.get(j).catValue.toString()
                                ,myDataset.get(j).time.toString(),myDataset.get(j).materials.toString(),
                                myDataset.get(j).warnings.toString(),myDataset.get(j).completed.toString(),
                                myDataset.get(j).favourite.toString(),
                                myDataset.get(j).activityDetails.toString(),
                                myDataset.get(j).catIcon.toString(),myDataset.get(j).ratings.toString(),userID)
                        db.insertFavActivities(cat)
                    }

                }
                else {
                    if (db.getPartFavActvities(myDataset.get(j).id.toString()).size != 0) {


                        db.updateFavActivities(myDataset.get(j).id.toString(),
                                myDataset.get(j).image.toString()
                                ,myDataset.get(j).title.toString(), myDataset.get(j).ageGroup.toString(),
                                myDataset.get(j).ageGroupIcon.toString(),
                                myDataset.get(j).overview.toString(), myDataset.get(j).catColor.toString(),
                                myDataset.get(j).catName.toString(), myDataset.get(j).catValue.toString()
                                ,myDataset.get(j).time.toString(),myDataset.get(j).materials.toString(),
                                myDataset.get(j).warnings.toString(),myDataset.get(j).completed.toString(),
                                myDataset.get(j).favourite.toString(),
                                myDataset.get(j).activityDetails.toString(),
                                myDataset.get(j).catIcon.toString(),myDataset.get(j).ratings.toString(),userID!!)



                    } else {

                        var cat: FavouriteActivitiesTable = FavouriteActivitiesTable(myDataset.get(j).id.toString(),
                                myDataset.get(j).image.toString()
                                , myDataset.get(j).title.toString(), myDataset.get(j).ageGroup.toString(),
                                myDataset.get(j).ageGroupIcon.toString(),
                                myDataset.get(j).overview.toString(), myDataset.get(j).catColor.toString(),
                                myDataset.get(j).catName.toString(), myDataset.get(j).catValue.toString()
                                ,myDataset.get(j).time.toString(),myDataset.get(j).materials.toString(),
                                myDataset.get(j).warnings.toString(),myDataset.get(j).completed.toString(),
                                myDataset.get(j).favourite.toString(),
                                myDataset.get(j).activityDetails.toString(),
                                myDataset.get(j).catIcon.toString(),myDataset.get(j).ratings.toString(),userID)
                        db.insertFavActivities(cat)
                    }

                }
            }





            return  DatabaseClient.getInstance(context).appDatabase
                    .taskDao
                    .getFavActvities(userID!!)
        }

        // onPostExecute runs on main thread
        override fun onPostExecute(list: List<FavouriteActivitiesTable>?) {
            if (list!!.size!=0) {
                val mLayoutManager = LinearLayoutManager(context)
                viewManager = mLayoutManager
                viewAdapter = FavouriteActivitiesListAdapter(openFragment, list, addToPlanner,removefromFav)
                //viewAdapter =LanguageSpinnerAdapter(activity);
                rv_fav_activities_list.layoutManager=viewManager
                rv_fav_activities_list.adapter=viewAdapter
            }
            else
            {
                rv_fav_activities_list.visibility=View.GONE
                no_fav_activities_found.visibility=View.VISIBLE

            }

        }
    }


    //===================Retrieve Categories==============================//
    inner class RetrieveTask// only retain a weak reference to the activity
    internal constructor(val context: Context, val mViewListModel: FavouriteActivityViewModel, val userID: String) : AsyncTask<Void, Void, List<FavouriteActivitiesTable>>() {


        override fun doInBackground(vararg voids: Void): List<FavouriteActivitiesTable> {
            return DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .getFavActvities(userID!!)
        }

        override fun onPostExecute(tasks: List<FavouriteActivitiesTable>) {
            super.onPostExecute(tasks)
          Log.d("Data"," "+tasks+" "+tasks.size)
            if (tasks.size == 0)
            {
                if (isNetworkAvailable(getView())) {
                    mViewListModel!!.authenticate(userID)
                    rv_fav_activities_list.visibility=View.VISIBLE
                    no_fav_activities_found.visibility=View.GONE

                } else {
                    rv_fav_activities_list.visibility=View.GONE
                    no_fav_activities_found.visibility=View.VISIBLE
                    Toast.makeText(context,"Please enable internet to get the list.", Toast.LENGTH_SHORT).show()
                }

            }
            else
            {
                val mLayoutManager = LinearLayoutManager(context)
                viewManager = mLayoutManager

                viewAdapter = FavouriteActivitiesListAdapter(openFragment,tasks,addToPlanner,removefromFav)
                //viewAdapter =LanguageSpinnerAdapter(activity);
                rv_fav_activities_list.layoutManager=viewManager
                rv_fav_activities_list.adapter=viewAdapter
                viewAdapter.notifyDataSetChanged()

            }

        }


    }
    val addToPlanner: (String) -> Unit={
        val userType: String ?= PreferenceClass.getStringPreferences(this.activity, Constants.USER_TYPE)
        if(userType.equals("1"))
        {

         RetrieveDatesTask(activity!!,it!!).execute()
        }
        else
        {
            addFragment(FragmentPremimum(),true,R.id.container_2)
        }
    }


    val removefromFav: (String) -> Unit={
        val finalstring=it.split(",");
        position=finalstring[1].toInt()
        DeleteTask(activity!!,finalstring[0].toString()!!,finalstring[1].toString()!!).execute()
    }
    //===================Retrieve Categories==============================//
    inner class RetrieveDatesTask// only retain a weak reference to the activity
    internal constructor(val context: Context,val actvityID: String) : AsyncTask<Void, Void, List<ScheduleDatesTable>>() {


        override fun doInBackground(vararg voids: Void): List<ScheduleDatesTable> {
            val db= DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
            return db.getAllDates()
        }

        override fun onPostExecute(tasks: List<ScheduleDatesTable>) {
            super.onPostExecute(tasks)
            showPlannerDialog(tasks,actvityID!!)
        }


    }

    private fun showPlannerDialog(tasks: List<ScheduleDatesTable>, actvityID: String) {
        val reviewDialog = Dialog(activity);
        reviewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        reviewDialog.setContentView(R.layout.dialog_add_planner);

        timeList= ArrayList();

        timeList!!.add("08:00 AM")
        timeList!!.add("08:30 AM")
        timeList!!.add("09:00 AM")
        timeList!!.add("09:30 AM")
        timeList!!.add("10:00 AM")
        timeList!!.add("10:30 AM")
        timeList!!.add("11:00 AM")
        timeList!!.add("11:30 AM")
        timeList!!.add("12:00 PM")
        timeList!!.add("12:30 PM")
        timeList!!.add("13:00 PM")
        timeList!!.add("13:30 PM")
        timeList!!.add("14:00 PM")
        timeList!!.add("14:30 PM")
        timeList!!.add("15:00 PM")
        timeList!!.add("15:30 PM")
        timeList!!.add("16:00 PM")
        timeList!!.add("16:30 PM")
        timeList!!.add("17:00 PM")
        timeList!!.add("17:30 PM")
        timeList!!.add("18:00 PM")
        timeList!!.add("18:30 PM")
        timeList!!.add("19:00 PM")
        timeList!!.add("19:30 PM")
        timeList!!.add("20:00 PM")
        timeList!!.add("20:30 PM")
        timeList!!.add("21:00 PM")
        timeList!!.add("21:30 PM")
        timeList!!.add("22:00 PM")
        timeList!!.add("22:30 PM")
        timeList!!.add("23:00 PM")
        timeList!!.add("23:30 PM")
        timeList!!.add("24:00 PM")
        timeList!!.add("24:30 PM")
        timeList!!.add("00:00 AM")
        timeList!!.add("00:30 AM")
        timeList!!.add("01:00 AM")
        timeList!!.add("01:30 AM")
        timeList!!.add("02:00 AM")
        timeList!!.add("02:30 AM")
        timeList!!.add("03:00 AM")
        timeList!!.add("03:30 AM")
        timeList!!.add("04:00 AM")
        timeList!!.add("04:30 AM")
        timeList!!.add("05:00 AM")
        timeList!!.add("05:30 AM")
        timeList!!.add("06:00 AM")
        timeList!!.add("06:30 AM")
        timeList!!.add("07:00 AM")
        timeList!!.add("07:30 AM")
        Log.d("tasks"," "+tasks.size);
        reviewDialog.show()
        val rv_dates = reviewDialog.findViewById(R.id.rv_dates) as RecyclerView
        val rv_time = reviewDialog.findViewById(R.id.rv_time) as RecyclerView
        val radioGroup = reviewDialog.findViewById(R.id.rg_button) as RadioGroup
        val setReminder=reviewDialog.findViewById(R.id.btn_reminder)as Button
        val atTime=reviewDialog.findViewById(R.id.rb_at_the_time)as RadioButton
        val beforeTime=reviewDialog.findViewById(R.id.rb_min_before)as RadioButton

        if(PreferenceClass.getStringPreferences(activity,Constants.REMINDER_CHECK).equals("1"))
        {
            atTime.isChecked=true;
            beforeTime.isChecked=false
        }
        else if(PreferenceClass.getStringPreferences(activity,Constants.REMINDER_CHECK).equals("0"))
        {
            beforeTime.isChecked=true
            atTime.isChecked=false;
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId.equals(R.id.rb_at_the_time))
            {
                setRemd="1"
                PreferenceClass.setStringPreference(activity,Constants.REMINDER_CHECK,setRemd)
                // var finalDate=selectedDate+" "+selectedTime
               //convertDateFormat(finalDate)
            }
            else if(checkedId.equals(R.id.rb_min_before))
            {   setRemd="0"
                PreferenceClass.setStringPreference(activity,Constants.REMINDER_CHECK,setRemd)
            }

        }


        setReminder.setOnClickListener {
            Log.d("Parameters"," "+scheduleString+" "+finalDate+" "+setRemd)
            if(selectedDate==null)
            {
                selectedDate=tasks.get(0).date.toString()
                newDate=tasks.get(0).date_new.toString()
            }


            if(selectedTime==null)
            {
                selectedTime="08:00 AM"
                val value=timeList.toString().split(" ")
                newTime=value[0]
            }

            scheduleString=selectedTime+","+selectedDate
            finalDate=newDate+" "+newTime


            reviewDialog.dismiss()
            mmViewListModel!!.authenticateMarkAsPlanner(userID!!,actvityID,scheduleString!!,setRemd!!,finalDate!!)
        }

        val mLayoutManager = LinearLayoutManager(context)
        val mLayoutManager2 = LinearLayoutManager(context)
        viewManager = mLayoutManager
        viewManager2 = mLayoutManager2
        viewAdapter = ScheduleDateListAdapter(tasks, getDate, updateValues, actvityID)
        arrayAdapter= ScheduleTimeAdapter(timeList, getTime);
        rv_time.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager2

            // specify an viewAdapter (see also next example)
            adapter = arrayAdapter


        }
        //viewAdapter =LanguageSpinnerAdapter(activity);
        rv_dates.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter


        }

    }


    val getDate: (String) -> Unit={
        val split=it.toString().split(",")
        selectedDate=split[0].toString()
        newDate=split[1].toString()
    }
    val getTime: (String) -> Unit={
        selectedTime=it.toString()
        val value=it.toString().split(" ")
        newTime=value[0]

    }


    val updateValues: (String) -> Unit = {

        RetrieveDatesTask(activity!!,it!!).execute()
    }

    //===================Retrieve Categories==============================//
    inner class DeleteTask// only retain a weak reference to the activity
    internal constructor(val context: Context, val actvityID: String, pos: String) : AsyncTask<Void, Void, Boolean>() {


        override fun doInBackground(vararg voids: Void): Boolean {
            val db= DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
            db.deleteFavActvities(userID!!,actvityID!!)

            return true

        }

        override fun onPostExecute(tasks: Boolean) {
            super.onPostExecute(tasks)
            Log.d("Detail<><>"," "+userID+" "+actvityID)
            mmViewListModel!!.authenticateRemoveFromFav(userID!!,actvityID!!)
        }


    }
}