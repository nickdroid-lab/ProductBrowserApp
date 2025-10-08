package org.revest.productbrowserapp

import io.ktor.client.HttpClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getHttpClient(): HttpClient


@Composable
expect fun NetworkImage(url: String, modifier: Modifier = Modifier, contentDescription: String? = null)

@Composable
expect fun BackIcon(contentDescription: String?)