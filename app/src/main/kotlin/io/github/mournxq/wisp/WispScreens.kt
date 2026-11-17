package io.github.mournxq.wisp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.mournxq.wisp.core.ChatError

@Composable
fun WispApp(vm: WispViewModel = viewModel()) {
    if (vm.showSettings) SettingsScreen(vm) else ChatScreen(vm)
}

@Composable
private fun ChatScreen(vm: WispViewModel) {
    var input by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    LaunchedEffect(vm.lines.size, vm.busy) {
        if (vm.lines.isNotEmpty()) listState.animateScrollToItem(listState.layoutInfo.totalItemsCount - 1)
    }
    Column(Modifier.fillMaxSize().safeDrawingPadding().padding(horizontal = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
            TextButton(onClick = vm::newChat) { Text(stringResource(R.string.new_chat)) }
            TextButton(onClick = vm::openSettings) { Text(stringResource(R.string.settings)) }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(vm.lines) { ChatBubble(it) }
            if (vm.busy) item { Text(stringResource(R.string.thinking), style = MaterialTheme.typography.bodySmall) }
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text(stringResource(R.string.input_hint)) },
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    vm.send(input)
                    input = ""
                },
                enabled = !vm.busy && input.isNotBlank(),
            ) { Text(stringResource(R.string.send)) }
        }
    }
}

@Composable
private fun ChatBubble(line: ChatLine) {
    val colors = MaterialTheme.colorScheme
    val background = when {
        line.error != null -> colors.errorContainer
        line.fromUser -> colors.primaryContainer
        else -> colors.surfaceVariant
    }
    Box(Modifier.fillMaxWidth(), contentAlignment = if (line.fromUser) Alignment.CenterEnd else Alignment.CenterStart) {
        Text(
            text = line.error?.let { errorText(it) } ?: line.text,
            color = colors.contentColorFor(background),
            modifier = Modifier
                .widthIn(max = 300.dp)
                .background(background, RoundedCornerShape(16.dp))
                .padding(12.dp),
        )
    }
}

@Composable
private fun SettingsScreen(vm: WispViewModel) {
    var key by remember { mutableStateOf("") }
    BackHandler(onBack = vm::closeSettings)
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(stringResource(R.string.settings), style = MaterialTheme.typography.headlineSmall)
        Text(stringResource(if (vm.hasKey) R.string.key_saved else R.string.key_missing))
        OutlinedTextField(
            value = key,
            onValueChange = { key = it },
            label = { Text(stringResource(R.string.api_key)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, autoCorrectEnabled = false),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                vm.saveKey(key)
                key = ""
            },
            enabled = key.isNotBlank(),
        ) { Text(stringResource(R.string.save_and_check)) }
        when (val check = vm.keyCheck) {
            KeyCheck.Idle -> Unit
            KeyCheck.Checking -> Text(stringResource(R.string.checking_key))
            KeyCheck.Ok -> Text(stringResource(R.string.key_ok))
            is KeyCheck.Failed -> Text(errorText(check.error), color = MaterialTheme.colorScheme.error)
        }
        Text(stringResource(R.string.current_model, vm.model))
        if (vm.models.isNotEmpty()) {
            Text(stringResource(R.string.model), style = MaterialTheme.typography.titleMedium)
            vm.models.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(selected = option.id == vm.model, onClick = { vm.selectModel(option.id) }),
                ) {
                    RadioButton(selected = option.id == vm.model, onClick = null)
                    Text(option.name, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
        if (vm.hasKey) OutlinedButton(onClick = vm::deleteKey) { Text(stringResource(R.string.delete_key)) }
        Button(onClick = vm::closeSettings) { Text(stringResource(R.string.back)) }
    }
}

@Composable
private fun errorText(error: ChatError): String = when (error) {
    ChatError.NoKey -> stringResource(R.string.error_no_key)
    ChatError.WrongKey -> stringResource(R.string.error_wrong_key)
    ChatError.NoCredits -> stringResource(R.string.error_no_credits)
    ChatError.RateLimited -> stringResource(R.string.error_rate_limited)
    ChatError.Overloaded -> stringResource(R.string.error_overloaded)
    ChatError.Offline -> stringResource(R.string.error_offline)
    ChatError.Refused -> stringResource(R.string.error_refused)
    is ChatError.Other -> stringResource(R.string.error_other, error.detail)
}
