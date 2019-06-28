package com.app.lifegames.ui.login.apiHit

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.login.LoginResponse
import com.app.lifegames.data.login.socialLogin.SocialLoginResponse
import com.app.lifegames.data.markAsFavResponse.MarkAsFavResponse
import com.app.lifegames.data.newActivityList.NewActivitiesListResponse
import com.app.lifegames.data.payment.GetTokenResponse
import com.app.lifegames.ui.selectAge.apiHit.ActivityListRespository
import kotlinx.android.synthetic.main.include_login.*
import okhttp3.ResponseBody

class LoginViewModel : MyViewModel() {
    var response = MutableLiveData<LoginResponse>()

    fun authenticate(email: String, password: String, deviceid:String) {
        Log.d("Email and pasword"," "+email+" "+password);
        isLoading.value = true
        LoginRespository.createAccount({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,email,password,deviceid)
    }
    var socialResponse = MutableLiveData<SocialLoginResponse>()
    fun authenticateSocial(type: String, socialID: String, token: String) {
        isLoading.value = true
        LoginRespository.socialLogin({
            socialResponse.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,type,socialID,token)
    }


    var tokenResponse = MutableLiveData<GetTokenResponse>()
    fun getToken(url: String) {
        isLoading.value = true
        LoginRespository.getToken({
            tokenResponse.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,url)
    }
}