package com.plater.android.core.di

import android.content.Context
import com.plater.android.data.remote.service.ApiService
import com.plater.android.data.remote.service.AuthService
import com.plater.android.data.repositoryimpl.UserRepositoryImpl
import com.plater.android.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module that provides repository-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        authService: AuthService,
        apiService: ApiService,
        @ApplicationContext context: Context
    ): UserRepository {
        return UserRepositoryImpl(
            authService = authService,
            apiService = apiService,
            context = context
        )
    }
}