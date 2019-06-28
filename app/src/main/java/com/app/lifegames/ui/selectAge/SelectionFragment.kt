package com.app.lifegames.ui.selectAge

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.lifegames.R
import com.app.lifegames.activity.ActivityMainNew
import com.app.lifegames.base_classes.BaseFragment
import kotlinx.android.synthetic.main.fragment_age_selection.*
import android.os.Vibrator
import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.app.lifegames.activity.MainActivity
import com.app.lifegames.adapter.SelectionCategoryAdapter
import com.app.lifegames.data.selectiondata.DataItem
import com.app.lifegames.database.AppDatabase
import com.app.lifegames.database.DatabaseClient
import com.app.lifegames.database.Entities.Categories
import com.app.lifegames.database.Entities.CategoriesTable
import com.app.lifegames.ui.selectAge.apiHit.ActivityListViewModel
import com.app.lifegames.ui.selectAge.apiHit.SelectionViewModel
import com.app.lifegames.ui.splash.apiHit.SaveDeviceIDViewModel
import com.app.lifegames.utils.security.ApiFailureTypes
import com.google.gson.Gson
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass


class SelectionFragment : BaseFragment() {
    var click: Boolean = false
    var click2: Boolean = false
    var click3: Boolean = false
    var click4: Boolean = false
    var click5: Boolean = false
    var click6: Boolean = false
    var clickage1: Boolean = false

    var clickage2: Boolean = false
    var clickage3: Boolean = false
    var clickage4: Boolean = false
    var activty: MainActivity? = null
    public var database: AppDatabase? = null
    private var categories: Categories? = null
    var age: String = "10-12"
    var category: String = "ALL"

    private var mDeviceViewModel: SaveDeviceIDViewModel? = null
    var myDataset: MutableList<DataItem> = ArrayList()
    var activitiesList: MutableList<com.app.lifegames.data.newActivityList.DataItem> = ArrayList()

    var selection: ArrayList<Boolean> = ArrayList()
    lateinit var viewAdapter: RecyclerView.Adapter<*>

