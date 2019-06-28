package com.app.lifegames.ui.activitiesDetail

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.app.lifegames.R
import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.data.reviewedActivity.AddReviewResponse
import com.app.lifegames.database.DatabaseClient
import com.app.lifegames.database.Entities.DoneActivitiesTable
import com.app.lifegames.ui.newActivity.apiHit.SelectedActivityViewModel
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.loadImg
import com.app.lifegames.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.dialog_add_review.*
import kotlinx.android.synthetic.main.fragment_new_activity.*

class CompletedActivityDetail : BaseFragment(), RatingBar.OnRatingBarChangeListener {
    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {

        showCustomDiaolg(rating)
    }

    var userID: String? = null
    var activityType: String? = null
    var activtiyID: String? = null

    var ratingValue:Float=0.0f

    private var mViewModel: SelectedActivityViewModel? = null
    private var mReviewViewModel: AddReviewResponse? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mViewModel = ViewModelProviders.of(this).get(SelectedActivityViewModel::class.java)
        userID = PreferenceClass.getStringPreferences(this.activity, Constants.USER_ID)
        activityType = PreferenceClass.getStringPreferences(this.activity, Constants.ACTIVITY_TYPE)
        activtiyID = PreferenceClass.getStringPreferences(this.activity, Constants.ACTIVIY_ID)




