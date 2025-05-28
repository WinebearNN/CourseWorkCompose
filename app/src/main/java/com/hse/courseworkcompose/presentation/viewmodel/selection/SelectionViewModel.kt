package com.hse.courseworkcompose.presentation.viewmodel.selection

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse.courseworkcompose.domain.entity.Selection
import com.hse.courseworkcompose.domain.useCase.SelectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SelectionViewModel @Inject constructor(
    private val selectionUseCase: SelectionUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SelectionViewModel"
    }

    private val _userGlobalId = MutableStateFlow<String>("")
    val userGlobalId: StateFlow<String> = _userGlobalId

    private val _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> get() = _loading

    private val _list = MutableStateFlow<List<Selection>?>(null)
    val list: StateFlow<List<Selection>?> get() = _list


    fun loadData(context: Context) {
        viewModelScope.launch {
            _loading.value=true
            runCatching {
                val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                _userGlobalId.value = prefs.getString("userGlobalId", "") ?: ""
            }.also {
                getSelectionList(_userGlobalId.value)
            }
        }
    }

    private fun getSelectionList(userGlobalId:String) {
        viewModelScope.launch {
            runCatching {
                selectionUseCase.getSelectionList(userGlobalId)
            }.onSuccess { localResult ->
                if (localResult.isSuccess) _list.value = localResult.getOrThrow()
            }.onFailure { exception ->
                Log.e(TAG, "Failed to get selection list: $exception")
            }.also {
                _loading.value = false
            }
        }
    }

}