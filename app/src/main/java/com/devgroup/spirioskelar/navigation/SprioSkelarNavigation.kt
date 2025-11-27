@file:OptIn(ExperimentalMaterial3Api::class)

package com.devgroup.spirioskelar.navigation


import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.devgroup.spirioskelar.login.Login
import com.devgroup.spirioskelar.login.loginEntry
import com.devgroup.spirioskelar.mandala.mandalaEntry

@Composable
fun SpirioNavigation(modifier: Modifier = Modifier) {
    val navBackStack: NavBackStack<NavKey> = rememberNavBackStack(Login)

    CompositionLocalProvider(LocalNavBackStack provides navBackStack) {
        NavDisplay(
            modifier = modifier,
            backStack = navBackStack,
            onBack = { navBackStack.back() },
            entryProvider = entryProvider { loginEntry(); mandalaEntry() },
            predictivePopTransitionSpec = {
                ContentTransform(
                    targetContentEnter = fadeIn(tween(durationMillis = 700)),
                    initialContentExit = fadeOut(tween(durationMillis = 700)),
                )
            }
        )
    }
}

fun <T : NavKey> NavBackStack<T>.back() {
    if (this.size > 1) this.removeLastOrNull()
}

val LocalNavBackStack = compositionLocalOf<NavBackStack<NavKey>> {
    error("LocalNavBackStack not provided")
}



