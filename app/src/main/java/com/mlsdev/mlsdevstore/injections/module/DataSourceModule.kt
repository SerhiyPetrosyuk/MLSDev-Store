package com.mlsdev.mlsdevstore.injections.module

import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService
import com.mlsdev.mlsdevstore.data.remote.service.TaxonomyService

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideDataSource(localDataSource: LocalDataSource): DataSource {
        return localDataSource
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(remoteDataSource: RemoteDataSource, appDatabase: AppDatabase): LocalDataSource {
        return LocalDataSource(remoteDataSource, appDatabase)
    }

    @Provides
    @Singleton
    fun remoteDataSource(browseService: BrowseService,
                                  authenticationService: AuthenticationService,
                                  taxonomyService: TaxonomyService,
                                  sharedPreferencesManager: SharedPreferencesManager,
                                  database: AppDatabase): RemoteDataSource {
        return RemoteDataSource(browseService, authenticationService, taxonomyService, sharedPreferencesManager, database)
    }
}
