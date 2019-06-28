package com.app.lifegames.ui.payment.apiHit

import android.util.Log
import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.payment.FinalPaymentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PaymentRespository {
    private val webService = ApiHelper.createService()

    fun finalPayment(successHandler: (FinalPaymentResponse) -> Unit,
                     failureHandler: (String) -> Unit,
                     onFailure: (Throwable) -> Unit,
                     userID: String,
                     nonce: String,
                     plane_amount: String,
                     plan_id: String,
                     start_date: String,
                     status: String,age:String,cat:String) {

        webService.finalPayment(userID, nonce,plane_amount,
                plan_id,start_date,status,age,cat ).enqueue(object : Callback<FinalPaymentResponse> {

            override fun onResponse(call: Call<FinalPaymentResponse>?, response: Response<FinalPaymentResponse>?) {
                response?.body()?.let {

                    /*if (it.status==0) {
                        failureHandler(it?.message!!)
                        return
                    }*/
                    successHandler(it)
                }
                if (response?.code() == 200) {

                    response.body()?.let {
                        if (it.status == 0) {
                            failureHandler(it.message.toString())
                        } else {
                            failureHandler(it.message.toString())
                        }

                    }

                } else if (response?.code() == 422) {
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

            override fun onFailure(call: Call<FinalPaymentResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }
}