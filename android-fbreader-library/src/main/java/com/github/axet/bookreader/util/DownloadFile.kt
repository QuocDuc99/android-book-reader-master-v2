package com.github.axet.bookreader.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object DownloadFile {
  fun downloadFile(url: String, file: File): String {
    CoroutineScope(Dispatchers.IO).launch {
      val client = OkHttpClient()
      val request = Request.Builder().url(url).build()
      client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        FileOutputStream(file).use { outputStream ->
          response.body?.byteStream()?.use { inputStream ->
            inputStream.copyTo(outputStream)
          }
        }
      }
    }
    return file.absolutePath
  }
}