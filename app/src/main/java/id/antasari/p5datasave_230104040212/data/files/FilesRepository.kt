package id.antasari.p5datasave_230104040212.data.files

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.Locale

data class NoteMeta(
    val id: String,          // unique id (timestamp string)
    val title: String,       // display title
    val fileName: String,    // actual txt file name in notesDir
    val createdAt: Long,
    val updatedAt: Long,
    val length: Int
)

class FilesRepository(private val context: Context) {
    private val notesDir = File(context.filesDir, "keva_notes").apply { mkdirs() }
    private val indexFile = File(notesDir, "_index.json")

    // ---------- Public APIs ----------
    fun listNotes(): List<NoteMeta> = loadIndex().sortedByDescending { it.updatedAt }

    fun readNote(id: String): Pair<NoteMeta, String>? {
        val index = loadIndex()
        val meta = index.find { it.id == id } ?: return null
        val file = File(notesDir, meta.fileName)
        val content = runCatching { file.readText() }.getOrDefault("")
        return meta.copy(length = content.length) to content
    }

    /**
     * Create new or update existing note.
     * @param id null untuk note baru, isi untuk update existing
     * @return id note yang tersimpan
     */
    fun saveNote(title: String, content: String, id: String? = null): String {
        val index = loadIndex().toMutableList()
        val now = System.currentTimeMillis()

        return if (id == null) {
            // create
            val newId = now.toString()
            val slug = slugify(title)
            val fileName = "${newId}_${slug}.txt"

            File(notesDir, fileName).writeText(content)

            val meta = NoteMeta(
                id = newId,
                title = title.ifBlank { "Untitled" },
                fileName = fileName,
                createdAt = now,
                updatedAt = now,
                length = content.length
            )
            index.add(meta)
            saveIndex(index)
            newId
        } else {
            // update
            val pos = index.indexOfFirst { it.id == id }
            val old = if (pos >= 0) index[pos] else null
            val fileName = old?.fileName ?: run {
                // jika tidak ketemu, treat as new
                return saveNote(title, content, id = null)
            }

            File(notesDir, fileName).writeText(content)
            val updated = NoteMeta(
                id = id,
                title = title.ifBlank { "Untitled" },
                fileName = fileName,
                createdAt = old.createdAt,
                updatedAt = now,
                length = content.length
            )
            index[pos] = updated
            saveIndex(index)
            id
        }
    }

    fun deleteNote(id: String): Boolean {
        val index = loadIndex().toMutableList()
        val pos = index.indexOfFirst { it.id == id }
        if (pos < 0) return false
        val meta = index[pos]

        // hapus file konten
        File(notesDir, meta.fileName).delete()
        // hapus entri index
        index.removeAt(pos)
        saveIndex(index)
        return true
    }

    // ---------- Internal helpers ----------
    private fun slugify(raw: String): String {
        val s = raw.lowercase(Locale.ROOT)
            .replace(Regex("[^a-z0-9]+"), "-")
            .trim('-')
        return if (s.isBlank()) "note" else s.take(32)
    }

    private fun loadIndex(): MutableList<NoteMeta> = runCatching {
        if (!indexFile.exists()) return mutableListOf()
        val arr = JSONArray(indexFile.readText())
        (0 until arr.length()).map { i ->
            val o = arr.getJSONObject(i)
            NoteMeta(
                id = o.getString("id"),
                title = o.optString("title", "Untitled"),
                fileName = o.getString("fileName"),
                createdAt = o.optLong("createdAt", 0L),
                updatedAt = o.optLong("updatedAt", 0L),
                length = o.optInt("length", 0)
            )
        }.toMutableList()
    }.getOrElse { mutableListOf() }

    private fun saveIndex(list: List<NoteMeta>) {
        val arr = JSONArray()
        list.forEach { n ->
            val o = JSONObject()
            o.put("id", n.id)
            o.put("title", n.title)
            o.put("fileName", n.fileName)
            o.put("createdAt", n.createdAt)
            o.put("updatedAt", n.updatedAt)
            o.put("length", n.length)
            arr.put(o)
        }
        indexFile.writeText(arr.toString())
    }
}