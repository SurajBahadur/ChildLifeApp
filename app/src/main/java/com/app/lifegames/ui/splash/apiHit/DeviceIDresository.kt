package com.app.lifegames.ui.splash.apiHit

import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.saveDeviceID.SaveDeviceResponse
import com.app.lifegames.data.scheduleDatesResponse.ScheduleDatesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DeviceIDresository {
    private val webService = ApiHelper.createService()

    fun saveDeviceID(successHandler: (SaveDeviceResponse) -> Unit,
                     failureHandler: (String) -> Unit,
                     onFailure: (Throwable) -> Unit,
                     deviceID: String,deviceToken: String)
    {
        webService.saveDeviceID(deviceID,deviceToken).enqueue(object : Callback<SaveDeviceResponse>
        {
            override fun onResponse(call: Call<SaveDeviceResponse>?, response: Response<SaveDeviceResponse>?) {
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

            override fun onFailure(call: Call<SaveDeviceResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }


    fun getDates(successHandler: (ScheduleDatesResponse) -> Unit,
                     failureHandler: (String) -> Unit,
                     onFailure: (Throwable) -> Unit)
    {
        webService.getDateList().enqueue(object : Callback<ScheduleDatesResponse>
        {
            override fun onResponse(call: Call<ScheduleDatesResponse>?, response: Response<ScheduleDatesResponse>?) {
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

            override fun onFailure(call: Call<ScheduleDatesResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}