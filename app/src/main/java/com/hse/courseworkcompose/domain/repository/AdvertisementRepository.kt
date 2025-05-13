package com.hse.courseworkcompose.domain.repository

import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.entity.Filter

interface AdvertisementRepository {

    suspend fun getAdvertisement(advertisementGlobalId:Long): Result<Advertisement>

    suspend fun getAdvertisementFavorites(): Result<List<AdvertisementShort>>

    suspend fun saveFavoriteAdvertisement(advertisementShort: AdvertisementShort)

    suspend fun getAdvertisementShortListByFilter(filter:Filter): Result<List<AdvertisementShort>>


}