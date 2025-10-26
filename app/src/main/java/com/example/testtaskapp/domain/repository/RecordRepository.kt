package com.example.testtaskapp.domain.repository

import com.example.testtaskapp.domain.model.Record

interface RecordRepository {
    suspend fun getAllIds(): List<Int>
    suspend fun getRecordById(id: Int): Record
}