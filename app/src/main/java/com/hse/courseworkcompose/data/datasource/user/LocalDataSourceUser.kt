package com.hse.courseworkcompose.data.datasource.user


import android.util.Log
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Inject
import com.hse.courseworkcompose.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSourceUser @Inject constructor(
    boxStore: BoxStore
) {
    private val userBox: Box<User> = boxStore.boxFor(User::class.java)

    companion object {
        private const val TAG = "LocalDataSourceUser"
    }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        userBox.removeAll()
    }

    suspend fun updateUserData(user: User) = withContext(Dispatchers.IO) {
        userBox.removeAll()
        userBox.put(user)
    }

    suspend fun getAllUsers(): MutableList<User>? = withContext(Dispatchers.IO) {
        Log.i(TAG, "All users: ${userBox.all}")
        userBox.all
    }

    suspend fun saveUser(user: User) = withContext(Dispatchers.IO) {
        userBox.put(user)
        Log.i(TAG, "User was saved: $user")
    }

//    suspend fun removeUser() = withContext(Dispatchers.IO) {
//        Log.i(TAG, "Users were removed")
//        userBox.removeAll()
//    }

    suspend fun getUser(id: Long): User? = withContext(Dispatchers.IO) {
        Log.i(TAG, "User was taken: ${userBox.get(id)}")
        userBox.get(id)
    }

    suspend fun contains(id: Long): Boolean = withContext(Dispatchers.IO) {
        Log.i(TAG, "Is user contains: ${userBox.contains(id)}")
        userBox.contains(id)
    }


}