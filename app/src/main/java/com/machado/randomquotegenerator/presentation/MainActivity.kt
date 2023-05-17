package com.machado.randomquotegenerator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.machado.randomquotegenerator.presentation.ui.theme.RandomQuoteGeneratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RandomQuoteGeneratorTheme {

            }
        }
    }
}
