package org.revest.productbrowserapp

import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getHttpClient(): HttpClient {
    val okHttpClient = OkHttpClient.Builder()
        // .addInterceptor(...) // Add any OkHttp interceptors if needed
        .build()

    return HttpClient(OkHttp) {
        engine {
            preconfigured = okHttpClient // Use the preconfigured OkHttpClient
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        // Other Ktor client configurations (e.g., content negotiation)
    }
}

@Composable
actual fun NetworkImage(url: String, modifier: Modifier, contentDescription: String?) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
actual fun BackIcon(contentDescription: String?) {
    Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = contentDescription
    )
}
