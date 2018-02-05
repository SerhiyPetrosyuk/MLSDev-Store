package com.mlsdev.mlsdevstore.inject.module;

import com.mlsdev.mlsdevstore.data.DataSource;
import com.mlsdev.mlsdevstore.data.model.authentication.AppAccessToken;
import com.mlsdev.mlsdevstore.data.remote.RemoteDataSource;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;

@Module
public class MockDataSourceModule {

    @Provides
    @Singleton
    DataSource provideDataSource() {
        return Mockito.mock(DataSource.class);
    }

    @Provides
    @Singleton
    RemoteDataSource remoteDataSource() {
        RemoteDataSource dataSource = Mockito.mock(RemoteDataSource.class);
        AppAccessToken appAccessToken = Mockito.mock(AppAccessToken.class);
        Mockito.when(appAccessToken.getAccessToken()).thenReturn("token");
        Mockito.when(appAccessToken.getExpirationDate()).thenReturn(System.currentTimeMillis() + 10000L);
        Mockito.when(dataSource.getAppAccessToken()).thenReturn(Single.just(appAccessToken));
        return dataSource;
    }

}
