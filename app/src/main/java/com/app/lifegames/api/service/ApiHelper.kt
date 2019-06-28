package com.app.lifegames.api.service

import Preferences
import com.app.lifegames.application.Application
import com.app.lifegames.data.Status
import com.app.lifegames.data.login.ErrorResponse
import com.app.lifegames.security.AddHeaderInterceptor
import com.app.lifegames.security.ResponseInterceptorterceptor
import com.google.gson.GsonBuilder
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.getValue
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by android on 3/11/17.
 * *
 */
object ApiHelper {


    private var mRetrofit: Retrofit

    // Creating Retrofit Object
    init {

        val token = "Bearer " + Preferences.prefs?.getValue(Constants.TOKEN, "0")
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request = chain.request()
                    .newBuilder()
//                    .addHeader("Accept","application/json")
                    .addHeader("Authorization", token)

                    .build()
            chain.proceed(request)
        }
        //  val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).client(httpClient.build()).build()

        val gson = GsonBuilder()
                .setLenient()
                .disableHtmlEscaping()
                .create()

        mRetrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
      /*  mRetrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()*/
    }


    // Creating OkHttpclient Object
    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        return OkHttpClient().newBuilder().connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
               // .addInterceptor(ChuckInterceptor(Application.getContext()))
                .addInterceptor(ResponseInterceptorterceptor())
                .addNetworkInterceptor(AddHeaderInterceptor())
                .build()

    }

    //Creating service class for calling the web services
    fun createService(): WebService {
        return mRetrofit.create(WebService::class.java)
    }

    // Handling error messages returned by Apis
    fun handleApiError(body: ResponseBody?): String {
        var errorMsg = Constants.SOMETHING_WENT_WRONG
        try {
            val errorConverter: Converter<ResponseBody, Status> =
                    mRetrofit.responseBodyConverter(Status::class.java, arrayOfNulls(0))
            val error: Status = errorConverter.convert(body)
            errorMsg = error.message
        } catch (e: Exception) {
        }

        return errorMsg
    }

    // Handling error messages returned by Apis
    fun handleAuthenticationError(body: ResponseBody?): String {
        val errorConverter: Converter<ResponseBody, ErrorResponse>
                = mRetrofit.responseBodyConverter(ErrorResponse::class.java, arrayOfNulls(0))
        val errorResponse: ErrorResponse = errorConverter.convert(body)
        var errorMsg =errorResponse.message!!
        val email = errorResponse.errors?.email
        val password =errorResponse.errors?.password
        val role = errorResponse.errors?.role
        val deviceToken =errorResponse.errors?.deviceToken
        val mobileNumber =errorResponse.errors?.mobile

        if (email != null && email.isNotEmpty()) {
            errorMsg = email[0].toString()
        } else if (password != null && password.isNotEmpty()) {
            errorMsg = password[0].toString()
        } else if (role != null && role.isNotEmpty()) {
            errorMsg = role[0].toString()

        } else if (deviceToken != null&&deviceToken.isNotEmpty()) {
            errorMsg = (deviceToken[0].toString())
        }
        else if (mobileNumber != null&&mobileNumber.isNotEmpty()) {
            errorMsg = mobileNumber[0].toString()
        }
        return errorMsg
    }


}