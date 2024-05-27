package com.example.movieapp.data.di

import android.content.Context
import com.example.movieapp.data.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MoviesDatabase {
        return MoviesDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: MoviesDatabase) = database.movieDao()

}