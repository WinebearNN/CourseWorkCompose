package com.hse.courseworkcompose.domain.useCase

import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.entity.Filter
import com.hse.courseworkcompose.domain.repository.AdvertisementRepository
import javax.inject.Inject

class AdvertisementUseCase @Inject constructor(
    private val advertisementRepository: AdvertisementRepository
) {

    companion object {
        private const val TAG = "AdvertisementUseCase"
    }

    suspend fun getAdvertisement(advertisementGlobalId:Long): Result<Advertisement> {
        return advertisementRepository.getAdvertisement(advertisementGlobalId)
    }

    suspend fun getAdvertisementFavorites(): Result<List<AdvertisementShort>>{
        return advertisementRepository.getAdvertisementFavorites()
    }

    suspend fun saveFavoriteAdvertisement(advertisementShort: AdvertisementShort){
        return advertisementRepository.saveFavoriteAdvertisement(advertisementShort)
    }

    suspend fun getAdvertisementShortListByFilter(filter: Filter): Result<List<AdvertisementShort>>{
        return advertisementRepository.getAdvertisementShortListByFilter(filter)
    }
}