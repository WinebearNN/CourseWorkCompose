package com.hse.courseworkcompose.data.datasource.advertisement

import android.util.Log
import com.hse.courseworkcompose.domain.entity.AdvertisementShort
import io.objectbox.Box
import io.objectbox.BoxStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


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
        Log.d(TAG, "Try to put fav adv")
        advertisementBox.put(advertisementShort)
    }

    suspend fun deleteFavoriteAdvertisement(advertisementShort: AdvertisementShort) = withContext(
        Dispatchers.IO
    ) {
        advertisementBox.remove(advertisementShort)
    }

    suspend fun getAllFavorites(): List<AdvertisementShort> = withContext(
        Dispatchers.IO
    ) {
        advertisementBox.all
    }

    suspend fun checkFavoriteList(list: List<AdvertisementShort>) = withContext(Dispatchers.IO) {
        advertisementBox.removeAll()
        advertisementBox.put(list)
    }
}