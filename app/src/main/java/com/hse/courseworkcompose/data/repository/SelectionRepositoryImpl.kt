package com.hse.courseworkcompose.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hse.courseworkcompose.data.datasource.selection.RemoteDataSourceSelection
import com.hse.courseworkcompose.data.network.response.SelectionResponse
import com.hse.courseworkcompose.domain.entity.Selection
import com.hse.courseworkcompose.domain.repository.SelectionRepository
import javax.inject.Inject

class SelectionRepositoryImpl @Inject constructor(
    private val remoteDataSourceSelection: RemoteDataSourceSelection
) : SelectionRepository {

    companion object {
        private const val TAG = "SelectionRepositoryImpl"
    }

    override suspend fun createSelection(selection: Selection): Result<Selection> {

        val result = remoteDataSourceSelection.createSelection(
            userGlobalId = selection.userGlobalId,
            description = selection.description,
            name = selection.name
        )

        if (result.isSuccess) {
            Log.i(TAG, "Selection is ${result.getOrNull()}")

            return Result.success(selection)
        }
        return Result.failure(Exception(result.getOrNull()))


    }

    override suspend fun getSelectionList(userGlobalId: String): Result<List<Selection>> {
        val result = remoteDataSourceSelection.getSelectionList(userGlobalId)
        if (result.isSuccess) {
            val selectionListResponse = Gson().fromJson<List<SelectionResponse>>(
                result.getOrNull(),
                object : TypeToken<List<SelectionResponse>>() {}.type
            )

            val selectionList = mutableListOf<Selection>()

            selectionListResponse.forEach {
                selectionList.add(
                    Selection(
                        globalId = it.id,
                        userGlobalId = it.owner.globalId,
                        description = it.description,
                        name = it.name,
                    )
                )
            }

            return Result.success(selectionList)
        } else {
            return Result.failure(Exception(result.getOrNull()))
        }
    }

}