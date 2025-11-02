package id.antasari.p5datasave_230104040212.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import id.antasari.p5datasave_230104040212.data.prefs.Prefs
import id.antasari.p5datasave_230104040212.data.prefs.PreferencesRepository

// -------------------- ViewModel --------------------

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
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    @Suppress("UNCHECKED_CAST")
                    return SharedPrefsViewModel(
                        PreferencesRepository(context.applicationContext)
                    ) as T
                }
            }
    }
}
// -------------------- UI Screen --------------------

@Composable
fun SharedPrefsScreen(onDarkChanged: (Boolean) -> Unit = {}) {
    val ctx = LocalContext.current
    val vm: SharedPrefsViewModel = viewModel(factory = SharedPrefsViewModel.factory(ctx))
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section container
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 1.dp
        ) {
            Column(
                Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // JUDUL RATA TENGAH
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

                // NIM
                OutlinedTextField(
                    value = vm.nim,
                    onValueChange = { vm.nim = it },
                    label = { Text("NIM (Student ID)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = {
                        Icon(
                            Icons.Outlined.ContentPaste,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                // Switch: Remember Me
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
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
                // Switch: Dark Mode (langsung ubah tema)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
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
                            onDarkChanged(checked) //update tema aplikasi
                        }
                    )
                }
                // Action Buttons - tanpa ikon, rapi 3 kolom
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Tombol SIMPAN (SAVE)
                    FilledTonalButton(
                        onClick = {
                            vm.save()
                            // sinkronkan tema bila user menyimpan preferensi
                            onDarkChanged(vm.dark)
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text("SAVE")
                    }

                    // Tombol MUAT (LOAD)
                    OutlinedButton(
                        onClick = {
                            vm.load()
                            // terapkan tema sesuai data yang diload
                            onDarkChanged(vm.dark)
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text("LOAD")
                    }

                    // Tombol HAPUS (CLEAR)
                    OutlinedButton(
                        onClick = {
                            vm.clear()
                            // reset ke light saat clear
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
        // Pratinjau (Kontras Light & Dark)
        vm.preview?.let {
            Surface(
                shape = MaterialTheme.shapes.large,
                tonalElevation = 1.dp,
                color = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Judul Pratinjau
                    Text(
                        "Saved Data Preview",
                        style = MaterialTheme.typography.titleSmall
                    )
                    // Detail Data
                    Text("Name: ${it.name}")
                    Text("NIM: ${it.nim}")
                    // Pratinjau status boolean "Remember Me"
                    Text("Remember Me: ${if (it.remember) "Yes" else "No"}")
                    // Pratinjau status boolean "Dark Mode"
                    Text("Dark Mode: ${if (it.dark) "On" else "Off"}")
                }
            }
        }
    }
}


