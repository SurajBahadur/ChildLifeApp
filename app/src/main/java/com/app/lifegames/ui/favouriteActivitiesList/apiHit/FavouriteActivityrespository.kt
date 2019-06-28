package com.app.lifegames.ui.favouriteActivitiesList.apiHit

import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.completedActivtites.CompletedActivityResponse
import com.app.lifegames.data.favouriteActivities.FavouriteActivitiesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FavouriteActivityrespository  {
    private val webService = ApiHelper.createService()

    fun getFavActivitiesList(successHandler: (FavouriteActivitiesResponse) -> Unit,
                              failureHandler: (String) -> Unit,
                              onFailure: (Throwable) -> Unit,
                              userID: String)
    {
        webService.getFavActivities(userID).enqueue(object : Callback<FavouriteActivitiesResponse>
        {
            override fun onResponse(call: Call<FavouriteActivitiesResponse>?, response: Response<FavouriteActivitiesResponse>?) {
                response?.body()?.let {

                    if (it.status!!.equals("0")) {
                        failureHandler(it?.message!!)
                        return
                    }
                    successHandler(it)
                }
               /* if (response?.code() == 200) {

                    response.body()?.let {
                        if(it.status==0)
                        {
                            failureHandler(it.message.toString())
                        }
                        *//* else
                         {
                             failureHandler(it.message.toString())
                         }*//*

                    }

                }
                else*/ if (response?.code() == 422) {
                    response.errorBody()?.let {
                        val error = ApiHelper.handleAuthenticationError(response.errorBody()!!)
                        failureHandler(error)
                    }

                }
                else if(response?.code() == 200)
                {
                    response?.body()?.let {
                        successHandler(it)
                    }
                }

                else {
                    response?.errorBody()?.let {
                        val error = ApiHelper.handleApiError(response.errorBody()!!)
                        failureHandler(error)
                    }
                }
            }

            override fun onFailure(call: Call<FavouriteActivitiesResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}