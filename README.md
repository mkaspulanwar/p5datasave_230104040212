# Keva - Praktikum Pemprograman Mobile Week #5 (SharedPreferences & File I/O)

<p align="center">
  <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License">
  <img src="https://img.shields.io/badge/Language-Kotlin-orange?style=flat&logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=flat&logo=jetpack-compose" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Design-Material%203-673AB7?style=flat&logo=material-design" alt="Material 3">
</p>

**Keva** adalah sebuah aplikasi Android yang dirancang untuk mendemonstrasikan implementasi **persistensi data lokal** menggunakan **Jetpack Compose (Material 3)**. Fokus utama proyek ini adalah pengelolaan **SharedPreferences** (untuk preferensi pengguna) dan **File Handling** (untuk manajemen berkas di _internal storage_).

---

## 👥 Tim Developer

| Peran | Nama | Profil GitHub |
| :--- | :--- | :--- |
| **Pengembang Proyek** | M. Kaspul Anwar | [![](https://img.shields.io/badge/GitHub-M.KaspulAnwar-181717?style=flat&logo=github)]([https://github.com/mkaspulanwar]) |
| **Dosen Pengampu** | Muhayat, M. IT | [![](https://img.shields.io/badge/GitHub-Muhayat,M.IT-181717?style=flat&logo=github)]([https://github.com/muhayat-lab]) |

---

## ✨ Fitur Utama (Objective)

Proyek ini mencakup praktik pengembangan inti berikut:

### I. Data Persistence & Repository Pattern
* Implementasi **SharedPreferences** untuk menyimpan/memuat/menghapus data kecil (_Name, NIM, Remember Me, Dark Mode_).
* Pengelolaan file **Internal Storage** (Create, Read, Update, Delete, List) dengan penanganan _error_ I/O yang robust.
* Penerapan **Repository Pattern** untuk memisahkan lapisan data (SharedPreferences dan File) dari UI/ViewModel, memastikan kode yang _testable_ dan _maintainable_.

### II. UI/UX & Compose Best Practices
* Sinkronisasi status **Dark Mode** dari SharedPreferences ke **Material 3 Theme** saat aplikasi diluncurkan.
* Navigasi menggunakan **Navigation Compose** dengan implementasi **BottomBar** dan **TopAppBar** kustom.
* Penerapan **Validasi Input** dasar (non-kosong, numerik) pada form sebelum proses penyimpanan.
* Desain UI modern menggunakan **Jetpack Compose & Material 3** (MD3) dengan fokus pada _form_ yang rapi dan _data preview_ yang jelas.

---

## 📂 Struktur Proyek

Struktur direktori utama proyek mengikuti praktik terbaik untuk aplikasi Jetpack Compose, berfokus pada pemisahan _Concerns_ (MVC/MVVM-like structure).
```
app/
├── build.gradle(.kts)                   # Compose & deps (material3, navigation, activity-compose, runtime-saveable)
├── src/
│   └── main/
│       ├── AndroidManifest.xml          # android:icon="@mipmap/ic_keva", label "Keva"
│       ├── java/id/antasari/p5datasave/main/
│       │   ├── MainActivity.kt          # Scaffold: TopAppBar kustom (tinggi), BottomBar, sinkron Dark Mode
│       │
│       ├── navigation/
│       │   ├── Dest.kt                  # Definisi route & item BottomBar (Home, SharedPrefs, Files, About)
│       │   ├── NavGraph.kt              # NavHost + composable routes
│       │   └── NavExt.kt                # navigateSingleTopTo(popUpTo start, save/restore state)
│       │
│       ├── data/
│       │   ├── prefs/
│       │   │   ├── PreferencesRepository.kt  # save()/load()/clear() via SharedPreferences
│       │   │   └── Prefs.kt                 # data class (name, nim, remember, dark)
│       │   │
│       │   ├── files/
│       │   │   ├── FilesRepository.kt       # create/update, read, delete, list (internal storage)
│       │   │   └── FileModels.kt (opsional) # model ringan untuk item file/list
│       │
│       ├── ui/
│       │   ├── screens/
│       │   │   ├── HomeScreen.kt           # Card ke SharedPrefs / Files, teks center
│       │   │   ├── SharedPrefsScreen.kt    # Editor + toggle, tombol SAVE/READ/CLEAR, Saved Data Preview
│       │   │   ├── FilesScreen.kt          # + Toggle New File, tombol SAVE/READ/DELETE + picker list
│       │   │   └── AboutScreen.kt          # Info paket & versi (PackageManager), Quick Tips (chips), scrollable
│       │   │
│       │   ├── components/
│       │   │   └── InfoBanner.kt (opsional) # Banner info/tips kecil
│       │   │
│       │   └── theme/
│       │       ├── Theme.kt               # KevaTheme (Light/Dark), Material 3 colorScheme
│       │       └── Color.kt               # KevaGreen/Blue/Teal, dsb.
│       │
│       ├── utils/ (opsional)               # Helper kecil jika diperlukan
│       │
│       └── res/
│           ├── values/
│           │   └── strings.xml             # "Keva", teks UI lain
│           │
│           ├── drawable/
│           │   └── ic_keva_foreground.xml  # LOGO foreground (dipakai di TopAppBar – aman utk Compose)
│           │
│           ├── mipmap-anydpi-v26/
│           │   └── ic_keva.xml             # Adaptive launcher icon (jangan dipakai langsung di Compose)
│           │
│           ├── mipmap-*/                   # Varian density ic_keva.png
│           │
│           └── xml/ (opsional)
```
---

## 🛠️ Stack & Dependencies

| Kategori | Dependency/Tool | Versi/Spesifikasi |
| :--- | :--- | :--- |
| **UI Framework** | Jetpack Compose + Material 3 | BOM `2024.10.01` |
| **Data Storage** | DataStore Preferences (Kotlin Flow) | `1.1.1` |
| **Navigation** | Navigation Compose | `2.8.3` |
| **Target SDK** | `compileSdk/targetSdk` | `35` |
| **Min SDK** | `minSdk` | `24` |
| **Build Tool** | Android Studio Narwhal | `2025.1.1` + JDK 17 |

---

## 🚀 Instalasi dan Jalankan

1.  Clone repository ini:
    ```bash
    git clone [URL_REPOSITORY_ANDA]
    ```
2.  Buka proyek di **Android Studio Narwhal (2025.1.1 atau terbaru)**.
3.  Pastikan dependensi Compose (`kotlinCompilerExtensionVersion = "1.5.14"`) telah disinkronkan.
4.  Jalankan aplikasi pada Emulator (API 34/35) atau perangkat fisik Android.

---
