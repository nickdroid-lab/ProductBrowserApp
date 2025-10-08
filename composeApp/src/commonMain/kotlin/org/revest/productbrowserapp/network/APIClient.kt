package org.revest.productbrowserapp.network
import io.ktor.client.HttpClient
import org.revest.productbrowserapp.getHttpClient

fun createHttpClient(): HttpClient {
    return getHttpClient()
}
