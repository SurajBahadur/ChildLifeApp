package com.app.lifegames.ui.selectAge.apiHit

import android.arch.lifecycle.MutableLiveData
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.markAsFavResponse.MarkAsFavResponse
import com.app.lifegames.data.markAsPlanner.MarkAsPlannerResponse
import com.app.lifegames.data.newActivityList.NewActivitiesListResponse
import com.app.lifegames.data.removeFromFav.RemoveFromFavResponse
import com.app.lifegames.data.removePlannerActivity.RemovePlannerActivityResponse
import com.app.lifegames.data.updateAsLogout.UpdateLogoutResponse

class ActivityListViewModel : MyViewModel() {
      var response = MutableLiveData<NewActivitiesListResponse>()

      fun authenticate(userID: String, services: String, age: String) {

          isLoading.value = true
          ActivityListRespository.getNewActivitiesList({
              response.value = it
              isLoading.value = false
          }, {
              apiError.value = it
              isLoading.value = false
          },{
              onFailure.value = it
              isLoading.value = false
          } ,userID,services,age)
      }


    var responseMarkAsFav = MutableLiveData<MarkAsFavResponse>()

    fun authenticateAddAsFav(userID: String, services: String) {

        isLoading.value = true
        ActivityListRespository.markAsFav({
            responseMarkAsFav.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,userID,services)
    }


    var responseRemoveFromFav = MutableLiveData<RemoveFromFavResponse>()

    fun authenticateRemoveFromFav(userID: String, services: String) {

        isLoading.value = true
        ActivityListRespository.removeFromFav({
            responseRemoveFromFav.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,userID,services)
    }


    var responseMarkAsPlanner = MutableLiveData<MarkAsPlannerResponse>()

    fun authenticateMarkAsPlanner(userID: String, activityID: String, schedule: String, remd: String, finalDate: String) {

        isLoading.value = true
        ActivityListRespository.markAsPlanner({
            responseMarkAsPlanner.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        },{
            onFailure.value = it
            isLoading.value = false
        } ,userID,activityID,schedule,remd,finalDate)
    }


    var responseRemoveActivtiy = MutableLiveData<RemovePlannerActivityResponse>()
    fun authenticateRemove(userID: String,activtyID: String) {

        isLoading.value = true
        ActivityListRespository.removePlannerActivity({
            responseRemoveActivtiy.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        }, {
            onFailure.value = it
            isLoading.value = false
        }, userID,activtyID)
    }

    var responselogout = MutableLiveData<UpdateLogoutResponse>()
    fun authenticateupdateLogout(userID: String) {

        isLoading.value = true
        ActivityListRespository.updateLogoutActivity({
            responselogout.value = it
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