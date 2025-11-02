# 💾 Keva - Aplikasi Penyimpanan Data Sederhana (SharedPreferences & File)

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Compose-green)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-purple)](https://m3.material.io/)

**Keva** adalah sebuah aplikasi Android yang dibangun menggunakan **Jetpack Compose** dan **Material 3** untuk mendemonstrasikan dan mempraktikkan konsep **penyimpanan data sederhana** di Android, yaitu **SharedPreferences** (untuk data _key-value_ kecil) dan **File Handling** (untuk menyimpan dan mengelola berkas di _internal storage_).

---

## 🎯 Tujuan Praktikum

Proyek ini dikembangkan untuk mencapai pemahaman dan implementasi dari poin-poin berikut:

1.  **Konsep Penyimpanan Data:** Memahami konsep dasar **SharedPreferences** (_key–value_) dan **File Handling** (_I/O_) di Android.
2.  **SharedPreferences CRUD:** Menggunakan SharedPreferences untuk **save/load/clear** data pengguna (Nama, NIM, Remember Me, Dark Mode).
3.  **Repository Pattern:** Membangun **Repository** yang terstruktur untuk SharedPreferences dan File agar **mudah dipakai** dan **diuji** (Testable).
4.  **Dark Mode Persistensi:** Mengaitkan preferensi **Dark Mode** dengan Material 3 sehingga tersimpan dan **diterapkan ulang** saat aplikasi dibuka.
5.  **Validasi Input:** Menerapkan **validasi input dasar** (wajib isi, NIM numerik) sebelum proses penyimpanan.
6.  **Pengelolaan File (CRUDL):** Mengelola file di _internal storage_: **create/update, read, delete**, serta **list** file yang tersimpan.
7.  **UI Pemilihan File:** Menyediakan **UI pemilihan file** untuk aksi `READ` dan `DELETE` (pilih berkas lalu proses).
8.  **Penanganan Error I/O:** Menangani **error I/O** (file tidak ada, format/izin) dan memberi _feedback_ yang jelas ke pengguna.
9.  **Desain UI Compose (MD3):** Mendesain UI dengan Jetpack Compose (MD3): _form_ rapi, tombol tanpa ikon, _Saved Data Preview_ (label kiri, nilai kanan), dan **TopAppBar berlogo** konsisten.
10. **Persistensi & Stabilitas:** Menguji **persistensi & stabilitas state** (saat _restart app_ atau rotasi layar), serta memastikan alur **navigasi tetap lancar**.

---

## 📂 Struktur Proyek

Struktur direktori utama proyek mengikuti praktik terbaik untuk aplikasi Jetpack Compose, berfokus pada pemisahan _Concerns_ (MVC/MVVM-like structure).

| Direktori/File | Keterangan/Fungsi Utama |
| :--- | :--- |
| `app/build.gradle.kts` | Konfigurasi Compose & _Dependencies_ (Material 3, Navigation, dll.) |
| **`src/main/`** | Sumber utama aplikasi |
| `- `**`java/../nimanda`** | Paket utama (_Root Package_) |
| `- - `**`MainActivity.kt`** | Titik masuk aplikasi (`Scaffold`, `TopAppBar kustom`, `BottomBar`, sinkron Dark Mode) |
| `- - `**`data/`** | Lapisan Data (berisi Repository dan Model) |
| `- - - `**`data/prefs/`** | Implementasi SharedPreferences |
| `- - - - `**`PreferencesRepository.kt`** | **Save/Load/Clear data** via SharedPreferences |
| `- - - - `**`Prefs.kt`** | _Data class_ untuk menyimpan data preferensi (name, nim, remember, dark) |
| `- - - `**`data/files/`** | Implementasi File Handling (Internal Storage) |
| `- - - - `**`FilesRepository.kt`** | **Create/Update, Read, Delete, List** (internal storage) |
| `- - - - `**`FileModels.kt`** | Model/Ringkasan data untuk _item file_ / _list_ file |
| `- - `**`ui/`** | Lapisan Antarmuka Pengguna (UI) |
| `- - - `**`ui/screens/`** | Komponen UI Utama (Screen) |
| `- - - - `**`HomeScreen.kt`** | **Card** ke SharedPreferences / Teks center |
| `- - - - `**`SharedPrefsScreen.kt`** | **Form** + _toggle_, tombol SAVE/READ/CLEAR, _Saved Data Preview_ |
| `- - - - `**`FilesScreen.kt`** | **Editor** + _Toggle New File_, tombol SAVE/READ/DELETE + _picker list_ |
| `- - - - `**`AboutScreen.kt`** | Info paket & versi (`PackageManager`), _Quick Tips_ (_chips_), _scrollable_ |
| `- - - `**`ui/components/`** | Komponen UI Reusable |
| `- - - - `**`InfoBanner.kt`** | **Banner** info/tips kecil |
| `- - - `**`ui/theme/`** | Konfigurasi Tema (Material 3) |
| `- - - - `**`Theme.kt`** | **KevaTheme** (Light/Dark), Material 3 _colorScheme_ |
| `- - - - `**`Color.kt`** | **KevaGreen/Blue/Teal**, dsb. |
| `- - `**`utils/`** | _Helper_ & Utilitas |
| `- - - - `**`InfoHelper.kt`** | _Helper_ kecil jika diperlukan |
| **`src/main/res/`** | Sumber daya aplikasi (Resources) |
| `- `**`values/strings.xml`** | Teks UI lainnya (`"Keva"`, dsb.) |
| `- `**`drawable/ic_keva_foreground.xml`** | LOGO _foreground_ (dipakai di TopAppBar – aman untuk Compose) |
| `- `**`mipmap/ic_keva.xml`** | Varian density _ic_keva.png_ |

---

## 🔧 Alat & Bahan (Requirements)

### 💻 Perangkat Keras (Hardware)

* Laptop x64 (RAM $\ge$ 8 GB disarankan)
* Smartphone Android (USB debug aktif) atau Emulator Android.

### ⚙️ Perangkat Lunak (Software)

* **Android Studio** (Narwhal | 2025.1.1 atau yang lebih baru).
* **JDK 17** (ikut bawaan Android Studio).
* **Android SDK:**
    * `compileSdk = 35`, `targetSdk = 35`, `minSdk = 24`.
    * Platform Tools & Build Tools terbaru.
* Android Emulator (API 34/35) atau perangkat fisik (USB debugging aktif).
* Git (opsional untuk _versioning_).

### 📚 Library & Tools Pendukung

| Library | Versi | Tujuan |
| :--- | :--- | :--- |
| **Compose BOM** | `2024.10.01` | Mengelola versi komponen Compose |
| **Activity Compose** | `1.9.3` | Integrasi Activity dengan Compose |
| **Material 3 (M3)** | (BOM) | Komponen UI (Theme, Button, Form, Scaffold) |
| **Material Icons Extended** | (BOM) | Set Ikon Tambahan |
| **Navigation Compose** | `2.8.3` | Pengelolaan Navigasi antar Screen |
| **Lifecycle Runtime Compose** | `2.8.6` | Mengamati siklus hidup komponen |
| **DataStore (Preferences)** | `1.1.1` | Pengganti modern untuk SharedPreferences |
| **Tooling** | (BOM) | Debugging & Preview (UI Tooling/Test Manifest) |

**Catatan:** Kompiler Compose: `kotlinCompilerExtensionVersion = "1.5.14"`. Pastikan Build Features: `compose true`.

---

## 👥 Developer

| Peran | Nama |
| :--- | :--- |
| **Mahasiswa/Pengembang** | M. Kaspul Anwar |
| **Dosen Pengampu** | Muhayat, M. IT |

---

**© 2025 - Mobile Programming Praktikum**
