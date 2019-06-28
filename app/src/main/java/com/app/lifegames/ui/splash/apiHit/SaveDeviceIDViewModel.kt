package com.app.lifegames.ui.splash.apiHit

import android.arch.lifecycle.MutableLiveData
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.saveDeviceID.SaveDeviceResponse
import com.app.lifegames.data.scheduleDatesResponse.ScheduleDatesResponse

class SaveDeviceIDViewModel : MyViewModel() {
    var response = MutableLiveData<SaveDeviceResponse>()

    fun authenticate(deviceID: String,deviceToken: String) {

        isLoading.value = true
        DeviceIDresository.saveDeviceID({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        },deviceID,deviceToken )
    }


    var responseDates = MutableLiveData<ScheduleDatesResponse>()

    fun getDates() {

        isLoading.value = true
        DeviceIDresository.getDates({
            responseDates.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        })
    }
}