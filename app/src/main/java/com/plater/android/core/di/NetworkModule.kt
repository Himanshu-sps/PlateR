package com.plater.android.core.di

import android.util.Log
import com.google.gson.Gson
import com.plater.android.data.remote.interceptor.AuthInterceptor
import com.plater.android.data.remote.service.ApiService
import com.plater.android.data.remote.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dagger module that configures Retrofit networking components for the app.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://dummyjson.com/"

    /**
     * Provides the base URL for API requests.
     */
    @Provides
    @Singleton
    @Named("base_url")
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("HTTP", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor) // Add auth interceptor first
            .addInterceptor(loggingInterceptor) // Add logging interceptor second
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofitBuilder: Retrofit.Builder): AuthService {
        // AuthService doesn't use OkHttpClient with interceptor to avoid circular dependency
        // when refreshing tokens from within the interceptor
        return retrofitBuilder
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): ApiService {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

}