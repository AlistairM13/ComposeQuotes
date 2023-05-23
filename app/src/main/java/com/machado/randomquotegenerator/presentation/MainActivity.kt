package com.machado.randomquotegenerator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.machado.randomquotegenerator.R
import com.machado.randomquotegenerator.presentation.ui.theme.RandomQuoteGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )
        setContent {
            RandomQuoteGeneratorTheme {
                val viewModel: QuoteViewModel = hiltViewModel()
                val state = viewModel.state.value
                val snackBarHostState = remember { SnackbarHostState() }
                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is QuoteViewModel.UIEvent.ShowSnackBar -> {
                                snackBarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFF1D2671),
                                        Color(0xFFC33764)
                                    )
                                )
                            )
                            .padding(it)
                    ) {

                        Card(
                            elevation = CardDefaults.cardElevation(16.dp),
                            colors = CardDefaults.cardColors(Color(0xFFE2E7B0)),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .fillMaxHeight(0.6f)
                                .padding(16.dp)
                        ) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                if (state.quotes.isNotEmpty()) {
                                    val quote = state.quotes.random()
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    ) {
                                        IconButton(onClick = {
                                            viewModel.saveQuote(quote)
                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_save),
                                                contentDescription = "Save quote",
                                                        tint = Color.Black
                                            )
                                        }
                                        if (state.quotes.size > 1) {
                                            IconButton(onClick = {
                                                viewModel.deleteQuote(quote)
                                            }) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.icon_delete),
                                                    contentDescription = "Delete saved quote",
                                                    tint = Color.Black
                                                )
                                            }
                                        }

                                    }
                                    Text(
                                        text = quote.content,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = Color.Black,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "- ${quote.author}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Light,
                                        textAlign = TextAlign.End,
                                        color = Color.Black,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                }

                                if (state.isLoading) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                                } else {
                                    IconButton(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.CenterHorizontally),
                                        onClick = {
                                            viewModel.getQuotes()
                                        }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_random),
                                            contentDescription = "Save quote",
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}




