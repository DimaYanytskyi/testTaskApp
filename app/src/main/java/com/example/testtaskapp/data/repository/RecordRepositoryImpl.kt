package com.example.testtaskapp.data.repository

import com.example.testtaskapp.data.remote.ApiService
import com.example.testtaskapp.domain.model.Record
import com.example.testtaskapp.domain.repository.RecordRepository
import jakarta.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val api: ApiService
) : RecordRepository {
    override suspend fun getAllIds(): List<Int> = api.getAllRecords()

    override suspend fun getRecordById(id: Int): Record =
        api.getRecord(id).let {
            Record(it.id, it.type, it.text, it.picture, it.url)
        }
}