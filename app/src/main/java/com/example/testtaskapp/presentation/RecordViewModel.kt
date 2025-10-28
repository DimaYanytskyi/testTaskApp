package com.example.testtaskapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    var recordIds by mutableStateOf<List<Int>>(emptyList())
    var selectedId by mutableIntStateOf(1)

    init { loadIDs() }

    private fun loadIDs() {
        viewModelScope.launch {
            loading = true
            try {
                recordIds = getAllRecords()
                val first = recordIds.first()
                uiState = getRecordById(first)
            } catch (e: Exception) {
                error = e.message
            } finally { loading = false }
        }
    }

    fun loadRecordById(id: Int) {
        viewModelScope.launch {
            selectedId = id
            loading = true
            try {
                val response = getRecordById(id)
                uiState = response
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }
}