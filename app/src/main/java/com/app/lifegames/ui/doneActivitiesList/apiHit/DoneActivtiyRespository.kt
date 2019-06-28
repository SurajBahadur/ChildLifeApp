package com.app.lifegames.ui.selectAge.apiHit

import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.completedActivtites.CompletedActivityResponse
import com.app.lifegames.data.newActivityList.NewActivitiesListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DoneActivtiyRespository {
    private val webService = ApiHelper.createService()

    fun getDoneActivitiesList(successHandler: (CompletedActivityResponse) -> Unit,
                              failureHandler: (String) -> Unit,
                              onFailure: (Throwable) -> Unit,
                              userID: String)
    {
        webService.getDoneActivities(userID).enqueue(object : Callback<CompletedActivityResponse>
        {
            override fun onResponse(call: Call<CompletedActivityResponse>?, response: Response<CompletedActivityResponse>?) {
                response?.body()?.let {

                     if (it.status==0) {
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

            override fun onFailure(call: Call<CompletedActivityResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}