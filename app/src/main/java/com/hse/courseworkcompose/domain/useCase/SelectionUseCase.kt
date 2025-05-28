package com.hse.courseworkcompose.domain.useCase

import android.util.Log
import com.hse.courseworkcompose.domain.entity.Selection
import com.hse.courseworkcompose.domain.repository.SelectionRepository
import com.hse.courseworkcompose.domain.repository.UserRepository
import javax.inject.Inject

class SelectionUseCase @Inject constructor(
    private val selectionRepository: SelectionRepository,

) {

    companion object {
        private const val TAG = "SelectionUseCase"
    }

    suspend fun getSelectionList(userGlobalId:String): Result<List<Selection>> {

        val result = selectionRepository.getSelectionList(
            userGlobalId = userGlobalId
        )
        Log.i(TAG, "$result")
        return result

    }

    suspend fun createSelection(selection: Selection): Result<Selection> {
        val result = selectionRepository.createSelection(selection)
        Log.i(TAG, "$result")
        return result
    }

}