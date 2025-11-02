package id.antasari.p5datasave_230104040212.data.prefs

import android.content.Context

data class Prefs(
    val name: String = "",
    val nim: String = "",
    val remember: Boolean = false,
    val dark: Boolean = false
)

class PreferencesRepository(context: Context) {
    private val prefs = context.getSharedPreferences("keva_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_NAME = "name"
        private const val KEY_NIM = "nim"
        private const val KEY_REMEMBER = "remember"
        private const val KEY_DARK = "dark"
    }

    fun save(data: Prefs) {
        prefs.edit()
            .putString(KEY_NAME, data.name)
            .putString(KEY_NIM, data.nim)
            .putBoolean(KEY_REMEMBER, data.remember)
            .putBoolean(KEY_DARK, data.dark)
            .apply()
    }

    fun load(): Prefs = Prefs(
        name = prefs.getString(KEY_NAME, "") ?: "",
        nim = prefs.getString(KEY_NIM, "") ?: "",
        remember = prefs.getBoolean(KEY_REMEMBER, false),
        dark = prefs.getBoolean(KEY_DARK, false)
    )

    fun clear() {
        prefs.edit()
            .remove(KEY_NAME)
            .remove(KEY_NIM)
            .remove(KEY_REMEMBER)
            .remove(KEY_DARK)
            .apply()
    }
}