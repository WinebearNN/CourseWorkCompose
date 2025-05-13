package com.hse.courseworkcompose.data.datasource.advertisement

import com.hse.courseworkcompose.domain.entity.Advertisement
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import io.objectbox.BoxStore
import javax.inject.Inject
import io.objectbox.Box
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LocalDataSourceAdvertisement @Inject constructor(
    boxStore: BoxStore
) {

    private val advertisementBox: Box<AdvertisementShort> =
        boxStore.boxFor(AdvertisementShort::class.java)


    companion object {
        private const val TAG = "LocalDataSourceAdvertisement"
    }

    suspend fun saveFavoriteAdvertisement(advertisementShort: AdvertisementShort) = withContext(
        Dispatchers.IO
    ) {
        advertisementBox.put(advertisementShort)
    }

    suspend fun getAllFavorites(): List<AdvertisementShort> = withContext(
        Dispatchers.IO
    ) {
        advertisementBox.all
    }
}