@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.devgroup.spirioskelar.mandala

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devgroup.spirioskelar.R
import com.devgroup.spirioskelar.navigation.LocalNavBackStack
import com.devgroup.spirioskelar.navigation.back
import com.devgroup.spirioskelar.ui.components.LabelText
import com.devgroup.spirioskelar.ui.components.Mandala
import com.devgroup.spirioskelar.ui.components.PrimaryButton
import com.devgroup.spirioskelar.ui.components.SpirioAnimatedBackgroundBox
import com.devgroup.spirioskelar.ui.components.SpirioSlider
import com.devgroup.spirioskelar.ui.components.SpirioTopAppBar
import com.devgroup.spirioskelar.ui.theme.SpirioSKELARTheme
import com.devgroup.spirioskelar.util.CollectAsEvents
import kotlinx.serialization.Serializable

@Serializable
data object Mandala : NavKey

fun EntryProviderScope<NavKey>.mandalaEntry() = entry<Mandala> { MandalaScreenRoot() }

@Composable
fun MandalaScreenRoot(viewModel: MandalaViewModel = viewModel()) {
    val navBackStack = LocalNavBackStack.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalSliderValue provides state.sliderValue) {
        MandalaScreen(
            state = state,
            onAction = viewModel::onAction
        )

        CollectAsEvents(viewModel.events) { event ->
            when (event) {
                MandalaEvent.NavEvent.Back -> navBackStack.back()
            }
        }
    }
}

@Composable
fun MandalaScreen(
    state: MandalaState,
    onAction: (MandalaAction) -> Unit,
    modifier: Modifier = Modifier
) {
    SpirioAnimatedBackgroundBox {
        Scaffold(
            modifier = modifier,
            containerColor = Color.Unspecified,
            topBar = {
                SpirioTopAppBar(
                    title = {
                        Text(
                            text = "About you",
                            style = MaterialTheme.typography.titleMediumEmphasized.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onAction(MandalaAction.OnNavigateBack) }) {
                            Icon(painter = painterResource(R.drawable.arrow_back), "navigate up")
                        }
                    }
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .consumeWindowInsets(contentPadding)
                    .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Mandala()
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "How experienced are you with spiritual practices?",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))

                Crossfade(targetState = state.sliderValue) { sliderValue ->
                    val text = if (sliderValue <= 0.25f) {
                        "Never tired"
                    } else if (sliderValue <= 0.5f) {
                        "Tried a few practices"
                    } else if (sliderValue <= 0.75f) {
                        "Tried a few practices"
                    } else "Practice regularly"

                    LabelText(modifier = Modifier.fillMaxWidth(), text = text)
                }

                Spacer(modifier = Modifier.height(16.dp))

                /*Text(text = "${state.sliderValue}", color = Color.White)*/
                SpirioSlider(value = state.sliderValue, onAction = onAction)

                Spacer(modifier = Modifier.height(32.dp))

                Box(contentAlignment = Alignment.BottomEnd) {
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { }
                    ) {
                        Text(
                            text = "Continue",
                            style = MaterialTheme.typography.titleMediumEmphasized.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun MandalaScreenPreview() {
    SpirioSKELARTheme {
        MandalaScreen(
            state = MandalaState(),
            onAction = { }
        )
    }
}

val LocalSliderValue = compositionLocalOf { 0f }