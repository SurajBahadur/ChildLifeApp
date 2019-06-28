package com.app.lifegames.ui.plannerActivitiesList

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
import com.app.lifegames.adapter.*
import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.data.plannerActivities.DataItem
import com.app.lifegames.database.DatabaseClient
import com.app.lifegames.database.Entities.PlannerActivitiesTable
import com.app.lifegames.database.Entities.ScheduleDatesTable
import com.app.lifegames.ui.activitiesDetail.PlannerActivitiesDetail

import com.app.lifegames.ui.plannerActivitiesList.apiHit.PlannerActivityViewModel
import com.app.lifegames.ui.selectAge.apiHit.ActivityListViewModel
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_planner_activites.*
import java.text.ParseException
import java.text.SimpleDateFormat


class PlannerActivitiesFragment : BaseFragment() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var plannerAdapter: RecyclerView.Adapter<*>
    private lateinit var arrayAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewManager2: RecyclerView.LayoutManager
    private  var activitiesList: MutableList<DataItem> = ArrayList<DataItem>()
    private var mViewListModel: PlannerActivityViewModel? = null
    private var mListModel: ActivityListViewModel? = null
    var scheduleList: List<ScheduleDatesTable>?=null
    var timeList:ArrayList<String>?=null
    var userID:String?=null
    var selectedDate:String?=null
    var selectedTime:String?=null
    var age:String?=null
    var category:String?=null
    var scheduleString:String?=null
    var newDate:String?=null
    var newTime:String?=null
    var finalDate:String?=null
    var setRemd:String?="1"
    var edit:Boolean=true;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListModel= ViewModelProviders.of(this).get(ActivityListViewModel::class.java)
        userID = PreferenceClass.getStringPreferences(this.activity, Constants.USER_ID)
        age = PreferenceClass.getStringPreferences(this.activity, Constants.AGE)
        category = PreferenceClass.getStringPreferences(this.activity, Constants.CATEGORY)
        mViewListModel= ViewModelProviders.of(this).get(PlannerActivityViewModel::class.java)

        if (isNetworkAvailable(getView())) {
            mViewListModel!!.authenticate(userID!!,age!!,category!!)

        } else {
            RetrieveTask(activity!!,mViewListModel!!,userID!!,age!!,category!!).execute()
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
                //    UpdatePlannerList(activity!!,userID!!,age!!,category!!).execute()
                    InsertTask(activitiesList,activity!!).execute()

                }



            }
        })



        mListModel?.responseMarkAsPlanner?.observe(this, Observer {
            it?.let {

                if(it.status==1)
                {

                    if (isNetworkAvailable(getView())) {
                        mViewListModel!!.authenticate(userID!!,age!!,category!!)

                    } else {
                        RetrieveTask(activity!!,mViewListModel!!,userID!!,age!!,category!!).execute()
                    }

                    Toast.makeText(activity,it.message.toString(),Toast.LENGTH_SHORT).show()

                }


            }
        })

        mViewListModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mViewListModel?.isLoading?.observe(this, Observer {
           /* if(it==true)
            {
                loading_planner_lay.visibility= View.VISIBLE
            }
            else
            {
                loading_planner_lay.visibility= View.GONE
            }*/
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

        return inflater.inflate(R.layout.fragment_planner_activites,container,false)
    }

    val openFragment: (String) -> Unit={
        PreferenceClass.setStringPreference(activity, Constants.ACTIVIY_ID,it)
        addFragment(PlannerActivitiesDetail(),true, R.id.container_2)
    }


    val editPlanerActivity: (PlannerActivitiesTable) -> Unit={

        RetrieveDatesTask(activity!!,it).execute()

    }

    val removePlanerActivity: (String) -> Unit={
        DeleteTask(activity!!,it.toString()).execute()
        mListModel!!.authenticateRemove(userID!!,it.toString())
        attachObserver()


    }

    private fun attachObserver() {
        mListModel?.responseRemoveActivtiy?.observe(this, Observer {
            it?.let {

                if(it.status==1)
                {

                    if (isNetworkAvailable(getView())) {
                        mViewListModel!!.authenticate(userID!!,age!!,category!!)

                    } else {
                        RetrieveTask(activity!!,mViewListModel!!,userID!!,age!!,category!!).execute()
                    }


                    Toast.makeText(activity,it.message.toString(),Toast.LENGTH_SHORT).show()


                }


            }
        })

        mListModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mListModel?.isLoading?.observe(this, Observer {
            /* if(it==true)
             {
                 loading_planner_lay.visibility= View.VISIBLE
             }
             else
             {
                 loading_planner_lay.visibility= View.GONE
             }*/
            // it?.let { showLoading(it) }
        })

        mListModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })


    }

    //===================Save Categories in database ==============================//
    inner class InsertTask// only retain a weak reference to the activity
    internal constructor(val myDataset: MutableList<DataItem>, val context: Context) : AsyncTask<Void, Void, List<PlannerActivitiesTable>>()
    {

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs: Void): List<PlannerActivitiesTable>? {
            for (j in 0..myDataset.size - 1) {


                var db = DatabaseClient.getInstance(context).appDatabase.taskDao
                if (db.getPlannerActvities(userID!!).size != myDataset.size) {
                    if (db.getPartPlannerActvities(myDataset.get(j).id.toString()).size != 0) {


                        db.updatePlannerActivities(myDataset.get(j).id.toString(),
                                myDataset.get(j).image.toString()
                                , myDataset.get(j).title.toString(), myDataset.get(j).ageGroup.toString(),
                                myDataset.get(j).ageGroupIcon.toString(),
                                myDataset.get(j).overview.toString(), myDataset.get(j).catColor.toString(),
                                myDataset.get(j).catName.toString(), myDataset.get(j).catValue.toString()
                                ,myDataset.get(j).time.toString(),myDataset.get(j).materials.toString(),
                                myDataset.get(j).warnings.toString(),myDataset.get(j).completed.toString(),
                                myDataset.get(j).favourite.toString(),
                                myDataset.get(j).activityDetails.toString(),
                                myDataset.get(j).catIcon.toString(),myDataset.get(j).ratings.toString(),
                                userID!!,myDataset.get(j).schedule.toString(),myDataset.get(j).reminder.toString())


                    } else {

                        var cat: PlannerActivitiesTable = PlannerActivitiesTable(myDataset.get(j).id.toString(),
                                myDataset.get(j).image.toString()
                                , myDataset.get(j).title.toString(), myDataset.get(j).ageGroup.toString(),
                                myDataset.get(j).ageGroupIcon.toString(),
                                myDataset.get(j).overview.toString(), myDataset.get(j).catColor.toString(),
                                myDataset.get(j).catName.toString(), myDataset.get(j).catValue.toString()
                                ,myDataset.get(j).time.toString(),myDataset.get(j).materials.toString(),
                                myDataset.get(j).warnings.toString(),myDataset.get(j).completed.toString(),
                                myDataset.get(j).favourite.toString(),
                                myDataset.get(j).activityDetails.toString(),
                                myDataset.get(j).catIcon.toString(),myDataset.get(j).ratings.toString()
                                ,userID,myDataset.get(j).schedule.toString(),myDataset.get(j).reminder.toString())
                        db.insertPlannerActivities(cat)
                    }

                }
                else {
                    if (db.getPartPlannerActvities(myDataset.get(j).id.toString()).size != 0) {


                        db.updatePlannerActivities(myDataset.get(j).id.toString(),
                                myDataset.get(j).image.toString()
                                , myDataset.get(j).title.toString(), myDataset.get(j).ageGroup.toString(),
                                myDataset.get(j).ageGroupIcon.toString(),
                                myDataset.get(j).overview.toString(), myDataset.get(j).catColor.toString(),
                                myDataset.get(j).catName.toString(), myDataset.get(j).catValue.toString()
                                ,myDataset.get(j).time.toString(),myDataset.get(j).materials.toString(),
                                myDataset.get(j).warnings.toString(),myDataset.get(j).completed.toString(),
                                myDataset.get(j).favourite.toString(),
                                myDataset.get(j).activityDetails.toString(),
                                myDataset.get(j).catIcon.toString(),myDataset.get(j).ratings.toString()
                                ,userID!!,myDataset.get(j).schedule.toString(),myDataset.get(j).reminder.toString())


                    } else {

                        var cat: PlannerActivitiesTable = PlannerActivitiesTable(myDataset.get(j).id.toString(),
                                myDataset.get(j).image.toString()
                                , myDataset.get(j).title.toString(), myDataset.get(j).ageGroup.toString(),
                                myDataset.get(j).ageGroupIcon.toString(),
                                myDataset.get(j).overview.toString(), myDataset.get(j).catColor.toString(),
                                myDataset.get(j).catName.toString(), myDataset.get(j).catValue.toString()
                                ,myDataset.get(j).time.toString(),myDataset.get(j).materials.toString(),
                                myDataset.get(j).warnings.toString(),myDataset.get(j).completed.toString(),
                                myDataset.get(j).favourite.toString(),
                                myDataset.get(j).activityDetails.toString(),
                                myDataset.get(j).catIcon.toString(),myDataset.get(j).ratings.toString(),
                                userID,myDataset.get(j).schedule.toString(),myDataset.get(j).reminder.toString())
                        db.insertPlannerActivities(cat)
                    }

                }

            }





            return  DatabaseClient.getInstance(context).appDatabase
                    .taskDao
                    .getPlannerActvities(userID!!)
        }

        // onPostExecute runs on main thread
        override fun onPostExecute(list: List<PlannerActivitiesTable>?) {
            if (list!!.size!=0) {
                rv_planner_activities_list.visibility=View.VISIBLE
                no_planner_activities_found.visibility=View.GONE
                val mLayoutManager = LinearLayoutManager(context)
                viewManager = mLayoutManager

                plannerAdapter = PlannerActivitiesListAdapter(openFragment, list, editPlanerActivity,removePlanerActivity)
                //viewAdapter =LanguageSpinnerAdapter(activity);
                rv_planner_activities_list.layoutManager=viewManager
                rv_planner_activities_list.adapter=plannerAdapter
                plannerAdapter.notifyDataSetChanged()

            }
            else
            {
                rv_planner_activities_list.visibility=View.GONE
                no_planner_activities_found.visibility=View.VISIBLE
                val mLayoutManager = LinearLayoutManager(context)
                viewManager = mLayoutManager

                plannerAdapter = PlannerActivitiesListAdapter(openFragment, list, editPlanerActivity,removePlanerActivity)
                //viewAdapter =LanguageSpinnerAdapter(activity);
                rv_planner_activities_list.layoutManager=viewManager
                rv_planner_activities_list.adapter=plannerAdapter
                plannerAdapter.notifyDataSetChanged()
            }

        }
    }


    //===================Retrieve Categories==============================//
    inner class RetrieveTask// only retain a weak reference to the activity
    internal constructor(val context: Context, val mViewListModel: PlannerActivityViewModel, val userID: String,val age: String,val category: String) : AsyncTask<Void, Void, List<PlannerActivitiesTable>>() {


        override fun doInBackground(vararg voids: Void): List<PlannerActivitiesTable> {
            return DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .getPlannerActvities(userID!!)
        }

        override fun onPostExecute(tasks: List<PlannerActivitiesTable>) {
            super.onPostExecute(tasks)

            if (tasks.size == 0) {
                if (isNetworkAvailable(getView())) {
                    mViewListModel!!.authenticate(userID,age,category)
                    no_planner_activities_found.visibility=View.VISIBLE
                    rv_planner_activities_list.visibility=View.GONE

                } else {

                    Toast.makeText(context,"Please enable internet to get the list.", Toast.LENGTH_SHORT).show()
                }

            }
            else
            {
                rv_planner_activities_list.visibility=View.GONE
                no_planner_activities_found.visibility=View.VISIBLE
                val mLayoutManager = LinearLayoutManager(context)
                viewManager = mLayoutManager

                plannerAdapter = PlannerActivitiesListAdapter(openFragment, tasks, editPlanerActivity,removePlanerActivity)
                //viewAdapter =LanguageSpinnerAdapter(activity);
                rv_planner_activities_list.layoutManager=viewManager
                rv_planner_activities_list.adapter=plannerAdapter
                plannerAdapter.notifyDataSetChanged()
            }

        }


    }


    //=========================Delete data===========================//
    inner class UpdatePlannerList// only retain a weak reference to the activity
    internal constructor(val context: Context, val userID: String,val age: String,val category: String) : AsyncTask<Void, Void,Boolean>() {


        override fun doInBackground(vararg voids: Void): Boolean {
             DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .deletePlannerActvities(userID!!)

            return true
        }

        override fun onPostExecute(tasks: Boolean) {
            super.onPostExecute(tasks)

            if (tasks == true) {
                if (isNetworkAvailable(getView())) {
                    mViewListModel!!.authenticate(userID,age,category)
                    rv_planner_activities_list.visibility=View.VISIBLE
                    no_planner_activities_found.visibility=View.GONE

                } else {
                    no_planner_activities_found.visibility=View.VISIBLE
                    rv_planner_activities_list.visibility=View.GONE
                    Toast.makeText(context,"Please enable internet to get the list.", Toast.LENGTH_SHORT).show()
                }

            }


        }


    }

    //===================Retrieve Categories==============================//
    inner class RetrieveDatesTask// only retain a weak reference to the activity
    internal constructor(val context: Context,val actvityID: PlannerActivitiesTable) : AsyncTask<Void, Void, List<ScheduleDatesTable>>() {


        override fun doInBackground(vararg voids: Void): List<ScheduleDatesTable> {
            val db= DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao

            return db.getAllDates()



        }

        override fun onPostExecute(tasks: List<ScheduleDatesTable>) {
            super.onPostExecute(tasks)

            scheduleList=tasks;
            showPlannerDialog(scheduleList!!,actvityID!!)
        }


    }

    //===================Retrieve Categories==============================//
    inner class DeleteTask// only retain a weak reference to the activity
    internal constructor(val context: Context,val actvityID: String) : AsyncTask<Void, Void, Boolean>() {


        override fun doInBackground(vararg voids: Void): Boolean {
            val db= DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao

             db.deleteAct(actvityID!!,userID!!)

            return true

        }

        override fun onPostExecute(tasks: Boolean) {
            super.onPostExecute(tasks)


        }


    }



    private fun showPlannerDialog(tasks: List<ScheduleDatesTable>,list: PlannerActivitiesTable) {
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

        if(list.reminder.toString().equals("1"))
        {
            atTime.isChecked=true;
            beforeTime.isChecked=false
        }
        else if(list.reminder.toString().equals("0"))
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
            {
                setRemd="0"
                PreferenceClass.setStringPreference(activity,Constants.REMINDER_CHECK,setRemd)

            }

        }


        val mLayoutManager = LinearLayoutManager(context)
        val mLayoutManager2 = LinearLayoutManager(context)
        viewManager = mLayoutManager
        viewManager2=mLayoutManager2
        viewAdapter = EditScheduleDateListAdapter(tasks,getDate,updateValues,list,edit)
        arrayAdapter= EditScheduleTimeAdapter(timeList,getTime,edit,list);

        setReminder.setOnClickListener {
            if(selectedDate==null)
            {
                var str=list.schedule.split(",")
                Log.d("list"," "+list.schedule.split(","));
                for(j in 0.. tasks.size-1)
                {
                    val data=tasks.get(j);
                    Log.d("Schedule"," "+str[1]+" "+data.date.toString());
                    if(str[1].toString().equals(data.date.toString()))
                    {

                        selectedDate=str[1].toString()
                        newDate=data.date_new.toString()
                    }
                }

            }
            if(selectedTime==null)
            {
                var str=list.schedule.split(",")
                for(j in 0.. timeList!!.size-1) {
                    val data = timeList!!.get(j);
                    if (str[0].toString().equals(data.toString())) {
                        selectedTime=data.toString()
                        val value=data.toString().split(" ")
                        newTime=value[0]
                    }
                }

            }



            scheduleString=selectedTime+","+selectedDate
            finalDate=newDate+" "+newTime
            reviewDialog.dismiss()
            mListModel!!.authenticateMarkAsPlanner(userID!!,list.actID,scheduleString!!,setRemd!!,finalDate!!)
        }
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
        rv_dates.layoutManager= viewManager

        rv_dates.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter


        }
        viewAdapter.notifyDataSetChanged()
    }

    private fun convertDateFormat(schedule: String) {
//Tuesday 14th April 9:30 AM  2019-03-27 00:58:12
        val input = SimpleDateFormat("EEEE DD MM HH:MM a")
       // val output = SimpleDateFormat("yyyy-MM-DD HH:MM:SS")
        try {
           var oneWayTripDate = input.parse(schedule)                 // parse input
           Log.d("Date"," "+oneWayTripDate)    // format output
        } catch (e: ParseException) {
            e.printStackTrace()
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

        //RetrieveDatesTask(activity!!,it.toString()).execute()
    }
}