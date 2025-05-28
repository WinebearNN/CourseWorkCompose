package com.hse.courseworkcompose.util

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import javax.inject.Singleton
import android.content.Context
import android.util.Log
import com.getkeepsafe.relinker.ReLinker
import com.hse.courseworkcompose.data.datasource.advertisement.LocalDataSourceAdvertisement
import com.hse.courseworkcompose.data.datasource.advertisement.RemoteDataSourceAdvertisement
import com.hse.courseworkcompose.data.datasource.order.RemoteDataSourceOrder
import com.hse.courseworkcompose.data.datasource.selection.RemoteDataSourceSelection
import com.hse.courseworkcompose.data.datasource.user.LocalDataSourceUser
import com.hse.courseworkcompose.data.datasource.user.RemoteDataSourceUser
import com.hse.courseworkcompose.data.network.apiService.ApiServiceAdvertisement
import com.hse.courseworkcompose.data.network.apiService.ApiServiceOrder
import com.hse.courseworkcompose.data.network.apiService.ApiServiceProvider
import com.hse.courseworkcompose.data.network.apiService.ApiServiceSelection
import com.hse.courseworkcompose.data.network.apiService.ApiServiceUser
import com.hse.courseworkcompose.data.repository.AdvertisementRepositoryImpl
import com.hse.courseworkcompose.data.repository.OrderRepositoryImpl
import com.hse.courseworkcompose.data.repository.SelectionRepositoryImpl
import com.hse.courseworkcompose.data.repository.UserRepositoryImpl
import com.hse.courseworkcompose.domain.entity.MyObjectBox
import com.hse.courseworkcompose.domain.repository.AdvertisementRepository
import com.hse.courseworkcompose.domain.repository.OrderRepository
import com.hse.courseworkcompose.domain.repository.SelectionRepository
import com.hse.courseworkcompose.domain.repository.UserRepository


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }


    @Provides
    @Singleton
    fun provideApiServiceUser(): ApiServiceUser {
        return ApiServiceProvider.apiServiceUser
    }

    @Provides
    @Singleton
    fun provideApiServiceOrder (): ApiServiceOrder {
        return ApiServiceProvider.apiServiceOrder
    }

    @Provides
    @Singleton
    fun provideApiServiceSelection(): ApiServiceSelection {
        return ApiServiceProvider.apiServiceSelection
    }
    @Provides
    @Singleton
    fun provideApiServiceAdvertisement(): ApiServiceAdvertisement {
        return ApiServiceProvider.apiServiceAdvertisement
    }


    @Provides
    @Singleton
    fun provideRemoteDataSourceUser(apiServiceUser: ApiServiceUser): RemoteDataSourceUser {
        return RemoteDataSourceUser(apiServiceUser)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSourceOrder(apiServiceOrder: ApiServiceOrder): RemoteDataSourceOrder {
        return RemoteDataSourceOrder(apiServiceOrder)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSourceAdvertisement(apiServiceAdvertisement: ApiServiceAdvertisement): RemoteDataSourceAdvertisement {
        return RemoteDataSourceAdvertisement(apiServiceAdvertisement)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSourceSelection(apiServiceSelection: ApiServiceSelection): RemoteDataSourceSelection {
        return RemoteDataSourceSelection(apiServiceSelection)
    }

    @Provides
    @Singleton
    fun provideLocalDataSourceUser(boxStore: BoxStore): LocalDataSourceUser {
        return LocalDataSourceUser(boxStore)
    }

    @Provides
    @Singleton
    fun provideDataSourceAdvertisement(boxStore: BoxStore): LocalDataSourceAdvertisement {
        return LocalDataSourceAdvertisement(boxStore)
    }




    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSourceUser: RemoteDataSourceUser,
        localDataSourceUser: LocalDataSourceUser
    ): UserRepository {
        return UserRepositoryImpl(
            remoteDataSourceUser,
            localDataSourceUser
        )
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        remoteDataSourceOrder: RemoteDataSourceOrder,
    ): OrderRepository {
        return OrderRepositoryImpl(
            remoteDataSourceOrder
        )
    }

    @Provides
    @Singleton
    fun provideSelectionRepository(
        remoteDataSourceSelection: RemoteDataSourceSelection,
    ): SelectionRepository {
        return SelectionRepositoryImpl(
            remoteDataSourceSelection
        )
    }

    @Provides
    @Singleton
    fun provideAdvertisementRepository(
        remoteDataSourceAdvertisement: RemoteDataSourceAdvertisement,
        localDataSourceAdvertisement: LocalDataSourceAdvertisement
    ): AdvertisementRepository {
        return AdvertisementRepositoryImpl(
            remoteDataSourceAdvertisement,
            localDataSourceAdvertisement
        )
    }


    @Provides
    @Singleton
    fun provideBoxStore(@ApplicationContext context: Context): BoxStore {
        val boxStore = MyObjectBox.builder()
            .androidContext(context)
            .androidReLinker(ReLinker.log(object : ReLinker.Logger {
                override fun log(message: String) {
                    Log.d("ObjectBoxLog", message)
                }
            }))
            .build()

//        boxStore.close()
//        boxStore.deleteAllFiles()

        return boxStore;


    }
}