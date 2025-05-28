package com.hse.courseworkcompose.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hse.courseworkcompose.data.datasource.advertisement.LocalDataSourceAdvertisement
import com.hse.courseworkcompose.data.datasource.advertisement.RemoteDataSourceAdvertisement
import com.hse.courseworkcompose.data.network.response.AdvertisementResponse
import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import com.hse.courseworkcompose.domain.repository.AdvertisementRepository
import javax.inject.Inject

class AdvertisementRepositoryImpl @Inject constructor(
    private val remoteDataSourceAdvertisement: RemoteDataSourceAdvertisement,
    private val localDataSourceAdvertisement: LocalDataSourceAdvertisement
) : AdvertisementRepository {

    companion object{
        private const val TAG="AdvertisementRepositoryImpl"
    }

    override suspend fun getAdvertisementList(userId:String): Result<List<AdvertisementResponse>> {
        val result = remoteDataSourceAdvertisement.getAdvertisementList(userId)
        if (result.isSuccess) {
            val advertisementList = Gson().fromJson<List<AdvertisementResponse>>(
                result.getOrNull(),
                object : TypeToken<List<AdvertisementResponse>>() {}.type
            )

            return Result.success(advertisementList)
        } else {
            return Result.failure(Exception(result.getOrNull()))
        }
    }

    override suspend fun getAdvertisement(advertisementGlobalId: Long,userId:String): Result<Advertisement> {

        val result =
            remoteDataSourceAdvertisement.getAdvertisementById(globalIdAdv = advertisementGlobalId.toString(), globalIdUser = userId)
        if (result.isSuccess) {
            val advertisementResponse = Gson().fromJson<AdvertisementResponse>(
                result.getOrNull(),
                AdvertisementResponse::class.java
            )
            val advertisement = Advertisement(
                globalId = advertisementResponse.id,
                price = advertisementResponse.price,
                isFavorite = advertisementResponse.isFavorite,
                sellerDiscount = advertisementResponse.sellerDiscount,
                url = advertisementResponse.url.first(),
                brand = advertisementResponse.brand,
                name = advertisementResponse.name,
                description = advertisementResponse.description,
                rate = advertisementResponse.popularity,
                quantityReviews = advertisementResponse.quantityReviews
            )
            return Result.success(advertisement)
        } else {
            return Result.failure(Exception(result.getOrNull()))
        }

    }

    override suspend fun getAdvertisementFavorites(userGlobalId: String): Result<List<AdvertisementShort>> {
        var advertisementShortList = localDataSourceAdvertisement.getAllFavorites()
        val result =
            remoteDataSourceAdvertisement.getAdvertisementListFavorite(userGlobalId = userGlobalId)
        if (result.isSuccess) {
            Log.d(TAG,"result from getAdvFavList: $result")
            val advertisementResponseList = Gson().fromJson<List<AdvertisementResponse>>(
                result.getOrNull()!!,
                object : TypeToken<List<AdvertisementResponse>>() {}.type
            )
            advertisementShortList = mutableListOf<AdvertisementShort>()
            advertisementResponseList.forEach { advertisement->
                advertisementShortList.add(
                    AdvertisementShort(
                        globalId = advertisement.id,
                        price = advertisement.price,
                        isFavorite = advertisement.isFavorite,
                        sellerDiscount = advertisement.sellerDiscount,
                        url = advertisement.url.first(),
                        brand = advertisement.brand,
                        name = advertisement.name
                    )
                )
            }
            localDataSourceAdvertisement.checkFavoriteList(advertisementShortList)
            return Result.success(advertisementShortList)
        } else {
            return Result.success(advertisementShortList)
        }
    }

    override suspend fun saveFavoriteAdvertisement(userGlobalId:String,advertisementShort: AdvertisementShort) {
        advertisementShort.isFavorite=true
        localDataSourceAdvertisement.saveFavoriteAdvertisement(advertisementShort)
        remoteDataSourceAdvertisement.saveFavouriteAdvertisement(
            userGlobalId = userGlobalId,
            advertisementGlobalId = advertisementShort.globalId.toString()
        )
    }

    override suspend fun deleteFavoriteAdvertisement(userGlobalId:String,advertisementShort: AdvertisementShort) {
        advertisementShort.isFavorite=false
        localDataSourceAdvertisement.deleteFavoriteAdvertisement(advertisementShort)
        remoteDataSourceAdvertisement.deleteFavouriteAdvertisement(
            userGlobalId,
            advertisementGlobalId = advertisementShort.globalId.toString()
        )
    }


    override suspend fun getAdvertisementListBySelectionId(selectionId: String): Result<List<AdvertisementShort>> {
        val result = remoteDataSourceAdvertisement.getAdvertisementListBySelectionId(selectionId)
        if (result.isSuccess) {
            val advertisementList = Gson().fromJson<List<AdvertisementResponse>>(
                result.getOrNull(),
                object : TypeToken<List<AdvertisementResponse>>() {}.type
            )
            val advertisementShortList=mutableListOf<AdvertisementShort>()
            advertisementList.forEach {advertisement->
                advertisementShortList.add(
                    AdvertisementShort(
                        globalId = advertisement.id,
                        price = advertisement.price,
                        isFavorite = advertisement.isFavorite,
                        sellerDiscount = advertisement.sellerDiscount,
                        url = advertisement.url.first(),
                        brand = advertisement.brand,
                        name = advertisement.name
                    )
                )
            }

            return Result.success(advertisementShortList)
        } else {
            return Result.failure(Exception(result.getOrNull()))
        }
    }


}