package com.app.lifegames.ui.newActivity.apiHit

import android.arch.lifecycle.MutableLiveData
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.rateActivity.RateActivityResponse
import com.app.lifegames.data.reviewedActivity.AddReviewResponse
import com.app.lifegames.data.selectedActivity.SelectedActivityResponse

class SelectedActivityViewModel : MyViewModel() {
    var response = MutableLiveData<SelectedActivityResponse>()

    fun authenticate(userID: String, services: String) {

        isLoading.value = true
        SelectedActivtiyRespository.getSelectedServiceDetail({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,userID,services)
    }


    var reviewResponse = MutableLiveData<AddReviewResponse>()

    fun authenticateAddReview(userID: String, services: String) {

        isLoading.value = true
        SelectedActivtiyRespository.addReview({
            reviewResponse.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,userID,services)
    }


    var rateResponse = MutableLiveData<RateActivityResponse>()

    fun authenticateRateActivity(userID: String, activityID: String, rating: Float, comment: String) {

        isLoading.value = true
        SelectedActivtiyRespository.addRating({
            rateResponse.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,userID,activityID,rating,comment)
    }
}