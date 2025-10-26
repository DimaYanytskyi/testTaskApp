package com.example.testtaskapp.domain.usecase

import com.example.testtaskapp.domain.repository.RecordRepository
import jakarta.inject.Inject
import com.example.testtaskapp.domain.model.Record

class GetRecordByIdUseCase @Inject constructor(private val repository: RecordRepository) {
    suspend operator fun invoke(id: Int): Record? = repository.getRecordById(id)
}