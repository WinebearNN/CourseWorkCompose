package com.hse.courseworkcompose.domain.repository

import com.hse.courseworkcompose.data.network.response.AdvertisementResponse
import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.AdvertisementShort

interface AdvertisementRepository {

    suspend fun getAdvertisementList(userId:String):Result<List<AdvertisementResponse>>

    suspend fun getAdvertisement(advertisementGlobalId: Long,userId:String): Result<Advertisement>

    suspend fun getAdvertisementFavorites(globalId:String): Result<List<AdvertisementShort>>

    suspend fun saveFavoriteAdvertisement(userGlobalId:String,advertisementShort: AdvertisementShort)
    suspend fun deleteFavoriteAdvertisement(userGlobalId:String,advertisementShort: AdvertisementShort)



    suspend fun getAdvertisementListBySelectionId(selectionId: String): Result<List<AdvertisementShort>>

}