package com.hse.courseworkcompose.presentation.viewmodel.selection

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hse.courseworkcompose.domain.entity.Selection
import com.hse.courseworkcompose.domain.useCase.SelectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddSelectionViewModel @Inject constructor(
    private val selectionUseCase: SelectionUseCase
) : ViewModel() {

    private val _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> get() = _loading

    private val _userGlobalId = MutableStateFlow<String>("")
    val userGlobalId: StateFlow<String> = _userGlobalId

    companion object{
        private const val TAG="AddSelectionViewModel"
    }

    fun loadData(context: Context) {
        viewModelScope.launch {
            runCatching {
                val prefs = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                _userGlobalId.value = prefs.getString("userGlobalId", "") ?: ""
            }
        }
    }


    fun createSelection(selection: Selection,navController: NavController) {
        _loading.value = true
        viewModelScope.launch {
            runCatching {
                selectionUseCase.createSelection(selection)
            }.onSuccess { localResult ->
                Log.d(TAG,"selection was successfully created: $localResult")
            }.onFailure { exception ->
                Log.e(TAG, "Error creating new selection: $exception")
            }.also {
                _loading.value=false

                val previousEntry = navController.previousBackStackEntry
                previousEntry?.savedStateHandle?.set("selectionCreated", true)
                navController.popBackStack()
            }
        }
    }


}