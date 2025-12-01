package com.plater.android.core.di

import android.util.Log
import com.google.gson.Gson
import com.plater.android.data.remote.interceptor.AuthInterceptor
import com.plater.android.data.remote.service.ApiService
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

    /**
     * Builds an [OkHttpClient] with authentication and logging interceptors.
     * AuthInterceptor is added first to handle token attachment and refresh.
     * LoggingInterceptor is added second to log requests (including auth headers).
     *
     * @param authInterceptor Interceptor that handles token attachment and silent refresh.
     * @return singleton OkHttp client instance.
     */
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

    /**
     * Creates the application's [Retrofit] instance using the provided client.
     *
     * @param okHttpClient configured HTTP client used for requests.
     * @return Retrofit instance pointing to the JSONPlaceholder API.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * Exposes the [ApiService] implementation created by Retrofit.
     *
     * @param retrofit Retrofit service generator.
     * @return typed API definition for endpoints.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

}