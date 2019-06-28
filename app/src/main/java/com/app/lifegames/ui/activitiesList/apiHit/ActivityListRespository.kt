package com.app.lifegames.ui.selectAge.apiHit

import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.markAsFavResponse.MarkAsFavResponse
import com.app.lifegames.data.markAsPlanner.MarkAsPlannerResponse
import com.app.lifegames.data.newActivityList.NewActivitiesListResponse
import com.app.lifegames.data.removeFromFav.RemoveFromFavResponse
import com.app.lifegames.data.removePlannerActivity.RemovePlannerActivityResponse
import com.app.lifegames.data.updateAsLogout.UpdateLogoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ActivityListRespository {
    private val webService = ApiHelper.createService()

    fun getNewActivitiesList(successHandler: (NewActivitiesListResponse) -> Unit,
                             failureHandler: (String) -> Unit,
                             onFailure: (Throwable) -> Unit,
                             userID: String,
                             services: String,
                             age: String)
    {
        webService.getNewActivities(userID,services,age).enqueue(object : Callback<NewActivitiesListResponse>
        {
            override fun onResponse(call: Call<NewActivitiesListResponse>?, response: Response<NewActivitiesListResponse>?) {
                response?.body()?.let {

                     /*if (it.status==0) {
                         failureHandler(it?.message!!)
                         return
                     }*/
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

            override fun onFailure(call: Call<NewActivitiesListResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }
    fun markAsFav(successHandler: (MarkAsFavResponse) -> Unit,
                             failureHandler: (String) -> Unit,
                             onFailure: (Throwable) -> Unit,
                             userID: String,
                             services: String)
    {
        webService.markAsFav(userID,services).enqueue(object : Callback<MarkAsFavResponse>
        {
            override fun onResponse(call: Call<MarkAsFavResponse>?, response: Response<MarkAsFavResponse>?) {
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

            override fun onFailure(call: Call<MarkAsFavResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

    fun removeFromFav(successHandler: (RemoveFromFavResponse) -> Unit,
                  failureHandler: (String) -> Unit,
                  onFailure: (Throwable) -> Unit,
                  userID: String,
                  services: String)
    {
        webService.removeFromFav(userID,services).enqueue(object : Callback<RemoveFromFavResponse>
        {
            override fun onResponse(call: Call<RemoveFromFavResponse>?, response: Response<RemoveFromFavResponse>?) {
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

            override fun onFailure(call: Call<RemoveFromFavResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }
    fun markAsPlanner(successHandler: (MarkAsPlannerResponse) -> Unit,
                      failureHandler: (String) -> Unit,
                      onFailure: (Throwable) -> Unit,
                      userID: String,
                      activityID: String, schedule: String, remd: String, finalDate: String)
    {
        webService.markAsPlanner(userID,activityID,schedule,remd,finalDate).enqueue(object : Callback<MarkAsPlannerResponse>
        {
            override fun onResponse(call: Call<MarkAsPlannerResponse>?, response: Response<MarkAsPlannerResponse>?) {
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

            override fun onFailure(call: Call<MarkAsPlannerResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

    fun removePlannerActivity(successHandler: (RemovePlannerActivityResponse) -> Unit,
                              failureHandler: (String) -> Unit,
                              onFailure: (Throwable) -> Unit,
                              userID: String, activtiyID: String)
    {
        webService.removePlannerActivitiy(userID!!,activtiyID!!).enqueue(object : Callback<RemovePlannerActivityResponse>
        {
            override fun onResponse(call: Call<RemovePlannerActivityResponse>?, response: Response<RemovePlannerActivityResponse>?) {
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

            override fun onFailure(call: Call<RemovePlannerActivityResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }


    fun updateLogoutActivity(successHandler: (UpdateLogoutResponse) -> Unit,
                              failureHandler: (String) -> Unit,
                              onFailure: (Throwable) -> Unit,
                              userID: String)
    {
        webService.updateLoginStatus(userID!!).enqueue(object : Callback<UpdateLogoutResponse>
        {
            override fun onResponse(call: Call<UpdateLogoutResponse>?, response: Response<UpdateLogoutResponse>?) {
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

            override fun onFailure(call: Call<UpdateLogoutResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }
}