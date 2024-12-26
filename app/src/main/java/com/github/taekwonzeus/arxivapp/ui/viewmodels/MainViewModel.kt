package com.github.taekwonzeus.arxivapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.github.taekwonzeus.arxivapp.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    val state = MutableStateFlow(runBlocking { apiService.queryRaw("all:electron") })
}