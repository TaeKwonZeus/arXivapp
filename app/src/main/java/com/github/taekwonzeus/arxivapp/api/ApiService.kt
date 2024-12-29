package com.github.taekwonzeus.arxivapp.api

import android.util.Log
import android.util.Xml
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import io.ktor.http.ContentType
import kotlinx.io.IOException
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.ByteArrayInputStream
import java.util.TreeSet
import javax.inject.Inject
import kotlin.system.exitProcess

data class ArxivEntry(
    val title: String?,
    val summary: String?,
    val links: List<ArxivLink>,
    val authors: TreeSet<String>,
)

data class ArxivLink(val href: String, val rel: String, val type: String)

val ns: String? = null

class ApiService @Inject constructor() {
    private val client = HttpClient {
        defaultRequest {
            accept(ContentType("application", "atom+xml"))
        }
    }

    suspend fun queryRaw(query: String): List<ArxivEntry> {
        val res: ByteArray;
        try {
            res = client.get("https://export.arxiv.org/api/query?search_query=$query&max_results=1000") {

            }.bodyAsBytes()
        } catch (e: Exception) {
            Log.e("error!!!", e.message!!)
            exitProcess(1)
        }
        val parser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

        val inputStream = ByteArrayInputStream(res)
        parser.setInput(inputStream, null)

        parser.nextTag()

        return readFeed(parser)
    }

    private fun readFeed(parser: XmlPullParser): List<ArxivEntry> {
        val entries = mutableListOf<ArxivEntry>()

        parser.require(XmlPullParser.START_TAG, ns, "feed")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag.
            if (parser.name == "entry") {
                entries.add(readEntry(parser))
            } else {
                skip(parser)
            }
        }
        return entries
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private fun readEntry(parser: XmlPullParser): ArxivEntry {
        parser.require(XmlPullParser.START_TAG, ns, "entry")
        var title: String? = null
        var summary: String? = null
        val links = mutableListOf<ArxivLink>()
        val authors = mutableListOf<String>()
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readTitle(parser)
                "summary" -> summary = readSummary(parser)
                "link" -> links.add(readLink(parser))
                "author" -> authors.add(readAuthor(parser))
                else -> skip(parser)
            }
        }
        return ArxivEntry(title, summary, links, TreeSet(authors))
    }

    // Processes title tags in the feed.
    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "title")
        return title
    }

    // Processes link tags in the feed.
    private fun readLink(parser: XmlPullParser): ArxivLink {
        parser.require(XmlPullParser.START_TAG, ns, "link")
        val rel = parser.getAttributeValue(null, "rel") ?: ""
        val href = parser.getAttributeValue(null, "href") ?: ""
        val type = parser.getAttributeValue(null, "type") ?: ""
        parser.nextTag()
        parser.require(XmlPullParser.END_TAG, ns, "link")
        return ArxivLink(href, rel, type)
    }

    // Processes author tags in the feed.
    private fun readAuthor(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "author")
        parser.nextTag()
        parser.require(XmlPullParser.START_TAG, ns, "name")
        val summary = readText(parser)
        while (parser.name != "author" || parser.eventType != XmlPullParser.END_TAG) {
            parser.next()
        }
        return summary
    }

    // Processes summary tags in the feed.
    private fun readSummary(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "summary")
        val summary = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "summary")
        return summary
    }

    // For the tags title and summary, extracts their text values.
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}