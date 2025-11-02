package id.antasari.p5datasave_230104040212.ui.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import id.antasari.p5datasave_230104040212.data.files.FilesRepository
import id.antasari.p5datasave_230104040212.data.files.NoteMeta

// ---------------- ViewModel ----------------

class FilesViewModel(private val repo: FilesRepository) : ViewModel() {
    var currentId by mutableStateOf<String?>(null)
    var title by mutableStateOf("")
    var note by mutableStateOf("")
    var newFileMode by mutableStateOf(false)
    var notes by mutableStateOf(listOf<NoteMeta>())

    fun refreshList() { notes = repo.listNotes() }

    fun setNewFileModeFlag(enable: Boolean) {
        newFileMode = enable
        if (enable) {
            currentId = null
            title = ""
            note = ""
        }
    }

    fun loadNote(id: String) {
        repo.readNote(id)?.let { (meta, content) ->
            currentId = meta.id
            title = meta.title
            note = content
            newFileMode = false
            refreshList()
        }
    }

    fun save() {
        val id = repo.saveNote(
            title = title,
            content = note,
            id = if (newFileMode) null else currentId
        )
        currentId = id
        newFileMode = false
        refreshList()
    }

    fun read() { currentId?.let { loadNote(it) } }

    fun delete() {
        currentId?.let {
            if (repo.deleteNote(it)) {
                currentId = null
                title = ""
                note = ""
                newFileMode = false
                refreshList()
            }
        }
    }

    fun deleteById(id: String) {
        if (repo.deleteNote(id)) {
            if (currentId == id) {
                currentId = null
                title = ""
                note = ""
                newFileMode = false
            }
            refreshList()
        }
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    @Suppress("UNCHECKED_CAST")
                    return FilesViewModel(FilesRepository(context.applicationContext)) as T
                }
            }
    }
}

// ---------------- UI Screen ----------------

@Composable
fun FilesScreen() {
    val ctx = LocalContext.current
    val vm: FilesViewModel = viewModel(factory = FilesViewModel.factory(ctx))
    var showReadDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Refresh list pertama kali
    LaunchedEffect(Unit) { vm.refreshList() }

    // ===== Dialog pilih file untuk READ =====
    if (showReadDialog) {
        AlertDialog(
            onDismissRequest = { showReadDialog = false },
            title = { Text("Select a file to read") },
            text = {
                if (vm.notes.isEmpty()) {
                    Text("No saved files.")
                } else {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = 80.dp, max = 320.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        vm.notes.forEach { meta ->
                            FilePickRow(
                                meta = meta,
                                onClick = {
                                    vm.loadNote(meta.id)
                                    showReadDialog = false
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showReadDialog = false }) { Text("Close") }
            }
        )
    }

    // ===== Dialog pilih file untuk DELETE =====
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Select a file to delete") },
            text = {
                if (vm.notes.isEmpty()) {
                    Text("No saved files.")
                } else {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = 80.dp, max = 320.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        vm.notes.forEach { meta ->
                            FilePickRow(
                                meta = meta,
                                onClick = {
                                    vm.deleteById(meta.id)
                                    showDeleteDialog = false
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Close") }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Surface(shape = MaterialTheme.shapes.large, tonalElevation = 1.dp) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    // Judul rata tengah
                    Text(
                        "File Handling Demo",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    // Status ringkas: jumlah file & selection
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        tonalElevation = 0.dp
                    ) {
                        val info = buildString {
                            append("Saved files: ${vm.notes.size}")
                            if (vm.currentId != null) append(" (Selected)")
                        }
                        Text(
                            text = info,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                        )
                    }

                    // Title field
                    OutlinedTextField(
                        value = vm.title,
                        onValueChange = { vm.title = it },
                        label = { Text("Title") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Note text area
                    OutlinedTextField(
                        value = vm.note,
                        onValueChange = { vm.note = it },
                        label = { Text("Your Note") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 180.dp),
                        singleLine = false,
                        maxLines = Int.MAX_VALUE
                    )

                    // Counter karakter
                    Text(
                        "Character count: ${vm.note.length}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Toggle New File
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("New File")
                            Text(
                                "Clear editor for a fresh note",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = vm.newFileMode,
                            onCheckedChange = { vm.setNewFileModeFlag(it) }
                        )
                    }

                    // Tombol aksi: SAVE, READ, DELETE
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilledTonalButton(
                            onClick = { vm.save() },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) { Text("SAVE") }

                        OutlinedButton(
                            onClick = {
                                vm.refreshList()
                                showReadDialog = true
                            },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) { Text("READ") }

                        OutlinedButton(
                            onClick = {
                                vm.refreshList()
                                showDeleteDialog = true
                            },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) { Text("DELETE") }
                    }
                }
            }
        }

        // Daftar file tersimpan
        if (vm.notes.isNotEmpty()) {
            item {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 1.dp
                ) {
                    Column(
                        Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Saved Files", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }

            items(vm.notes, key = { it.id }) { meta ->
                FileRow(
                    meta = meta,
                    selected = vm.currentId == meta.id,
                    onClick = { vm.loadNote(meta.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(Modifier.height(12.dp)) }
        }
    }
}

@Composable
private fun FilePickRow(meta: NoteMeta, onClick: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(meta.title, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.width(8.dp))
            Text("${meta.length} ch", style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun FileRow(
    meta: NoteMeta,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (selected) MaterialTheme.colorScheme.secondaryContainer
    else MaterialTheme.colorScheme.surfaceVariant
    val fg = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
    else MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        shape = MaterialTheme.shapes.small,
        color = bg,
        contentColor = fg,
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(meta.title, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.width(8.dp))
            Text("${meta.length} ch", style = MaterialTheme.typography.labelSmall)
        }
    }
}