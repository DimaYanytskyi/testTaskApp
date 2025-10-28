package com.example.testtaskapp.di

import com.example.testtaskapp.data.remote.ApiService
import com.example.testtaskapp.data.repository.RecordRepositoryImpl
import com.example.testtaskapp.domain.repository.RecordRepository
import com.example.testtaskapp.domain.usecase.GetAllRecordsUseCase
import com.example.testtaskapp.domain.usecase.GetRecordByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideRecordRepository(api: ApiService): RecordRepository = RecordRepositoryImpl(api)


    @Provides
    fun provideGetAllRecordsUseCase(repository: RecordRepository) = GetAllRecordsUseCase(repository)


    @Provides
    fun provideGetRecordByIdUseCase(repository: RecordRepository) = GetRecordByIdUseCase(repository)
}