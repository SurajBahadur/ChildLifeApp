package com.app.lifegames.ui.selectAge.apiHit

import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.selectiondata.SelectionCategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SelectionRespository {
    private val webService = ApiHelper.createService()

    fun getSelectedServiceList(successHandler: (SelectionCategoryResponse) -> Unit,
                               failureHandler: (String) -> Unit,
                               onFailure: (Throwable) -> Unit)
    {
        webService.getCategories().enqueue(object : Callback<SelectionCategoryResponse>
        {
            override fun onResponse(call: Call<SelectionCategoryResponse>?, response: Response<SelectionCategoryResponse>?) {
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

            override fun onFailure(call: Call<SelectionCategoryResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}