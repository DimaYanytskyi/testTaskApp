package com.example.testtaskapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskapp.domain.usecase.GetAllRecordsUseCase
import com.example.testtaskapp.domain.usecase.GetRecordByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import com.example.testtaskapp.domain.model.Record

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getAllRecords: GetAllRecordsUseCase,
    private val getRecordById: GetRecordByIdUseCase
) : ViewModel() {

    var uiState by mutableStateOf<Record?>(null)
        private set

    var loading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)

    init { loadRandomRecord() }

    private fun loadRandomRecord() {
        viewModelScope.launch {
            try {
                val ids = getAllRecords()
                val first = ids.random()
                uiState = getRecordById(first)
            } catch (e: Exception) {
                error = e.message
            } finally { loading = false }
        }
    }
}