package com.app.lifegames.ui.newActivity.apiHit

import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.rateActivity.RateActivityResponse
import com.app.lifegames.data.reviewedActivity.AddReviewResponse
import com.app.lifegames.data.selectedActivity.SelectedActivityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SelectedActivtiyRespository {
    private val webService = ApiHelper.createService()

    fun getSelectedServiceDetail(successHandler: (SelectedActivityResponse) -> Unit,
                             failureHandler: (String) -> Unit,
                             onFailure: (Throwable) -> Unit,
                             userID: String,
                             services: String)
    {
        webService.getServiceDetail(userID,services).enqueue(object : Callback<SelectedActivityResponse>
        {
            override fun onResponse(call: Call<SelectedActivityResponse>?, response: Response<SelectedActivityResponse>?) {
                response?.body()?.let {

                    if (it.status!!.equals("0")) {
                        failureHandler(it?.message!!)
                        return
                    }
                    successHandler(it)
                }
                if (response?.code() == 200) {

                    response.body()?.let {
                        if(it.status==0)
                        {
                            failureHandler(it.message.toString())
                        }
                        /* else
                         {
                             failureHandler(it.message.toString())
                         }*/

                    }

                }
                else if (response?.code() == 422) {
                    response.errorBody()?.let {
                        val error = ApiHelper.handleAuthenticationError(response.errorBody()!!)
                        failureHandler(error)
                    }

                } else {
                    response?.errorBody()?.let {
                        val error = ApiHelper.handleApiError(response.errorBody()!!)
                        failureHandler(error)
                    }
                }
            }

            override fun onFailure(call: Call<SelectedActivityResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }


    fun addReview(successHandler: (AddReviewResponse) -> Unit,
                                 failureHandler: (String) -> Unit,
                                 onFailure: (Throwable) -> Unit,
                                 userID: String,
                                 services: String)
    {
        webService.setActivityAsDone(userID,services).enqueue(object : Callback<AddReviewResponse>
        {
            override fun onResponse(call: Call<AddReviewResponse>?, response: Response<AddReviewResponse>?) {
                response?.body()?.let {

                    if (it.status!!.equals("0")) {
                        failureHandler(it?.message!!)
                        return
                    }
                    successHandler(it)
                }
                if (response?.code() == 422) {
                    response.errorBody()?.let {
                        val error = ApiHelper.handleAuthenticationError(response.errorBody()!!)
                        failureHandler(error)
                    }

                } else {
                    response?.errorBody()?.let {
                        val error = ApiHelper.handleApiError(response.errorBody()!!)
                        failureHandler(error)
                    }
                }
            }

            override fun onFailure(call: Call<AddReviewResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }


    fun addRating(successHandler: (RateActivityResponse) -> Unit,
                  failureHandler: (String) -> Unit,
                  onFailure: (Throwable) -> Unit,
                  userID: String,
                  cat: String, rating: Float, comment: String)
    {
        webService.rateActivity(userID,cat,rating,comment).enqueue(object : Callback<RateActivityResponse>
        {
            override fun onResponse(call: Call<RateActivityResponse>?, response: Response<RateActivityResponse>?) {
                response?.body()?.let {

                    if (it.status!!.equals("0")) {
                        failureHandler(it?.message!!)
                        return
                    }
                    successHandler(it)
                }
                if (response?.code() == 422) {
                    response.errorBody()?.let {
                        val error = ApiHelper.handleAuthenticationError(response.errorBody()!!)
                        failureHandler(error)
                    }

                } else {
                    response?.errorBody()?.let {
                        val error = ApiHelper.handleApiError(response.errorBody()!!)
                        failureHandler(error)
                    }
                }
            }

            override fun onFailure(call: Call<RateActivityResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}