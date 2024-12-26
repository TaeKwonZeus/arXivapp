package com.github.taekwonzeus.arxivapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.github.taekwonzeus.arxivapp.services.ApiService
import com.github.taekwonzeus.arxivapp.ui.models.MainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val apiService: ApiService) : ViewModel() {
    val state = MutableStateFlow(MainModel())
}