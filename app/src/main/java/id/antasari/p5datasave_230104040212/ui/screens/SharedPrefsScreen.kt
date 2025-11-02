package id.antasari.p5datasave_230104040212.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.CreationExtras
import id.antasari.p5datasave_230104040212.data.prefs.Prefs
import id.antasari.p5datasave_230104040212.data.prefs.PreferencesRepository

// ---------------------- UI Screen ------------------------
@Composable
fun SharedPrefsScreen(onDarkChanged: (Boolean) -> Unit = {}) {
    val ctx = LocalContext.current
    val vm: SharedPrefsViewModel = viewModel(factory = SharedPrefsViewModel.factory(ctx))
    val scroll = rememberScrollState()

    // Muat data preview saat pertama kali masuk
    LaunchedEffect(Unit) {
        vm.preview = PreferencesRepository(ctx).load()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ===== Form Section =====
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 1.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Judul form
                Text(
                    "SharedPreferences Demo",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                // Name
                OutlinedTextField(
                    value = vm.name,
                    onValueChange = { vm.name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // NIM (numeric)
                OutlinedTextField(
                    value = vm.nim,
                    onValueChange = { vm.nim = it },
                    label = { Text("NIM (Student ID)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Switch: Remember Me
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Remember Me")
                        Text(
                            "Keep me logged in",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = vm.remember,
                        onCheckedChange = { vm.remember = it }
                    )
                }

                // Switch: Dark Mode
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Dark Mode Preference")
                        Text(
                            "Toggle theme preference",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = vm.dark,
                        onCheckedChange = { checked ->
                            vm.dark = checked
                            onDarkChanged(checked)
                        }
                    )
                }

                // Tombol Aksi
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilledTonalButton(
                        onClick = {
                            vm.save()
                            onDarkChanged(vm.dark)
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text("SAVE")
                    }

                    OutlinedButton(
                        onClick = {
                            vm.load()
                            onDarkChanged(vm.dark)
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text("READ")
                    }

                    OutlinedButton(
                        onClick = {
                            vm.clear()
                            onDarkChanged(false)
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text("CLEAR")
                    }
                }
            }
        }

        // ===== Saved Data Preview =====
        vm.preview?.let { p ->
            Surface(
                shape = MaterialTheme.shapes.large,
                tonalElevation = 1.dp,
                color = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Saved Data Preview", style = MaterialTheme.typography.titleSmall)
                    RowInfo("Name", p.name)
                    RowInfo("NIM", p.nim)
                    RowInfo("Remember", if (p.remember) "Yes" else "No")
                    RowInfo("Dark Mode", if (p.dark) "On" else "Off")
                }
            }
        }
    }
}

@Composable
private fun RowInfo(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

// ---------------------- ViewModel ------------------------
class SharedPrefsViewModel(private val repo: PreferencesRepository) : ViewModel() {
    var name by mutableStateOf("")
    var nim by mutableStateOf("")
    var remember by mutableStateOf(false)
    var dark by mutableStateOf(false)
    var preview by mutableStateOf<Prefs?>(null)

    fun save() {
        repo.save(Prefs(name, nim, remember, dark))
        preview = repo.load()
    }

    fun load() {
        repo.load().also {
            name = it.name
            nim = it.nim
            remember = it.remember
            dark = it.dark
            preview = it
        }
    }

    fun clear() {
        repo.clear()
        name = ""
        nim = ""
        remember = false
        dark = false
        preview = null
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    @Suppress("UNCHECKED_CAST")
                    return SharedPrefsViewModel(
                        PreferencesRepository(context.applicationContext)
                    ) as T
                }
            }
    }
}