package com.app.lifegames.ui.favouriteActivitiesList.apiHit

import android.arch.lifecycle.MutableLiveData
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.completedActivtites.CompletedActivityResponse
import com.app.lifegames.data.favouriteActivities.FavouriteActivitiesResponse
import com.app.lifegames.ui.selectAge.apiHit.DoneActivtiyRespository

class FavouriteActivityViewModel : MyViewModel() {
    var response = MutableLiveData<FavouriteActivitiesResponse>()

    fun authenticate(userID: String) {

        isLoading.value = true
        FavouriteActivityrespository.getFavActivitiesList({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        }, {
            onFailure.value = it
            isLoading.value = false
        }, userID)
    }
}