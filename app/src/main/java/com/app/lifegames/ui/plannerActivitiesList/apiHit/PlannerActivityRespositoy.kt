package com.app.lifegames.ui.plannerActivitiesList.apiHit

import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.favouriteActivities.FavouriteActivitiesResponse
import com.app.lifegames.data.plannerActivities.PlannerActivitiesResponse
import com.app.lifegames.data.removePlannerActivity.RemovePlannerActivityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PlannerActivityRespositoy {
    private val webService = ApiHelper.createService()

    fun getPlannerActivitiesList(successHandler: (PlannerActivitiesResponse) -> Unit,
                             failureHandler: (String) -> Unit,
                             onFailure: (Throwable) -> Unit,
                             userID: String,cat: String,age: String)
    {
        webService.getPlannerActivities(userID).enqueue(object : Callback<PlannerActivitiesResponse>
        {
            override fun onResponse(call: Call<PlannerActivitiesResponse>?, response: Response<PlannerActivitiesResponse>?) {
                response?.body()?.let {

               /*     if (it.status!!.equals("0")) {
                        failureHandler(it?.message!!)
                        return
                    }*/
                    successHandler(it)
                }

                if (response?.code() == 200) {
                    response?.body()?.let {

                        successHandler(it)
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

            override fun onFailure(call: Call<PlannerActivitiesResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }




}