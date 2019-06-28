package com.app.lifegames.ui.login.apiHit

import android.util.Log
import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.login.LoginResponse
import com.app.lifegames.data.login.socialLogin.SocialLoginResponse
import com.app.lifegames.data.markAsFavResponse.MarkAsFavResponse
import com.app.lifegames.data.newActivityList.NewActivitiesListResponse
import com.app.lifegames.data.payment.GetTokenResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginRespository {
    private val webService = ApiHelper.createService()

    fun createAccount(successHandler: (LoginResponse) -> Unit,
                             failureHandler: (String) -> Unit,
                             onFailure: (Throwable) -> Unit,
                             email: String,
                             password: String,deviceid:String)
    {
        Log.d("Email and pasword"," "+email+" "+password);
        webService.createAccount(email,password,deviceid).enqueue(object : Callback<LoginResponse>
        {

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
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
                        else
                        {
                            failureHandler(it.message.toString())
                        }

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

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }
    fun socialLogin(successHandler: (SocialLoginResponse) -> Unit,
                      failureHandler: (String) -> Unit,
                      onFailure: (Throwable) -> Unit,
                      type: String,
                      socialID: String,token:String)
    {

        webService.socialLogin(type,socialID,token).enqueue(object : Callback<SocialLoginResponse>
        {

            override fun onResponse(call: Call<SocialLoginResponse>?, response: Response<SocialLoginResponse>?) {
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
                        else
                        {
                            failureHandler(it.message.toString())
                        }

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

            override fun onFailure(call: Call<SocialLoginResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }


    fun getToken(successHandler: (GetTokenResponse) -> Unit,
                    failureHandler: (String) -> Unit,
                    onFailure: (Throwable) -> Unit, url:String)
    {

        webService.getToken(url).enqueue(object : Callback<GetTokenResponse>
        {

            override fun onResponse(call: Call<GetTokenResponse>?, response: Response<GetTokenResponse>?) {
                response?.body()?.let {

                    /*if (it.status==0) {
                        failureHandler(it?.message!!)
                        return
                    }*/
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

            override fun onFailure(call: Call<GetTokenResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

    private fun sendPaymentNonceToServer(paymentNonce: String) {

    }
}