package com.app.lifegames.ui.plannerActivitiesList.apiHit

import android.arch.lifecycle.MutableLiveData
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.favouriteActivities.FavouriteActivitiesResponse
import com.app.lifegames.data.plannerActivities.PlannerActivitiesResponse
import com.app.lifegames.data.removePlannerActivity.RemovePlannerActivityResponse
import com.app.lifegames.ui.favouriteActivitiesList.apiHit.FavouriteActivityrespository

class PlannerActivityViewModel: MyViewModel() {
    var response = MutableLiveData<PlannerActivitiesResponse>()

    fun authenticate(userID: String,cat: String,age: String) {

        isLoading.value = true
        PlannerActivityRespositoy.getPlannerActivitiesList({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        }, {
            onFailure.value = it
            isLoading.value = false
        }, userID,cat,age)
    }


}