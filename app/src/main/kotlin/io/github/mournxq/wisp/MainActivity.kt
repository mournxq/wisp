package io.github.mournxq.wisp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.github.mournxq.wisp.core.ClaudeSdkCheck
import io.github.mournxq.wisp.core.Wisp

private fun sdkStatus(): String =
    runCatching { ClaudeSdkCheck.requestJson() }
        .fold({ "Claude SDK OK: $it" }, { "Claude SDK error: $it" })

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val colors = if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            MaterialTheme(colorScheme = colors) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(Wisp.NAME, style = MaterialTheme.typography.displayMedium)
                        Text(Wisp.greeting(), style = MaterialTheme.typography.bodyLarge)
                        Text(sdkStatus(), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
