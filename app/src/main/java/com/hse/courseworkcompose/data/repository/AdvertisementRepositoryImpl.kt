package com.hse.courseworkcompose.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hse.courseworkcompose.data.datasource.advertisement.LocalDataSourceAdvertisement
import com.hse.courseworkcompose.data.datasource.advertisement.RemoteDataSourceAdvertisement
import com.hse.courseworkcompose.data.network.response.AdvertisementResponse
import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.entity.Filter
import com.hse.courseworkcompose.domain.repository.AdvertisementRepository
import javax.inject.Inject

class AdvertisementRepositoryImpl @Inject constructor(
    private val remoteDataSourceAdvertisement: RemoteDataSourceAdvertisement,
    private val localDataSourceAdvertisement: LocalDataSourceAdvertisement
): AdvertisementRepository {

    override suspend fun getAdvertisement(advertisementGlobalId: Long): Result<Advertisement> {

        val result = remoteDataSourceAdvertisement.getAdvertisementById(advertisementGlobalId.toString())
        if (result.isSuccess){
            val advertisementResponse = Gson().fromJson<AdvertisementResponse>(
                result.getOrNull(),
                AdvertisementResponse::class.java
            )
            val advertisement= Advertisement(
                globalId = advertisementResponse.globalId,
                price = advertisementResponse.price,
                isFavorite = advertisementResponse.isFavorite,
                sellerDiscount = advertisementResponse.sellerDiscount,
                url = advertisementResponse.url,
                brand = advertisementResponse.brand,
                name = advertisementResponse.name,
                description = advertisementResponse.description,
                rate = advertisementResponse.rate,
                quantityReviews = advertisementResponse.quantityReviews
            )
            return Result.success(advertisement)
        }else{
            return Result.failure(Exception(result.getOrNull()))
        }

    }

    override suspend fun getAdvertisementFavorites(): Result<List<AdvertisementShort>> {
        val advertisementShortList=localDataSourceAdvertisement.getAllFavorites()
        return Result.success(advertisementShortList)
    }

    override suspend fun saveFavoriteAdvertisement(advertisementShort: AdvertisementShort) {
        localDataSourceAdvertisement.saveFavoriteAdvertisement(advertisementShort)
    }

    override suspend fun getAdvertisementShortListByFilter(filter: Filter): Result<List<AdvertisementShort>> {
        val result = remoteDataSourceAdvertisement.getAdvertisementListByFilter(filter)
        if (result.isSuccess){
            val advertisementShortList = Gson().fromJson<List<AdvertisementShort>>(
                result.getOrNull(),
                object : TypeToken<List<AdvertisementShort>>() {}.type
            )

            return Result.success(advertisementShortList)
        }else{
            return Result.failure(Exception(result.getOrNull()))
        }
    }


}