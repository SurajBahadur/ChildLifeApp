package com.app.lifegames.ui.doneActivitiesList.apiHit

import android.arch.lifecycle.MutableLiveData
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.completedActivtites.CompletedActivityResponse
import com.app.lifegames.ui.selectAge.apiHit.DoneActivtiyRespository

class DoneActivityViewModel : MyViewModel() {
      var response = MutableLiveData<CompletedActivityResponse>()

      fun authenticate(userID: String) {

          isLoading.value = true
          DoneActivtiyRespository.getDoneActivitiesList({
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