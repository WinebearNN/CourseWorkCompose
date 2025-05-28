package com.hse.courseworkcompose.domain.useCase

import com.hse.courseworkcompose.data.network.response.AdvertisementResponse
import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.repository.AdvertisementRepository
import com.hse.courseworkcompose.domain.repository.UserRepository
import javax.inject.Inject

class AdvertisementUseCase @Inject constructor(
    private val advertisementRepository: AdvertisementRepository,
    private val userRepository: UserRepository
) {

    private var userId = ""

//    private suspend fun getUserId(): String {
//        if (userId.isBlank()) {
//            userId = userRepository.getUser().getOrThrow().globalId.toString()
//        }
//        return userId
//
//    }

    companion object {
        private const val TAG = "AdvertisementUseCase"
    }

    suspend fun getAdvertisement(advertisementGlobalId: Long,userId:String): Result<Advertisement> {
        return advertisementRepository.getAdvertisement(advertisementGlobalId = advertisementGlobalId, userId = userId)
    }

    suspend fun getAdvertisementFavorites(userGlobalId:String): Result<List<AdvertisementShort>> {
        return advertisementRepository.getAdvertisementFavorites(userGlobalId)
    }

    suspend fun getAdvertisementList(userId:String): Result<List<AdvertisementResponse>> {
        return advertisementRepository.getAdvertisementList(userId)
    }

    suspend fun saveFavoriteAdvertisement(
        advertisementShort: AdvertisementShort,
        userGlobalId:String
    ) {
        return advertisementRepository.saveFavoriteAdvertisement(userGlobalId = userGlobalId, advertisementShort = advertisementShort)
    }

    suspend fun deleteFavoriteAdvertisement(
        advertisementShort: AdvertisementShort,
        userGlobalId:String
    ) {
        return advertisementRepository.deleteFavoriteAdvertisement(userGlobalId, advertisementShort)
    }
}