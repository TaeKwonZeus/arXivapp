package com.github.taekwonzeus.arxivapp.api

import com.github.taekwonzeus.arxivapp.api.model.ArxivFeed
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import java.io.StringReader
import javax.inject.Inject
import javax.xml.bind.JAXBContext

class ApiService @Inject constructor() {
    private val client = HttpClient {
        defaultRequest {
            accept(ContentType("application", "atom+xml"))
        }
    }

    private fun removeNamespaces(xml: String): String =
        xml.replace(Regex("""xmlns:[^=]+="[^"]+"|xmlns="[^"]+"|:"""), "")

    suspend fun queryRaw(query: String): ArxivFeed? {
        // Unescaped query
        val res = client.get("https://export.arxiv.org/api/query?search_query=$query")
        if (!res.status.isSuccess()) return null

        val context = JAXBContext.newInstance(ArxivFeed::class.java)
        val unmarshaller = context.createUnmarshaller()

        val reader = StringReader(res.bodyAsText())

        return unmarshaller.unmarshal(reader) as ArxivFeed
    }
}