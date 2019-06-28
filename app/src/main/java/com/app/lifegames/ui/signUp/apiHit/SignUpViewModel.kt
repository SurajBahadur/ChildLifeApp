package com.app.lifegames.ui.signUp.apiHit

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.login.LoginResponse
import com.app.lifegames.data.signup.SignUpResponse
import com.app.lifegames.ui.login.apiHit.LoginRespository

class SignUpViewModel : MyViewModel() {
    var response = MutableLiveData<SignUpResponse>()

    fun authenticate(name: String,fullName: String,email: String, password: String, deviceid: String, deviceToken: String) {
        Log.d("Email and pasword"," "+email+" "+password);
        isLoading.value = true
        SignUpRespoistory.createAccount({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,name,fullName,email,password,deviceid,deviceToken)
    }


}