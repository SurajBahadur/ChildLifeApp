package com.app.lifegames.ui.signUp.apiHit

import android.util.Log
import com.app.lifegames.api.service.ApiHelper
import com.app.lifegames.data.login.LoginResponse
import com.app.lifegames.data.signup.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SignUpRespoistory {
    private val webService = ApiHelper.createService()

    fun createAccount(successHandler: (SignUpResponse) -> Unit,
                      failureHandler: (String) -> Unit,
                      onFailure: (Throwable) -> Unit,
                      name: String,
                      fullname: String,
                      email: String,
                      password: String,
                      deviceToken: String,
                      deviceID: String)
    {
        Log.d("Email and pasword"," "+email+" "+password);
        webService.signUp(name,fullname,email,password,deviceToken,deviceID).enqueue(object : Callback<SignUpResponse>
        {

            override fun onResponse(call: Call<SignUpResponse>?, response: Response<SignUpResponse>?) {
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

            override fun onFailure(call: Call<SignUpResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}