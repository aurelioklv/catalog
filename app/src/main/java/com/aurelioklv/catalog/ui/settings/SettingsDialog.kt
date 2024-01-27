package com.aurelioklv.catalog.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aurelioklv.catalog.data.model.DarkThemeConfig

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState by viewModel.state.collectAsStateWithLifecycle()
    SettingsDialog(
        onDismiss = onDismiss,
        state = settingsUiState,
        onSetDarkThemeConfig = viewModel::setDarkThemeConfig,
        onToggleFetchAmount = viewModel::toggleFetchAmount,
        onToggleGridColumn = viewModel::toggleGridColumn,
    )

}

@Composable
fun SettingsDialog(
    state: SettingsUiState,
    onDismiss: () -> Unit,
    onSetDarkThemeConfig: (DarkThemeConfig) -> Unit,
    onToggleFetchAmount: () -> Unit,
    onToggleGridColumn: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    AlertDialog(
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Settings")
        },
        text = {
            HorizontalDivider()
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(8.dp))
                when (state) {
                    SettingsUiState.Loading -> {
                        Text(text = "Loading")
                    }

                    is SettingsUiState.Success -> {
                        SettingsPanel(
                            settings = state.settings,
                            onSetDarkThemeConfig = onSetDarkThemeConfig,
                            onToggleFetchAmount = onToggleFetchAmount,
                            onToggleGridColumn = onToggleGridColumn,
                        )
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = "OK",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        }
    )
}

@Composable
fun SettingsPanel(
    settings: EditableSettings,
    onSetDarkThemeConfig: (DarkThemeConfig) -> Unit,
    onToggleFetchAmount: () -> Unit,
    onToggleGridColumn: () -> Unit,
) {
    SettingsSectionTitle(text = "Theme")
    Column {
        SettingsChooserRow(
            text = "System Default",
            selected = settings.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
            onClick = { onSetDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) }
        )
        SettingsChooserRow(
            text = "Light",
            selected = settings.darkThemeConfig == DarkThemeConfig.LIGHT,
            onClick = { onSetDarkThemeConfig(DarkThemeConfig.LIGHT) }
        )
        SettingsChooserRow(
            text = "Dark",
            selected = settings.darkThemeConfig == DarkThemeConfig.DARK,
            onClick = { onSetDarkThemeConfig(DarkThemeConfig.DARK) }
        )
    }

    SettingsSectionTitle(text = "Other preferences")
    Column {
        SettingsToggle(
            text = "Fetch amount",
            value = settings.fetchAmount.toString(),
            onClick = onToggleFetchAmount
        )
        SettingsToggle(
            text = "Grid column",
            value = settings.gridColumn.toString(),
            onClick = onToggleGridColumn
        )
    }
}

@Composable
fun SettingsSectionTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium)
}

@Composable
fun SettingsChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
fun SettingsToggle(
    text: String,
    value: String,
    onClick: () -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .clickable { onClick() }) {
        Text(text, modifier = Modifier.weight(3f))
        Text(value, modifier = Modifier.weight(1f))
    }
}