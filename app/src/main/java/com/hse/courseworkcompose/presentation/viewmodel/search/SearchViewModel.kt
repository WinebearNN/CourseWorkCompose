package com.hse.courseworkcompose.presentation.viewmodel.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.useCase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SearchViewModel"
    }

    private var _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> get() = _loading

    private var _list = MutableStateFlow<List<AdvertisementShort>?>(null)
    val list : StateFlow<List<AdvertisementShort>?> get() = _list




    fun getAdvertisementListBySelectionId(selectionId: String){
        viewModelScope.launch {
            _loading.value=true
            runCatching {
                Log.d(TAG,"Start to getting adv list by selection id: $selectionId")
                searchUseCase.getAdvertisementListBySelectionId(selectionId)
            }.onSuccess { localResult->
                Log.d(TAG,"list was taken successfully: $localResult")
                _list.value=localResult.getOrThrow()
            }.onFailure { e->
                Log.e(TAG,"Failed to get list by selection id: $e")
                _list.value=mutableListOf<AdvertisementShort>()
            }.also {
                _loading.value=false
            }
        }
    }


}