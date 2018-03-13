package com.mlsdev.mlsdevstore.dependency_injection.module;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.local.LocalDataSource;
import com.mlsdev.mlsdevstore.data.local.SharedPreferencesManager;
import com.mlsdev.mlsdevstore.data.local.database.AppDatabase;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;
import com.mlsdev.mlsdevstore.data.remote.service.AuthenticationService;
import com.mlsdev.mlsdevstore.data.remote.service.BrowseService;
import com.mlsdev.mlsdevstore.data.remote.service.TaxonomyService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {

    @Provides
    @Singleton
    DataSource provideDataSource(LocalDataSource localDataSource) {
        return localDataSource;
    }

    @Provides
    @Singleton
    LocalDataSource provideLocalDataSource(RemoteDataSource remoteDataSource, AppDatabase appDatabase) {
        return new LocalDataSource(remoteDataSource, appDatabase);
    }

    @Provides
    @Singleton
    RemoteDataSource remoteDataSource(BrowseService browseService,
                                      AuthenticationService authenticationService,
                                      TaxonomyService taxonomyService,
                                      SharedPreferencesManager sharedPreferencesManager,
                                      AppDatabase database) {
        return new RemoteDataSource(browseService, authenticationService, taxonomyService, sharedPreferencesManager, database);
    }
}
