package com.sunaa.protoaddme.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.sunaa.protoaddme.UserInfo.UserList
import com.sunaa.protoaddme.mainfeature.data.ProtoImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ObjectModule {

    // Define the extension property outside of the Activity
    val Context.userDataStore: DataStore<UserList> by dataStore(
        fileName = "user_details.pb",
        serializer = ProtoImplementation()
    )

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<UserList> {
        return context.userDataStore
    }
}