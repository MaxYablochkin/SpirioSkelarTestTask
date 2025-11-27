@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalLayoutApi::class
)

package com.devgroup.spirioskelar.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devgroup.spirioskelar.R
import com.devgroup.spirioskelar.mandala.Mandala
import com.devgroup.spirioskelar.navigation.LocalNavBackStack
import com.devgroup.spirioskelar.ui.components.PrimaryButton
import com.devgroup.spirioskelar.ui.components.SpirioBackgroundBox
import com.devgroup.spirioskelar.ui.components.UserAvatar
import com.devgroup.spirioskelar.ui.theme.Error
import com.devgroup.spirioskelar.ui.theme.SpirioSKELARTheme
import com.devgroup.spirioskelar.util.CollectAsEvents
import kotlinx.serialization.Serializable

@Serializable
data object Login : NavKey

fun EntryProviderScope<NavKey>.loginEntry() = entry<Login> { LoginScreenRoot() }

@Composable
fun LoginScreenRoot(viewModel: LoginViewModel = viewModel()) {
    val navBackStack = LocalNavBackStack.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onAction = viewModel::onAction
    )

    CollectAsEvents(viewModel.events) { event ->
        when (event) {
            LoginEvent.NavEvent.Mandala -> navBackStack.add(Mandala)
        }
    }

}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    SpirioBackgroundBox {
        Scaffold(modifier = modifier, containerColor = Color.Unspecified) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .consumeWindowInsets(contentPadding)
                    .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                    .imePadding()
                    .imeNestedScroll(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                UserAvatar()
                Text(
                    text = "Welcome back",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineLarge
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.emailValue,
                    onValueChange = { onAction(LoginAction.OnEmailChange(it)) },
                    label = {
                        Text(
                            text = "Your email",
                            color = if (state.isEmailError) Error else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    trailingIcon = {
                        if (state.isEmailError) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_error_24),
                                contentDescription = "incorrect email",
                                tint = Error
                            )
                        }
                    },
                    supportingText = {
                        Text(
                            text = if (state.isEmailError) "Invalid email" else "",
                            color = if (state.isEmailError) Error else Color.Unspecified
                        )
                    }
                )
                TextField(
                    value = state.passwordValue,
                    onValueChange = { onAction(LoginAction.OnPasswordChange(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    supportingText = {
                        Text(
                            text = if (state.isPasswordError) "Empty password" else "",
                            color = if (state.isPasswordError) Error else Color.Unspecified
                        )
                    }
                )
                Spacer(modifier = Modifier.weight(1f))

                Box(contentAlignment = Alignment.BottomEnd) {
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onAction(LoginAction.OnMandalaClick) },
                        enabled = !state.isEmailError && !state.isPasswordError
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
private fun LoginScreenPreview() {
    SpirioSKELARTheme {
        LoginScreen(
            state = LoginState(),
            onAction = { }
        )
    }
}

