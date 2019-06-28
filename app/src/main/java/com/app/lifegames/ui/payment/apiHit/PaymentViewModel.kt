package com.app.lifegames.ui.payment.apiHit

import android.arch.lifecycle.MutableLiveData
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.payment.FinalPaymentResponse

class PaymentViewModel : MyViewModel() {
    var response = MutableLiveData<FinalPaymentResponse>()

    fun FinalPaymentResponse(userID: String, nonce: String, plane_amount: String, plan_id: String,start_date:String,
                    status:String,age:String,cat:String) {
        isLoading.value = true
        PaymentRespository.finalPayment({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,userID,nonce,plane_amount,plan_id,start_date,status,age,cat)
    }
}