package com.github.taekwonzeus.arxivapp

import com.github.taekwonzeus.arxivapp.api.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ApiServiceTest {
    @Test
    fun queryRaw() = runBlocking {
        val client = ApiService()
        println(client.queryRaw("all:electron"))
    }
}