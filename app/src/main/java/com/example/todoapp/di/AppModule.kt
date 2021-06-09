package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.todoapp.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database = Room.databaseBuilder(
        app, Database::class.java, "database"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideItemDao(db: Database) = db.itemDao()
}