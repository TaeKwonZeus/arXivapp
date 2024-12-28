package com.github.taekwonzeus.arxivapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.github.taekwonzeus.arxivapp.api.ApiService
import com.github.taekwonzeus.arxivapp.ui.models.MainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    val state = MutableStateFlow(
        MainModel(
            runBlocking {
                apiService.queryRaw("all:electron")
            }
        )
    )
}