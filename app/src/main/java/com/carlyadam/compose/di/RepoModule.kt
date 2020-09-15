package com.carlyadam.compose.di

import com.carlyadam.compose.data.api.ApiService
import com.carlyadam.compose.repo.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideUserRepository(
        apiService: ApiService
    ): PostRepository {
        return PostRepository(apiService)
    }
}
