package com.app.lifegames.ui.selectAge.apiHit

import android.arch.lifecycle.MutableLiveData
import com.app.lifegames.api.model.MyViewModel
import com.app.lifegames.data.selectiondata.SelectionCategoryResponse

class SelectionViewModel : MyViewModel() {
      var response = MutableLiveData<SelectionCategoryResponse>()

      fun authenticate() {

          isLoading.value = true
          SelectionRespository.getSelectedServiceList({
              response.value = it
              isLoading.value = false
          }, {
              apiError.value = it
              isLoading.value = false
          },{
              onFailure.value = it
              isLoading.value = false
          } )
      }
}