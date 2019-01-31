package com.mlsdev.mlsdevstore.injections.module

import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.local.LocalDataSource
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService
import com.mlsdev.mlsdevstore.data.remote.service.OrderService
import com.mlsdev.mlsdevstore.data.remote.service.TaxonomyService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
                         orderService: OrderService,
                         sharedPreferencesManager: SharedPreferencesManager,
                         database: AppDatabase,
                         cart: Cart): RemoteDataSource {
        return RemoteDataSource(browseService, authenticationService, taxonomyService, orderService,
                sharedPreferencesManager, database, cart)
    }
}
