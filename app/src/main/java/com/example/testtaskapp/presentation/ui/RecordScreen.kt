package com.example.testtaskapp.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testtaskapp.presentation.RecordViewModel
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun RecordScreen(viewModel: RecordViewModel = hiltViewModel()) {
    val record = viewModel.uiState
    val loading = viewModel.loading
    val error = viewModel.error

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            loading -> CircularProgressIndicator(modifier = Modifier.background(Color.Red))
            error != null -> {
                Text(text = "Error: $error")
            }
            record != null -> when (record.type) {
                "text" -> TextScreen(record.text ?: "")
                "image" -> ImageScreen(record.picture ?: "")
                "webview" -> WebviewScreen(record.url ?: "")
                else -> Text(text = "Unknown type")
            }
        }
    }
}

@Composable
fun TextScreen(text: String) {
    Text(text = text)
}

@Composable
fun ImageScreen(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = "Image type",
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun WebviewScreen(url: String) {
    AndroidView(factory = { ctx ->
        WebView(ctx).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}