        /*  if (isNetworkAvailable(getView())) {
              mViewModel!!.authenticate(userID!!, activtiyID!!);

          } else {*/
        RetrieveTask(activity!!, mViewModel!!, userID!!, activtiyID!!).execute()

/*        }*/
        getActivtiyDetail()
        tv_rate_activity.setOnRatingBarChangeListener(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_new_activity, container, false)
    }

    private fun showCustomDiaolg(rating: Float) {
        val reviewDialog = Dialog(activity);
        reviewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //reviewDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        reviewDialog.setContentView(R.layout.dialog_add_review);
        reviewDialog.show();

        val tvSubmitBtn = reviewDialog.findViewById(R.id.tv_submit_btn) as TextView
        val et_write_review = reviewDialog.findViewById(R.id.et_write_review) as EditText
        tvSubmitBtn.setOnClickListener {
            if(et_write_review.text.toString()!=null)
            {
                setRating(rating,et_write_review.text.toString())
            }
            else
            {
                setRating(rating,"")
            }

            reviewDialog.dismiss()
        }
    }

    private fun setRating(rating: Float, comment: String) {
        ratingValue=rating
        mViewModel!!.authenticateRateActivity(userID!!, activtiyID!!, rating,comment);
        getRatingResponse()
    }


    private fun markActivtiyAsDone() {
        mViewModel!!.authenticateAddReview(userID!!, activtiyID!!);
        getResponse()
    }

    private fun getRatingResponse() {
        mViewModel?.rateResponse?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    tv_pro_rating_.rating=ratingValue
                }


            }
        })

        mViewModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mViewModel?.isLoading?.observe(this, Observer {

            //  it?.let { showLoading(it) }
        })

        mViewModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })
    }

    private fun getResponse() {
        mViewModel?.reviewResponse?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                }


            }
        })

        mViewModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mViewModel?.isLoading?.observe(this, Observer {
            /* if(it==true)
             {
                 frame_loading.visibility=View.VISIBLE
             }
             else
             {
                 frame_loading.visibility=View.GONE
             }*/
            //  it?.let { showLoading(it) }
        })

        mViewModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })

    }


    private fun getActivtiyDetail() {
        mViewModel?.response?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
//                    tv_age_vale.setText(it.data!!.ageGroup)
//
//
//                    // String to ByteArray
//                    val title = it.data!!.title.toString().toByteArray(Charsets.UTF_8)
//
//                    // ByteArray to String
//                    val titleString = String(title, Charsets.UTF_8)
//                    playText.setText(titleString)
//
//                    playText.setText(it.data!!.title)
//
//
////                    *//*     tv_selected_value.setText(it.data.catName)*//*
//                    it.data!!.catIcon.let { activity?.let { it1 -> iv_selected_value.loadImg(it!!, it1) } }
//
//                    tv_selected_value.setTextColor(Color.parseColor(it.data.catColor))
//                    tv_pro_rating_.rating = it.data.ratings!!.toFloat()
//
//                    var overview = it.data.overview.toString()
//                    // String to ByteArray
//                    val overViewByteArray = overview.toByteArray(Charsets.UTF_8)
//
//                    // ByteArray to String
//                    val overviewDetail = String(overViewByteArray, Charsets.UTF_8)
//
//
//                    tv_detail.setText(overviewDetail)
//
//                    if (it.data.time != null) {
//                        tv_time.setText("TIME: " + it.data.time)
//                    } else {
//                        tv_time_detail.visibility = View.GONE
//                    }
//
//                    if (it.data.materials != null || !TextUtils.isEmpty(it.data.materials)) {
//
//                        // String to ByteArray
//                        val material = it.data.materials.toString().toByteArray(Charsets.UTF_8)
//
//                        // ByteArray to String
//                        val materialDetail = String(material, Charsets.UTF_8)
//                        tv_materials.setText("MATERIALS: " + materialDetail)
//                    } else {
//                        detail_materials.visibility = View.GONE
//                    }
//
//                    if (it.data.warnings != null || !TextUtils.isEmpty(it.data.warnings)) {
//                        // String to ByteArray
//                        val warning = it.data.warnings.toString().toByteArray(Charsets.UTF_8)
//                        // ByteArray to String
//                        val warningDetail = String(warning, Charsets.UTF_8)
//                        tv_warning.setText("WARNINGS: " + warningDetail)
//                    } else {
//                        warning_detial.visibility = View.GONE
//                    }
//
//                    Log.d("Detail", " " + it.data.activityDetails);
//
//
//                    var detail = it.data.activityDetails.toString()
//                    // String to ByteArray
//                    val byteArray = detail.toByteArray(Charsets.UTF_8)
//                    // ByteArray to String
//                    val output = String(byteArray, Charsets.UTF_8)
//
//                    tv_detail_2.setText(output)
//                    // tv_detail_2.maxLines=it.data.activityDetails!!.length
//
//
//                    it.data!!.image.let { activity?.let { it1 -> activtiy_img.loadImg(it!!, it1) } }
                    //InsertTask(it.data, activity!!).execute()


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
                new_frame_loading.visibility = View.VISIBLE
            } else {
                new_frame_loading.visibility = View.GONE
            }
            //  it?.let { showLoading(it) }
        })

        mViewModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })

    }



    private fun setValues(it: List<DoneActivitiesTable>) {


        if (it.get(0).completed.equals("1")) {
            submit_rate.visibility = View.GONE
        } else {
            submit_rate.visibility = View.VISIBLE
        }

        tv_age_vale.setText(it.get(0).age_group)


        // String to ByteArray
        val title = it.get(0).title.toString().toByteArray(Charsets.UTF_8)

        // ByteArray to String
        val titleString = String(title, Charsets.UTF_8)
        playText.setText(titleString)

        playText.setText(it.get(0).title)
        it.get(0).age_group_icon.let { activity?.let { it1 -> all_age.loadImg(it!!, it1) } }

        tv_selected_value.setText(it.get(0).cat_value)
        it.get(0).cat_icon.let { activity?.let { it1 -> iv_selected_value.loadImg(it!!, it1) } }

     //   tv_selected_value.setTextColor(Color.parseColor(it.get(0).cat_color))
        tv_pro_rating_.rating = it.get(0).ratings!!.toFloat()

        var overview = it.get(0).overview.toString()
        // String to ByteArray
        val overViewByteArray = overview.toByteArray(Charsets.UTF_8)

        // ByteArray to String
        val overviewDetail = String(overViewByteArray, Charsets.UTF_8)

        if (overviewDetail == null) {

        } else {

        }

        tv_detail.setText(overviewDetail)

        if (it.get(0).time != null) {
            tv_time.setText("TIME: " +it.get(0).time)
        } else {
            tv_time_detail.visibility = View.GONE
        }

        if (it.get(0).materials.equals("null")||it.get(0).materials == null || TextUtils.isEmpty(it.get(0).materials)) {
            detail_materials.visibility = View.GONE

        } else {

            // String to ByteArray
            val material = it.get(0).materials.toString().toByteArray(Charsets.UTF_8)

            // ByteArray to String
            val materialDetail = String(material, Charsets.UTF_8)
            tv_materials.setText("MATERIALS: " + materialDetail)

        }

        if (it.get(0).warnings.equals("null") || it.get(0).warnings == null || TextUtils.isEmpty(it.get(0).warnings)) {
            warning_detial.visibility = View.GONE
        } else {
            // String to ByteArray
            val warning =it.get(0).warnings.toString().toByteArray(Charsets.UTF_8)
            // ByteArray to String
            val warningDetail = String(warning, Charsets.UTF_8)
            tv_warning.setText("WARNINGS: " + warningDetail)

        }

        Log.d("Detail", " " +it.get(0).activity_details);


        var detail = it.get(0).activity_details.toString()
        // String to ByteArray
        val byteArray = detail.toByteArray(Charsets.UTF_8)
        // ByteArray to String
        val output = String(byteArray, Charsets.UTF_8)

        tv_detail_2.setText(output)
        // tv_detail_2.maxLines=it.data.activityDetails!!.length


        it.get(0)!!.image.let { activity?.let { it1 -> activtiy_img.loadImg(it!!, it1) } }
    }


    //===================Retrieve Categories==============================//
    inner class RetrieveTask// only retain a weak reference to the activity
    internal constructor(val context: Context, val mViewListModel: SelectedActivityViewModel, val userID: String, val id: String) : AsyncTask<Void, Void, List<DoneActivitiesTable>>() {


        override fun doInBackground(vararg voids: Void): List<DoneActivitiesTable> {
            return DatabaseClient
                    .getInstance(context)
                    .appDatabase
                    .taskDao
                    .getPartDoneActvities(id!!)
        }

        override fun onPostExecute(tasks: List<DoneActivitiesTable>) {
            super.onPostExecute(tasks)

            /* if (tasks.size == 0) {
                 if (isNetworkAvailable(getView())) {
                     no_detail.visibility=View.GONE
                     rl_main_lay.visibility=View.VISIBLE
                     mViewListModel!!.authenticate(userID!!, id!!);

                 } else {
                     no_detail.visibility=View.VISIBLE
                     rl_main_lay.visibility=View.GONE
                    Toast.makeText(context,"Please enable internet to get the activity detail.",Toast.LENGTH_SHORT).show()

                 }

             } else {*/
            setValues(tasks)

/*
            }*/

        }


    }
}