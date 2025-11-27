package com.devgroup.spirioskelar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.devgroup.spirioskelar.navigation.SpirioNavigation
import com.devgroup.spirioskelar.ui.theme.SpirioSKELARTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpirioSKELARTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SpirioNavigation()
                }
            }
        }
    }
}