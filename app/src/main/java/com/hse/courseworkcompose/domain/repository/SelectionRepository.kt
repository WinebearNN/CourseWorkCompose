package com.hse.courseworkcompose.domain.repository

import com.hse.courseworkcompose.domain.entity.Selection

interface SelectionRepository {


    suspend fun createSelection(selection: Selection): Result<Selection>

    suspend fun getSelectionList(userGlobalId:String): Result<List<Selection>>

}