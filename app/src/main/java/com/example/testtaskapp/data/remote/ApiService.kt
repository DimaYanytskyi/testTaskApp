package com.example.testtaskapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("records")
    suspend fun getAllRecords(): List<Int>

    @GET("record/{id}")
    suspend fun getRecord(
        @Path("id") id: Int
    ): RecordDto
}