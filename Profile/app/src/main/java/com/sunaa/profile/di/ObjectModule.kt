package com.sunaa.profile.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.sunaa.profile.UserDetails
import com.sunaa.profile.UserDetailsSerializer
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
    val Context.userDataStore: DataStore<UserDetails> by dataStore(
        fileName = "user_details.pb",
        serializer = UserDetailsSerializer
    )
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) : DataStore<UserDetails> {
        return context.userDataStore
    }
}