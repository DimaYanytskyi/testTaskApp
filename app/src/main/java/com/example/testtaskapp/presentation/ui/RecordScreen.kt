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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest

@Composable
fun RecordScreen(viewModel: RecordViewModel = hiltViewModel()) {
    val record = viewModel.uiState
    val loading = viewModel.loading
    val error = viewModel.error
    val recordIds = viewModel.recordIds
    val selectedId = viewModel.selectedId

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                loading -> CircularProgressIndicator()
                error != null -> Text(text = "Error: $error")
                record != null -> when (record.type) {
                    "text" -> TextScreen(record.text ?: "")
                    "image" -> ImageScreen(record.picture ?: "")
                    "webview" -> WebviewScreen(record.url ?: "")
                    else -> Text(text = "Unknown type")
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            recordIds.forEach { id ->
                Box(
                    modifier = Modifier
                        .background(
                            if (id == selectedId) Color(0xFFD26C43) else Color.LightGray,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .clickable { viewModel.loadRecordById(id) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "ID: $id", color = Color.White)
                }
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
    var isLoading by remember { mutableStateOf(true) }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = "Loaded image",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                isLoading = true
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is AsyncImagePainter.State.Success -> {
                isLoading = false
                SubcomposeAsyncImageContent()
            }
            is AsyncImagePainter.State.Error -> {
                isLoading = false
                Text(text = "Failed to load image", color = Color.Red)
            }
            else -> {}
        }
    }
}

@Composable
fun WebviewScreen(url: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
        modifier = modifier
    )
}