    public var mViewModel: SelectionViewModel? = null
    private var mViewListModel: ActivityListViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_age_selection, container, false)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activty = MainActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userID: String = PreferenceClass.getStringPreferences(this.activity, Constants.USER_ID)
        mViewModel = ViewModelProviders.of(this).get(SelectionViewModel::class.java)
        mDeviceViewModel = ViewModelProviders.of(this).get(SaveDeviceIDViewModel::class.java)
        mViewListModel = ViewModelProviders.of(this).get(ActivityListViewModel::class.java)

        var token=PreferenceClass.getStringPreferences(activity,Constants.FCM_TOKEN);
        Log.d("token","******"+token)
        if (isNetworkAvailable(getView())) {
            mDeviceViewModel!!.authenticate(deviceToken(),token)

        }

        init();

        val vibe = activity!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        tv_lets_play_btn.setOnClickListener {
            //  loading.visibility=View.VISIBLE
            PreferenceClass.setStringPreference(activity, Constants.AGE, age)
            PreferenceClass.setStringPreference(activity, Constants.CATEGORY,category)
           // mViewListModel!!.authenticate(userID, category, age)
            vibe.vibrate(100)
            //  fame_lay.visibility = View.VISIBLE
            var intent = Intent(activity, ActivityMainNew::class.java)
            startActivity(intent)
            //activity?.finish()

        }


        /*if(activty!!.database!!.taskDao.getAll().size!=0)
        {
            displayList()
        }
        else
        {*/

        /*  }*/
       /* if (isNetworkAvailable(getView())) {
            mViewModel!!.authenticate()

        } else {
            RetrieveTask(activity!!, mViewModel!!).execute()
        }*/

        attachObservers()
        getActivitiesList()
    }

    private fun displayList() {
        // initialize database instance
        // fetch list of notes in background thread
        //activty!!.retrieveData()
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {
        selectMethod(iv_age_10_12, iv_age_10_12_black, tv_age_10_12, li_age_10_12);
        /* viewAdapter = SelectionCategoryAdapter(myDataset)
         rv_categoriesList.layoutManager = GridLayoutManager(this.activity, 2)
         rv_categoriesList.adapter = viewAdapter*/



        li_age_3_5.setOnClickListener {

            if (clickage1 == false) {

                age = "3-5"
                selectMethod(iv_age_3_5, iv_age_3_5_black, tv_3_5, li_age_3_5);
                unselectMethod(iv_age_6_9, iv_age_6_9_black, tv_age_6_9, li_age_6_9);
                unselectMethod(iv_age_10_12, iv_age_10_12_black, tv_age_10_12, li_age_10_12);
                unselectMethod(iv_age_all, iv_age_all_black, tv_all, li_age_all);

                /* iv_age_3_5.visibility=View.GONE
                 iv_age_3_5_black.visibility=View.VISIBLE
                 tv_3_5.setTextColor(Color.parseColor("#000000"))
                 li_age_3_5.setBackgroundColor(Color.parseColor("#FFFFFF"))

                 iv_age_6_9.visibility=View.VISIBLE
                 iv_age_6_9_black.visibility=View.GONE
                 tv_age_6_9.setTextColor(Color.parseColor("#FFFFFF"))
                 li_age_6_9.setBackgroundColor(android.R.color.transparent)

                 iv_age_10_12.visibility=View.VISIBLE
                 iv_age_10_12_black.visibility=View.GONE
                 tv_age_10_12.setTextColor(Color.parseColor("#FFFFFF"))
                 li_age_10_12.setBackgroundColor(android.R.color.transparent)

                 iv_age_all.visibility=View.VISIBLE
                 iv_age_all_black.visibility=View.GONE
                 tv_all.setTextColor(Color.parseColor("#FFFFFF"))
                 li_age_all.setBackgroundColor(android.R.color.transparent)*/

                clickage4 = false;
                clickage3 = false;
                clickage2 = false;
                clickage1 = true;


            } else {
                unselectMethod(iv_age_3_5, iv_age_3_5_black, tv_3_5, li_age_3_5);
                /* iv_age_3_5.visibility=View.VISIBLE
                 iv_age_3_5_black.visibility=View.GONE
                 tv_3_5.setTextColor(Color.parseColor("#FFFFFF"))
                 li_age_3_5.setBackgroundColor(android.R.color.transparent)*/
                clickage1 = false;
            }


        }

        li_age_6_9.setOnClickListener {

            if (clickage2 == false) {
                age = "6-9"
                selectMethod(iv_age_6_9, iv_age_6_9_black, tv_age_6_9, li_age_6_9);
                unselectMethod(iv_age_3_5, iv_age_3_5_black, tv_3_5, li_age_3_5);
                unselectMethod(iv_age_10_12, iv_age_10_12_black, tv_age_10_12, li_age_10_12);
                unselectMethod(iv_age_all, iv_age_all_black, tv_all, li_age_all);

                /*  iv_age_6_9.visibility=View.GONE
                  iv_age_6_9_black.visibility=View.VISIBLE
                  tv_age_6_9.setTextColor(Color.parseColor("#000000"))
                  li_age_6_9.setBackgroundColor(Color.parseColor("#FFFFFF"))

                   iv_age_3_5.visibility=View.VISIBLE
                   iv_age_3_5_black.visibility=View.GONE
                   tv_3_5.setTextColor(Color.parseColor("#FFFFFF"))
                   li_age_3_5.setBackgroundColor(android.R.color.transparent)

                   iv_age_10_12.visibility=View.VISIBLE
                   iv_age_10_12_black.visibility=View.GONE
                   tv_age_10_12.setTextColor(Color.parseColor("#FFFFFF"))
                   li_age_10_12.setBackgroundColor(android.R.color.transparent)


                   iv_age_all.visibility=View.VISIBLE
                   iv_age_all_black.visibility=View.GONE
                   tv_all.setTextColor(Color.parseColor("#FFFFFF"))
                   li_age_all.setBackgroundColor(android.R.color.transparent)
   */
                clickage4 = false;
                clickage3 = false;
                clickage1 = false;
                clickage2 = true


            } else {
                unselectMethod(iv_age_6_9, iv_age_6_9_black, tv_age_6_9, li_age_6_9);
                /* iv_age_6_9.visibility=View.VISIBLE
                 iv_age_6_9_black.visibility=View.GONE
                 tv_age_6_9.setTextColor(Color.parseColor("#FFFFFF"))
                 li_age_6_9.setBackgroundColor(android.R.color.transparent)*/
                clickage2 = false;
            }
        }


        li_age_10_12.setOnClickListener {

            if (clickage3 == false) {
                age = "10-12"
                selectMethod(iv_age_10_12, iv_age_10_12_black, tv_age_10_12, li_age_10_12);
                unselectMethod(iv_age_3_5, iv_age_3_5_black, tv_3_5, li_age_3_5);
                unselectMethod(iv_age_6_9, iv_age_6_9_black, tv_age_6_9, li_age_6_9);
                unselectMethod(iv_age_all, iv_age_all_black, tv_all, li_age_all);

                /*iv_age_10_12.visibility=View.GONE
                iv_age_10_12_black.visibility=View.VISIBLE
                tv_age_10_12.setTextColor(Color.parseColor("#000000"))
                li_age_10_12.setBackgroundColor(Color.parseColor("#FFFFFF"))

                iv_age_3_5.visibility=View.VISIBLE
                iv_age_3_5_black.visibility=View.GONE
                tv_3_5.setTextColor(Color.parseColor("#FFFFFF"))
                li_age_3_5.setBackgroundColor(android.R.color.transparent)

                iv_age_6_9.visibility=View.VISIBLE
                iv_age_6_9_black.visibility=View.GONE
                tv_age_6_9.setTextColor(Color.parseColor("#FFFFFF"))
                li_age_6_9.setBackgroundColor(android.R.color.transparent)

                iv_age_all.visibility=View.VISIBLE
                iv_age_all_black.visibility=View.GONE
                tv_all.setTextColor(Color.parseColor("#FFFFFF"))
                li_age_all.setBackgroundColor(android.R.color.transparent)*/
                clickage4 = false;

                clickage2 = false;
                clickage1 = false;
                clickage3 = true


            } else {
                unselectMethod(iv_age_10_12, iv_age_10_12_black, tv_age_10_12, li_age_10_12);
                /*iv_age_10_12.visibility=View.VISIBLE
                iv_age_10_12_black.visibility=View.GONE
                tv_age_10_12.setTextColor(Color.parseColor("#FFFFFF"))
                li_age_10_12.setBackgroundColor(android.R.color.transparent)*/
                clickage3 = false;
            }
        }



        li_age_all.setOnClickListener {

            if (clickage4 == false) {
                age = "ALL"
                selectMethod(iv_age_all, iv_age_all_black, tv_all, li_age_all);
                unselectMethod(iv_age_3_5, iv_age_3_5_black, tv_3_5, li_age_3_5);
                unselectMethod(iv_age_6_9, iv_age_6_9_black, tv_age_6_9, li_age_6_9);
                unselectMethod(iv_age_10_12, iv_age_10_12_black, tv_age_10_12, li_age_10_12);

                /*  iv_age_all.visibility=View.GONE
                  iv_age_all_black.visibility=View.VISIBLE
                  tv_all.setTextColor(Color.parseColor("#000000"))
                  li_age_all.setBackgroundColor(Color.parseColor("#FFFFFF"))

                  iv_age_10_12.visibility=View.VISIBLE
                  iv_age_10_12_black.visibility=View.GONE
                  tv_age_10_12.setTextColor(Color.parseColor("#FFFFFF"))
                  li_age_10_12.setBackgroundColor(android.R.color.transparent)

                  iv_age_3_5.visibility=View.VISIBLE
                  iv_age_3_5_black.visibility=View.GONE
                  tv_3_5.setTextColor(Color.parseColor("#FFFFFF"))
                  li_age_3_5.setBackgroundColor(android.R.color.transparent)

                  iv_age_6_9.visibility=View.VISIBLE
                  iv_age_6_9_black.visibility=View.GONE
                  tv_age_6_9.setTextColor(Color.parseColor("#FFFFFF"))
                  li_age_6_9.setBackgroundColor(android.R.color.transparent)
  */
                clickage3 = false;
                clickage2 = false;
                clickage1 = false;
                clickage4 = true

            } else {

                unselectMethod(iv_age_all, iv_age_all_black, tv_all, li_age_all);
                /* iv_age_all.visibility=View.VISIBLE
                 iv_age_all_black.visibility=View.GONE
                 tv_all.setTextColor(Color.parseColor("#FFFFFF"))
                 li_age_all.setBackgroundColor(android.R.color.transparent)*/
                clickage4 = false;
            }
        }









        li_self_category.setOnClickListener {
            if (click == false) {
                category = "SLF"

                /*  selectCategory(iv_self_category,tv_self,li_self_category);
                  unselectedCategory(iv_communtiy,tv_community,li_community);
                  unselectedCategory(iv_social,tv_social_skills,li_social_skills);
                  unselectedCategory(iv_world,tv_world,li_world);
                  unselectedCategory(iv_problem,tv_problem,li_problem);
                  unselectedCategory(iv_all,tv_all_tv,li_all);
  */

                iv_self_category.setImageResource(R.drawable.category_self_black);
                tv_self.setTextColor(Color.parseColor("#000000"))
                li_self_category.setBackgroundColor(Color.parseColor("#BEBDFF"))

                iv_communtiy.setImageResource(R.drawable.comunity_icon);
                tv_community.setTextColor(Color.parseColor("#F79F39"))
                li_community.setBackgroundColor(android.R.color.transparent)

                iv_social.setImageResource(R.drawable.social_skills);
                tv_social_skills.setTextColor(Color.parseColor("#FCFF66"))
                li_social_skills.setBackgroundColor(android.R.color.transparent)

                iv_world.setImageResource(R.drawable.category_world);
                tv_world.setTextColor(Color.parseColor("#87FFD3"))
                li_world.setBackgroundColor(android.R.color.transparent)


                iv_problem.setImageResource(R.drawable.category_problem_solving);
                tv_problem.setTextColor(Color.parseColor("#FF3E3E"))
                li_problem.setBackgroundColor(android.R.color.transparent)

                iv_all.setImageResource(R.drawable.category_all_white);
                tv_all_tv.setTextColor(Color.parseColor("#FFFFFF"))
                li_all.setBackgroundColor(android.R.color.transparent)

                click = true
                click2 = false
                click3 = false
                click4 = false
                click5 = false
                click6 = false
            } else {
                //  unselectedCategory(iv_self_category,tv_self,li_self_category);
                iv_self_category.setImageResource(R.drawable.category_self);
                tv_self.setTextColor(Color.parseColor("#BEBDFF"))
                li_self_category.setBackgroundColor(android.R.color.transparent)
                click = false
            }

        }


        li_community.setOnClickListener {
            if (click2 == false) {
                category = "COM"
                /*selectCategory(iv_communtiy,tv_community,li_community);
                unselectedCategory(iv_self_category,tv_self,li_self_category);
                unselectedCategory(iv_social,tv_social_skills,li_social_skills);
                unselectedCategory(iv_world,tv_world,li_world);
                unselectedCategory(iv_problem,tv_problem,li_problem);
                unselectedCategory(iv_all,tv_all_tv,li_all);*/


                iv_communtiy.setImageResource(R.drawable.comunity_icon_black);
                tv_community.setTextColor(Color.parseColor("#000000"))
                li_community.setBackgroundColor(Color.parseColor("#F79F39"))


                iv_self_category.setImageResource(R.drawable.category_self);
                tv_self.setTextColor(Color.parseColor("#BEBDFF"))
                li_self_category.setBackgroundColor(android.R.color.transparent)


                iv_social.setImageResource(R.drawable.social_skills);
                tv_social_skills.setTextColor(Color.parseColor("#FCFF66"))
                li_social_skills.setBackgroundColor(android.R.color.transparent)

                iv_world.setImageResource(R.drawable.category_world);
                tv_world.setTextColor(Color.parseColor("#87FFD3"))
                li_world.setBackgroundColor(android.R.color.transparent)


                iv_problem.setImageResource(R.drawable.category_problem_solving);
                tv_problem.setTextColor(Color.parseColor("#FF3E3E"))
                li_problem.setBackgroundColor(android.R.color.transparent)

                iv_all.setImageResource(R.drawable.category_all_white);
                tv_all_tv.setTextColor(Color.parseColor("#FFFFFF"))
                li_all.setBackgroundColor(android.R.color.transparent)
                click2 = true
                click = false
                click3 = false
                click4 = false
                click5 = false
                click6 = false
            } else {
                // unselectedCategory(iv_communtiy,tv_community,li_community);
                iv_communtiy.setImageResource(R.drawable.comunity_icon);
                tv_community.setTextColor(Color.parseColor("#F79F39"))
                li_community.setBackgroundColor(android.R.color.transparent)
                click2 = false
            }

        }


        li_social_skills.setOnClickListener {
            if (click3 == false) {
                category = "SCL"
                /*      selectCategory(iv_communtiy,tv_community,li_community);
                      unselectedCategory(iv_self_category,tv_self,li_self_category);
                      unselectedCategory(iv_social,tv_social_skills,li_social_skills);
                      unselectedCategory(iv_world,tv_world,li_world);
                      unselectedCategory(iv_problem,tv_problem,li_problem);
                      unselectedCategory(iv_all,tv_all_tv,li_all);*/

                iv_social.setImageResource(R.drawable.social_skills_normal_black);
                tv_social_skills.setTextColor(Color.parseColor("#000000"))
                li_social_skills.setBackgroundColor(Color.parseColor("#FCFF66"))

                iv_communtiy.setImageResource(R.drawable.comunity_icon);
                tv_community.setTextColor(Color.parseColor("#F79F39"))
                li_community.setBackgroundColor(android.R.color.transparent)

                iv_self_category.setImageResource(R.drawable.category_self);
                tv_self.setTextColor(Color.parseColor("#BEBDFF"))
                li_self_category.setBackgroundColor(android.R.color.transparent)


                iv_world.setImageResource(R.drawable.category_world);
                tv_world.setTextColor(Color.parseColor("#87FFD3"))
                li_world.setBackgroundColor(android.R.color.transparent)


                iv_problem.setImageResource(R.drawable.category_problem_solving);
                tv_problem.setTextColor(Color.parseColor("#FF3E3E"))
                li_problem.setBackgroundColor(android.R.color.transparent)

                iv_all.setImageResource(R.drawable.category_all_white);
                tv_all_tv.setTextColor(Color.parseColor("#FFFFFF"))
                li_all.setBackgroundColor(android.R.color.transparent)


                click3 = true
                click2 = false
                click = false
                click4 = false
                click5 = false
                click6 = false
            } else {
                //  unselectedCategory(iv_social,tv_social_skills,li_social_skills);
                iv_social.setImageResource(R.drawable.social_skills);
                tv_social_skills.setTextColor(Color.parseColor("#FCFF66"))
                li_social_skills.setBackgroundColor(android.R.color.transparent)
                click3 = false
            }

        }



        li_world.setOnClickListener {
            if (click4 == false) {
                category = "WLD"
                /*selectCategory(iv_world,tv_world,li_world);
                unselectedCategory(iv_self_category,tv_self,li_self_category);
                unselectedCategory(iv_social,tv_social_skills,li_social_skills);
                unselectedCategory(iv_communtiy,tv_community,li_community);
                unselectedCategory(iv_problem,tv_problem,li_problem);
                unselectedCategory(iv_all,tv_all_tv,li_all);
*/
                iv_world.setImageResource(R.drawable.category_world_black);
                tv_world.setTextColor(Color.parseColor("#000000"))
                li_world.setBackgroundColor(Color.parseColor("#87FFD3"))


                iv_social.setImageResource(R.drawable.social_skills);
                tv_social_skills.setTextColor(Color.parseColor("#FCFF66"))
                li_social_skills.setBackgroundColor(android.R.color.transparent)


                iv_communtiy.setImageResource(R.drawable.comunity_icon);
                tv_community.setTextColor(Color.parseColor("#F79F39"))
                li_community.setBackgroundColor(android.R.color.transparent)

                iv_self_category.setImageResource(R.drawable.category_self);
                tv_self.setTextColor(Color.parseColor("#BEBDFF"))
                li_self_category.setBackgroundColor(android.R.color.transparent)




                iv_problem.setImageResource(R.drawable.category_problem_solving);
                tv_problem.setTextColor(Color.parseColor("#FF3E3E"))
                li_problem.setBackgroundColor(android.R.color.transparent)

                iv_all.setImageResource(R.drawable.category_all_white);
                tv_all_tv.setTextColor(Color.parseColor("#FFFFFF"))
                li_all.setBackgroundColor(android.R.color.transparent)




                click4 = true
                click3 = false
                click2 = false
                click = false
                click5 = false
                click6 = false
            } else {
                //  unselectedCategory(iv_world,tv_world,li_world);
                iv_world.setImageResource(R.drawable.category_world);
                tv_world.setTextColor(Color.parseColor("#87FFD3"))
                li_world.setBackgroundColor(android.R.color.transparent)
                click4 = false
            }

        }


        li_problem.setOnClickListener {
            if (click5 == false) {
                category = "PRB"
                /*   selectCategory(iv_problem,tv_problem,li_problem);
                   unselectedCategory(iv_self_category,tv_self,li_self_category);
                   unselectedCategory(iv_social,tv_social_skills,li_social_skills);
                   unselectedCategory(iv_communtiy,tv_community,li_community);
                   unselectedCategory(iv_world,tv_world,li_world);
                   unselectedCategory(iv_all,tv_all_tv,li_all);
   */

                iv_problem.setImageResource(R.drawable.category_problem_solving_black);
                tv_problem.setTextColor(Color.parseColor("#000000"))
                li_problem.setBackgroundColor(Color.parseColor("#FF3E3E"))


                iv_world.setImageResource(R.drawable.category_world);
                tv_world.setTextColor(Color.parseColor("#87FFD3"))
                li_world.setBackgroundColor(android.R.color.transparent)


                iv_social.setImageResource(R.drawable.social_skills);
                tv_social_skills.setTextColor(Color.parseColor("#FCFF66"))
                li_social_skills.setBackgroundColor(android.R.color.transparent)


                iv_communtiy.setImageResource(R.drawable.comunity_icon);
                tv_community.setTextColor(Color.parseColor("#F79F39"))
                li_community.setBackgroundColor(android.R.color.transparent)

                iv_self_category.setImageResource(R.drawable.category_self);
                tv_self.setTextColor(Color.parseColor("#BEBDFF"))
                li_self_category.setBackgroundColor(android.R.color.transparent)



                iv_all.setImageResource(R.drawable.category_all_white);
                tv_all_tv.setTextColor(Color.parseColor("#FFFFFF"))
                li_all.setBackgroundColor(android.R.color.transparent)
                click4 = false
                click3 = false
                click2 = false
                click = false
                click5 = true
                click6 = false
            } else {
                // unselectedCategory(iv_problem,tv_problem,li_problem);
                iv_problem.setImageResource(R.drawable.category_problem_solving);
                tv_problem.setTextColor(Color.parseColor("#FF3E3E"))
                li_problem.setBackgroundColor(android.R.color.transparent)
                click5 = false
            }

        }

        li_all.setOnClickListener {
            if (click6 == false) {
                category = "ALL"
                /*       selectCategory(iv_all,tv_all_tv,li_all);
                       unselectedCategory(iv_self_category,tv_self,li_self_category);
                       unselectedCategory(iv_social,tv_social_skills,li_social_skills);
                       unselectedCategory(iv_communtiy,tv_community,li_community);
                       unselectedCategory(iv_world,tv_world,li_world);
                       unselectedCategory(iv_problem,tv_problem,li_all);*/


                iv_all.setImageResource(R.drawable.category_all_black);
                tv_all_tv.setTextColor(Color.parseColor("#000000"))
                li_all.setBackgroundColor(Color.parseColor("#FFFFFF"))


                iv_problem.setImageResource(R.drawable.category_problem_solving);
                tv_problem.setTextColor(Color.parseColor("#FF3E3E"))
                li_problem.setBackgroundColor(android.R.color.transparent)


                iv_world.setImageResource(R.drawable.category_world);
                tv_world.setTextColor(Color.parseColor("#87FFD3"))
                li_world.setBackgroundColor(android.R.color.transparent)


                iv_social.setImageResource(R.drawable.social_skills);
                tv_social_skills.setTextColor(Color.parseColor("#FCFF66"))
                li_social_skills.setBackgroundColor(android.R.color.transparent)


                iv_communtiy.setImageResource(R.drawable.comunity_icon);
                tv_community.setTextColor(Color.parseColor("#F79F39"))
                li_community.setBackgroundColor(android.R.color.transparent)

                iv_self_category.setImageResource(R.drawable.category_self);
                tv_self.setTextColor(Color.parseColor("#BEBDFF"))
                li_self_category.setBackgroundColor(android.R.color.transparent)



                click4 = false
                click3 = false
                click2 = false
                click = false
                click5 = false
                click6 = true

            } else {
                // unselectedCategory(iv_all,tv_all_tv,li_all);
                iv_all.setImageResource(R.drawable.category_all_white);
                tv_all_tv.setTextColor(Color.parseColor("#FFFFFF"))
                li_all.setBackgroundColor(android.R.color.transparent)

                click = false
            }

        }
    }

/*    @SuppressLint("ResourceAsColor")
    private fun unselectedCategory(image: ImageView?, text: TextView?, selectionBox: LinearLayout?) {
        image!!.setImageResource(R.drawable.comunity_icon);
        text!!.setTextColor(Color.parseColor("#F79F39"))
        selectionBox!!.setBackgroundColor(android.R.color.transparent)
    }

    private fun selectCategory(CategoryImage: ImageView?, text: TextView?, selectionBox: LinearLayout?) {
        CategoryImage!!.setImageResource(R.drawable.category_self_black);
        text!!.setTextColor(Color.parseColor("#000000"))
        selectionBox!!.setBackgroundColor(Color.parseColor("#BEBDFF"))
    }*/

    @SuppressLint("ResourceAsColor")
    private fun unselectMethod(unselectImage: ImageView?, selectImage: ImageView?, text: TextView?, selectionbox: LinearLayout?) {
        unselectImage!!.visibility = View.VISIBLE
        selectImage!!.visibility = View.GONE
        text!!.setTextColor(Color.parseColor("#FFFFFF"))
        selectionbox!!.setBackgroundColor(android.R.color.transparent)
        clickage1 = false;
        clickage2 = true
        clickage3 = false;
        clickage4 = false;
    }

    private fun selectMethod(unselectImage: ImageView?, selectImage: ImageView?, text: TextView?, selectionbox: LinearLayout?) {
        unselectImage!!.visibility = View.GONE
        selectImage!!.visibility = View.VISIBLE
        text!!.setTextColor(Color.parseColor("#000000"))
        selectionbox!!.setBackgroundColor(Color.parseColor("#FFFFFF"))

        clickage1 = false;
        clickage2 = true
        clickage3 = false;
        clickage4 = false;


    }

    private fun getActivitiesList() {
        mViewListModel?.response?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                    activitiesList.clear()
                    it.data?.let { it1 -> activitiesList.addAll(it1) }
                    fame_lay.visibility = View.GONE
                    val gson = Gson()
                    val checkedSer = gson.toJson(activitiesList)
                    PreferenceClass.setStringPreference(activity, Constants.ACTIVIYLIST, checkedSer)

                    var intent: Intent = Intent(activity, ActivityMainNew::class.java)
                    startActivity(intent)
                    activity?.finish()

                }


            }
        })

        mViewListModel?.apiError?.observe(this, Observer {
            showMessage(it.toString())
        })

        mViewListModel?.isLoading?.observe(this, Observer {
            if (it == true) {
                fame_lay.visibility = View.VISIBLE
            } else {
                fame_lay.visibility = View.GONE
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


    fun attachObservers() {

        mDeviceViewModel?.response?.observe(this, Observer {
            it?.let {

                if(it.status==1)
                {
                    Log.d("Response"," "+it);
                    PreferenceClass.setStringPreference(activity, Constants.USER_ID,it.userId)
                    PreferenceClass.setStringPreference(activity, Constants.USER_TYPE,it.userType)
                    PreferenceClass.setStringPreference(activity,Constants.LOGIN_STATUS,it.loginStatus)

                }
                else
                {

                }


            }
        })
        mViewModel?.response?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                    myDataset.clear()
                    it.data?.let { it1 -> myDataset.addAll(it1) }
                    var cat: CategoriesTable? = null
                    for (j in 0..myDataset.size - 1) {
                        selection.add(false)


                    }

                    /*  viewAdapter = SelectionCategoryAdapter(myDataset)


                      rv_categoriesList.layoutManager = GridLayoutManager(this.activity, 2)
                      rv_categoriesList.adapter = viewAdapter
                      viewAdapter.notifyDataSetChanged()*/
                    InsertTask(myDataset, activity!!).execute()
                }


            }
        })

        mViewModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mViewModel?.isLoading?.observe(this, Observer {
            if (it == true) {
                fame_lay.visibility = View.VISIBLE
            } else {
                fame_lay.visibility = View.GONE
            }
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
    internal constructor(val myDataset: MutableList<DataItem>, val context: Context) : AsyncTask<Void, Void, List<CategoriesTable>>() {

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs: Void): List<CategoriesTable>? {
            for (j in 0..myDataset.size - 1) {

                //adding to database
                var db = DatabaseClient.getInstance(context).appDatabase.taskDao
                if (db.getAll().size != myDataset.size) {
                    if (db.getParticulorAll(myDataset.get(j).catValue.toString()).size != 0) {

                        if (myDataset.get(j).catName.toString().equals("ALL")) {
                            db.updateCatTable(1, myDataset.get(j).catValue.toString(), myDataset.get(j).catName.toString(),
                                    myDataset.get(j).catColor.toString(),myDataset.get(j).icon.toString())
                        } else {
                            db.updateCatTable(0, myDataset.get(j).catValue.toString(), myDataset.get(j).catName.toString(),
                                    myDataset.get(j).catColor.toString(),myDataset.get(j).icon.toString())
                        }

                    } else {

                        if (myDataset.get(j).catName.toString().equals("ALL")) {
                            var cat: CategoriesTable = CategoriesTable(myDataset.get(j).catName.toString(),
                                    myDataset.get(j).catColor.toString()
                                    , myDataset.get(j).catValue.toString(), myDataset.get(j).icon.toString(), 1)
                            db.insert(cat)
                        } else {
                            var cat: CategoriesTable = CategoriesTable(myDataset.get(j).catName.toString(),
                                    myDataset.get(j).catColor.toString()
                                    , myDataset.get(j).catValue.toString(), myDataset.get(j).icon.toString(), 0)
                            db.insert(cat)
                        }

                    }

                } else {
                    if (db.getParticulorAll(myDataset.get(j).catValue.toString()).size != 0) {

                        if (myDataset.get(j).catName.toString().equals("ALL")) {
                            db.updateCatTable(1, myDataset.get(j).catValue.toString(), myDataset.get(j).catName.toString(),
                                    myDataset.get(j).catColor.toString(),myDataset.get(j).icon.toString())
                        } else {
                            db.updateCatTable(0, myDataset.get(j).catValue.toString(), myDataset.get(j).catName.toString(),
                                    myDataset.get(j).catColor.toString(),myDataset.get(j).icon.toString())
                        }

                    } else {
                        if (myDataset.get(j).catName.toString().equals("ALL")) {
                            var cat: CategoriesTable = CategoriesTable(myDataset.get(j).catName.toString(),
                                    myDataset.get(j).catColor.toString()
                                    , myDataset.get(j).catValue.toString(), myDataset.get(j).icon.toString(), 1)
                            db.insert(cat)
                        } else {
                            var cat: CategoriesTable = CategoriesTable(myDataset.get(j).catName.toString(),
                                    myDataset.get(j).catColor.toString()
                                    , myDataset.get(j).catValue.toString(), myDataset.get(j).icon.toString(), 0)
                            db.insert(cat)
                        }
                    }
                }


            }





            return DatabaseClient.getInstance(context).appDatabase
                    .taskDao
                    .getAll()
        }

        // onPostExecute runs on main thread
        override fun onPostExecute(list: List<CategoriesTable>?) {
            if (list!!.size != 0) {
                viewAdapter = SelectionCategoryAdapter(list, updateValues)


                rv_categoriesList.layoutManager = GridLayoutManager(context, 2)
                rv_categoriesList.adapter = viewAdapter
                viewAdapter.notifyDataSetChanged()
            }
        }

    }

    //===================Retrieve Categories==============================//
    inner class RetrieveTask// only retain a weak reference to the activity
    internal constructor(val context: Context, val mViewModel: SelectionViewModel) : AsyncTask<Void, Void, List<CategoriesTable>>() {


        override fun doInBackground(vararg voids: Void): List<CategoriesTable> {
            return DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .getAll()
        }

        override fun onPostExecute(tasks: List<CategoriesTable>) {
            super.onPostExecute(tasks)



            if (tasks.size == 0) {
                mViewModel!!.authenticate()
            } else {
                /*var dataItem = DataItem()
                for (i in 0..tasks.size - 1) {

                    if(tasks.get(i).selectedCat==1)
                    {
                        dataItem = DataItem(tasks.get(i).catName, tasks.get(i).icon, tasks.get(i).catValue, tasks.get(i).catColor, true)
                    }
                    else
                    {
                        dataItem = DataItem(tasks.get(i).catName, tasks.get(i).icon, tasks.get(i).catValue, tasks.get(i).catColor, false)
                    }

                    myDataset.add(dataItem)
                }*/


                viewAdapter = SelectionCategoryAdapter(tasks, updateValues)
                rv_categoriesList.layoutManager = GridLayoutManager(context, 2)
                rv_categoriesList.adapter = viewAdapter
                viewAdapter.notifyDataSetChanged()

            }

        }


    }


    val updateValues: () -> Unit = {

        RetrieveTask(activity!!, mViewModel!!).execute()
    }

}



