package org.revest.productbrowserapp

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import platform.UIKit.UIDevice
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


actual fun getHttpClient(): HttpClient {
    return HttpClient(Darwin) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // A robust setting for production
                prettyPrint = true
                isLenient = true
            })
        }
        // Other Ktor client configurations (e.g., content negotiation)
    }
}

@Composable
actual fun NetworkImage(url: String, modifier: Modifier, contentDescription: String?) {
    // iOS implementation placeholder
    // Compose for iOS does not have a direct equivalent to AsyncImage yet
    // You may use a custom implementation or SwiftUI interop
    // For now, show a placeholder
    androidx.compose.material3.Text("Img", modifier = modifier)
}

@Composable
actual fun BackIcon(contentDescription: String?) {
    // Simple text as back icon for iOS
    Text("<", modifier = Modifier)
}
