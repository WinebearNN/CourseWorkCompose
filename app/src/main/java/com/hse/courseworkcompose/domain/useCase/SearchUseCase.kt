package com.hse.courseworkcompose.domain.useCase

import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.repository.AdvertisementRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
) {

    companion object {
        private const val TAG = "SearchUseCase"
    }


    suspend fun getAdvertisementListBySelectionId(selectionId: String): Result<List<AdvertisementShort>>{
        return advertisementRepository.getAdvertisementListBySelectionId(selectionId)
    }







}