package com.example.testtaskapp.domain.usecase

import com.example.testtaskapp.domain.repository.RecordRepository
import jakarta.inject.Inject

class GetAllRecordsUseCase @Inject constructor(private val repository: RecordRepository) {
    suspend operator fun invoke(): List<Int> = repository.getAllIds()
}