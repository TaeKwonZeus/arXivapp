package com.github.taekwonzeus.arxivapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.github.taekwonzeus.arxivapp.api.ApiService
import com.github.taekwonzeus.arxivapp.api.ArxivEntry
import com.github.taekwonzeus.arxivapp.ui.models.MainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val apiService: ApiService) : ViewModel() {
    var state = MutableStateFlow(
        MainModel(
            runBlocking {
                apiService.queryRaw("all:electron")
            }
        )
    )
    var art: ArxivEntry? = null
}